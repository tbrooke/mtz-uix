(ns app.components
  (:require [app.ui :as ui]
            [app.pages :as pages]  ; New import
            [app.routes :as routes] ; New import
            [uix.core :refer [$ use-state use-effect defui]]
            [reitit.core :as r]           ; New import
            [reitit.frontend :as rf]      ; New import
            [reitit.frontend.easy :as rfe])) ; New import

;; Keep your existing counter component
(defui counter []
  (let [[count set-count!] (use-state 0)]
    ($ :.flex.flex-row.gap-12.items-center.ml-4.mt-2
       ($ :pre.text-3xl.mb-2.w-4.dark:text-white count)
       ($ :.gap-2
          ($ ui/button {:on-click #(set-count! inc)} "+")
          ($ ui/button {:on-click #(set-count! dec)} "-")))))

;; Router setup
(def router (rf/router routes/routes))


;; ;; Replace the page-router component in components.cljs with this:
;; (defui page-router [{:keys [current-route]}]
;;   (let [route-name (get-in current-route [:data :name])]
;;     (js/console.log "Route name:" route-name)
;;     (js/console.log "pages namespace:" pages)
    
;;     ;; Test if page components exist
;;     ($ :div.p-8
;;       ($ :h1 "Debug Router")
;;       ($ :p (str "Current route: " route-name))
      
;;       ;; Try calling just one page component first
;;       (try
;;         (case route-name
;;           :app.routes/home 
;;           (do 
;;             (js/console.log "Trying to render home-page")
;;             ($ pages/home-page))
          
;;           ;; For now, just show debug info for other routes
;;           ($ :div 
;;             ($ :h2 "Other Route")
;;             ($ :p (str "Route: " route-name))))
;;         (catch js/Error e
;;           (js/console.error "Error rendering page:" e)
;;           ($ :div 
;;             ($ :h2 "Error")
;;             ($ :p (str "Failed to render: " route-name))
;;             ($ :p (str "Error: " (.-message e)))))))))


;; Replace your current page-router with this real version:
(defui page-router [{:keys [current-route]}]
  (let [route-name (get-in current-route [:data :name])]
    (case route-name
      :app.routes/home ($ pages/home-page)
      :app.routes/about ($ pages/about-page)
      :app.routes/worship ($ pages/worship-page)
      :app.routes/events ($ pages/events-page)
      :app.routes/ministries ($ pages/ministries-page)
      :app.routes/contact ($ pages/contact-page)
      ;; Default case
      ($ pages/home-page))))




;; ;; Page router component
;; (defui page-router [{:keys [current-route]}]
;;   (let [route-name (get-in current-route [:data :name])]
;;     (case route-name
;;       :app.routes/home ($ pages/simple-home-page)
;;       :app.routes/about ($ pages/about-page)
;;       :app.routes/worship ($ pages/worship-page)
;;       :app.routes/events ($ pages/events-page)
;;       :app.routes/ministries ($ pages/ministries-page)
;;       :app.routes/contact ($ pages/contact-page)
;;       ;; Default/404 page
;;       ($ pages/home-page))))

;; Updated main app component
(defui app []
  (let [[current-route set-current-route!] (use-state nil)]

    ;; Initialize routing
    (use-effect
     (fn []
       (rfe/start! router
                   (fn [new-match _]
                     (set-current-route! new-match))
                   {:use-fragment false}))
     [])

    ;; Update document title based on route
    (use-effect
     (fn []
       (when-let [title (get-in current-route [:data :page-title])]
         (set! (.-title js/document) title)))
     [current-route])

    ($ :div
       ;; Header with navigation
       ($ ui/mtz-header {:nav-links routes/nav-links})

       ;; Main content area
       ($ page-router {:current-route current-route})

       ;; Optional: Keep counter for testing on home page
       (when (= (get-in current-route [:data :name]) :app.routes/home)
         ($ :section.py-8.text-center
            ($ counter))))))