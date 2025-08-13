#!/usr/bin/env bb

(require '[babashka.fs :as fs]
         '[cheshire.core :as json]
         '[clojure.string :as str]
         '[clojure.pprint :refer [pprint]])

;; Type mapping from Strapi to Malli
(def type-mappings
  {"string"    :string
   "text"      :string
   "uid"       :string
   "email"     [:re #".+@.+\..+"]
   "boolean"   :boolean
   "integer"   :int
   "datetime"  :string  ; Could use inst? for actual date validation
   "date"      :string
   "json"      :any     ; Could be more specific
   "blocks"    :any     ; Strapi rich text blocks
   "media"     :map     ; Media objects are complex
   "component" :map     ; Components are nested objects
   "enumeration" :keyword})

(defn strapi-type->malli [attr-def]
  (let [base-type (get type-mappings (:type attr-def) :any)
        required? (:required attr-def)
        enum-vals (:enum attr-def)
        min-length (:minLength attr-def)
        max-length (:maxLength attr-def)
        min-val (:min attr-def)]
    
    (cond
      ;; Handle enumerations
      enum-vals
      [:enum (mapv keyword enum-vals)]
      
      ;; Handle string constraints
      (and (= base-type :string) (or min-length max-length))
      [:string (cond-> {}
                 min-length (assoc :min min-length)
                 max-length (assoc :max max-length))]
      
      ;; Handle integer constraints  
      (and (= base-type :int) min-val)
      [:int {:min min-val}]
      
      ;; Handle media types
      (= (:type attr-def) "media")
      (if (:multiple attr-def)
        [:vector :map]  ; Multiple media items
        [:maybe :map])  ; Single media item
      
      ;; Default case
      :else base-type)))

(defn process-attributes [attributes]
  (reduce-kv
    (fn [acc attr-name attr-def]
      (let [malli-type (strapi-type->malli attr-def)
            required? (:required attr-def)]
        (assoc acc 
               (keyword attr-name)
               (if required?
                 malli-type
                 [:maybe malli-type]))))
    {}
    attributes))

(defn schema-json->clojure [schema-data]
  (let [info (:info schema-data)
        attributes (:attributes schema-data)
        processed-attrs (process-attributes attributes)]
    
    {:schema-name (keyword (:singularName info))
     :display-name (:displayName info)
     :description (:description info)
     :collection-name (:collectionName schema-data)
     :plural-name (:pluralName info)
     :singular-name (:singularName info)
     :api-endpoint (str "/" (:pluralName info))
     :malli-schema [:map processed-attrs]
     :strapi-response-schema 
     [:map
      [:data [:vector [:map processed-attrs]]]
      [:meta [:map
              [:pagination [:maybe :map]]]]]}))

(defn generate-schemas-ns [schemas]
  (let [ns-form `(~'ns ~'app.schemas
                   "Generated Malli schemas from Strapi content types"
                   (:require [malli.core :as m]
                             [malli.error :as me]))
        
        schema-defs (map (fn [schema]
                           `(~'def ~(symbol (:singular-name schema))
                              "~(:description schema)"
                              ~(:malli-schema schema)))
                         schemas)
        
        response-schema-defs (map (fn [schema]
                                    `(~'def ~(symbol (str (:singular-name schema) "-response"))
                                       "Strapi API response for ~(:display-name schema)"
                                       ~(:strapi-response-schema schema)))
                                  schemas)
        
        all-schemas-map `(~'def ~'all-schemas
                           "Map of all content type schemas"
                           ~(into {} (map (fn [s] [(keyword (:singular-name s)) (:malli-schema s)]) schemas)))
        
        validation-fn '(defn validate
                         "Validate data against a schema"
                         [schema-key data]
                         (let [schema (get all-schemas schema-key)]
                           (if schema
                             (if (m/validate schema data)
                               {:valid? true :data data}
                               {:valid? false 
                                :errors (me/humanize (m/explain schema data))
                                :data data})
                             {:valid? false :errors {:schema "Schema not found"}})))]
    
    (concat [ns-form] schema-defs response-schema-defs [all-schemas-map validation-fn])))

(defn generate-enhanced-api-ns [schemas]
  (let [ns-form `(~'ns ~'app.api
                   "Enhanced API client with Malli validation"
                   (:require [app.schemas :as schemas]
                             [clojure.string :as str]))
        
        config-vars '(do
                       (def ^:dynamic *base-url* 
                         (if (exists? js/process)
                           (or (.-STRAPI_API_URL js/process.env) "http://backend:1337/api")
                           "http://localhost:1337/api"))
                       
                       (def ^:dynamic *api-token* 
                         (if (exists? js/process)
                           (.-STRAPI_API_TOKEN js/process.env)
                           nil)))
        
        fetch-fn '(defn fetch-json
                    "Fetch JSON data from a URL with optional authentication"
                    [url]
                    (let [headers (if *api-token*
                                    #js {"Authorization" (str "Bearer " *api-token*)
                                         "Content-Type" "application/json"}
                                    #js {"Content-Type" "application/json"})]
                      (-> (js/fetch url #js {:headers headers})
                          (.then #(.json %))
                          (.then #(js->clj % :keywordize-keys true)))))
        
        validated-fetch-fn '(defn fetch-with-validation
                              "Fetch and validate data against schema"
                              [endpoint schema-key]
                              (-> (fetch-json (str *base-url* endpoint))
                                  (.then (fn [response]
                                           (let [data (:data response)]
                                             (if (vector? data)
                                               (map #(schemas/validate schema-key %) data)
                                               [(schemas/validate schema-key data)]))))))
        
        ;; Generate specific API functions for each content type
        api-fns (mapcat (fn [schema]
                          (let [singular (:singular-name schema)
                                plural (:plural-name schema)
                                endpoint (:api-endpoint schema)
                                schema-key (keyword (:singular-name schema))]
                            
                            [`(~'defn ~(symbol (str "get-" plural))
                                "~(str \"Fetch \" (:display-name schema) \" with validation\")"
                                []
                                (~'fetch-with-validation ~endpoint ~schema-key))
                             
                             `(~'defn ~(symbol (str "get-" singular))
                                "~(str \"Fetch single \" (:display-name schema) \" by ID with validation\")"
                                [~'id]
                                (~'fetch-with-validation (str ~endpoint "/" ~'id) ~schema-key))
                             
                             `(~'defn ~(symbol (str "get-" plural "-raw"))
                                "~(str \"Fetch \" (:display-name schema) \" without validation\")"
                                []
                                (~'fetch-json (str ~'*base-url* ~endpoint)))]))
                        schemas)]
    
    (concat [ns-form config-vars fetch-fn validated-fetch-fn] api-fns)))

(defn generate-routes-ns [schemas]
  (let [ns-form `(~'ns ~'app.routes
                   "Generated Reitit routes from Strapi schemas"
                   (:require [reitit.frontend :as rf]
                             [app.pages :as pages]))
        
        ;; Generate routes for each content type
        content-routes (mapcat (fn [schema]
                                 (let [plural (:plural-name schema)
                                       singular (:singular-name schema)]
                                   [`[~(str "/" plural)
                                      {:name ~(keyword plural)
                                       :view ~(symbol "pages" (str plural "-page"))
                                       :controllers [{:start (fn [] (println "Loading ~(:display-name schema)"))}]}]
                                    
                                    `[~(str "/" plural "/:id")
                                      {:name ~(keyword singular)
                                       :view ~(symbol "pages" (str singular "-page"))
                                       :parameters {:path {:id :string}}
                                       :controllers [{:start (fn [params#] (println "Loading ~(:display-name schema)" (:id (:path params#))))}]}]]))
                               schemas)
        
        routes-def `(~'def ~'routes
                      [~'""
                       [~'"/" {:name :home :view ~'pages/home-page}]
                       ~@content-routes])]
    
    [ns-form routes-def]))

(defn find-strapi-schemas [backend-path]
  "Find all Strapi schema.json files"
  (let [api-path (str backend-path "/src/api")]
    (when (fs/exists? api-path)
      (->> (fs/glob api-path "**/schema.json")
           (mapv (fn [schema-file]
                   (-> schema-file
                       slurp
                       (json/parse-string keyword)
                       schema-json->clojure)))))))

(defn write-clojure-file [file-path forms]
  "Write ClojureScript forms to file with proper formatting"
  (fs/create-dirs (fs/parent file-path))
  (with-open [w (clojure.java.io/writer file-path)]
    (binding [*out* w]
      (doseq [form forms]
        (pprint form)
        (println)))))

(defn -main [& args]
  (let [backend-path (or (first args) "../../backend")
        output-dir "src/generated"]
    
    (println "🔍 Looking for Strapi schemas in:" backend-path)
    
    (let [schemas (find-strapi-schemas backend-path)]
      
      (if (empty? schemas)
        (do
          (println "❌ No Strapi schemas found!")
          (System/exit 1))
        (do
          (println "📋 Found" (count schemas) "schemas:")
          (doseq [schema schemas]
            (println "  -" (:display-name schema) "(" (:singular-name schema) ")"))
          
          ;; Generate enhanced API client
          (let [api-forms (generate-enhanced-api-ns schemas)]
            (write-clojure-file "src/app/api.cljs" api-forms)
            (println "✅ Generated enhanced API client: src/app/api.cljs"))
          
          ;; Generate Malli schemas
          (let [schema-forms (generate-schemas-ns schemas)]
            (write-clojure-file "src/app/schemas.cljs" schema-forms)
            (println "✅ Generated Malli schemas: src/app/schemas.cljs"))
          
          ;; Generate Reitit routes
          (let [route-forms (generate-routes-ns schemas)]
            (write-clojure-file "src/app/routes.cljs" route-forms)
            (println "✅ Generated Reitit routes: src/app/routes.cljs"))
          
          (println "\n🎉 Generation complete!")
          (println "📝 Don't forget to:")
          (println "   1. Add malli dependencies to deps.edn")
          (println "   2. Update existing namespaces to use generated code")
          (println "   3. Create page components for the routes")
          (println "   4. Test the API validation"))))))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))