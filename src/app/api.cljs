(ns app.api
  (:require [clojure.string :as str]))

(def strapi-base-url "http://localhost:1337/api")

(defn fetch-json
  "Fetch JSON data from a URL, returns a promise"
  [url]
  (-> (js/fetch url)
      (.then #(.json %))
      (.then #(js->clj % :keywordize-keys true))))

(defn get-events []
  "Fetch church events from Strapi"
  (fetch-json (str strapi-base-url "/events")))

(defn get-announcements []
  "Fetch church announcements from Strapi"
  (fetch-json (str strapi-base-url "/announcements")))

(defn get-sermons []
  "Fetch sermons from Strapi"
  (fetch-json (str strapi-base-url "/sermons")))