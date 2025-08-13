(ns app.api
  (:require [app.schemas :as schemas]))

(def ^:dynamic *base-url* 
  (if (exists? js/window)
    "http://localhost:1337/api"  ; Browser context - use localhost
    "http://host.docker.internal:1337/api"))  ; Server context - use Docker host

(defn fetch-json [url]
  (-> (js/fetch url)
      (.then #(.json %))
      (.then #(js->clj % :keywordize-keys true))))

(defn get-sermons []
  (fetch-json (str *base-url* "/sermons")))

(defn get-sermon [id]
  (fetch-json (str *base-url* "/sermons/" id)))

(defn get-blog-entries []
  (fetch-json (str *base-url* "/blog-entries")))

(defn get-blog-entry [id]
  (fetch-json (str *base-url* "/blog-entries/" id)))

(defn get-homepage-features []
  (fetch-json (str *base-url* "/homepage-features?populate=*")))

(defn get-homepage-feature [id]
  (fetch-json (str *base-url* "/homepage-features/" id)))

(defn get-events []
  (fetch-json (str *base-url* "/events")))

(defn get-event [id]
  (fetch-json (str *base-url* "/events/" id)))