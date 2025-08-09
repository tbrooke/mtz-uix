(ns app.routes)

;; Reitit route format
(def routes
  [["/" {:name ::home :page-title "Welcome - Mount Zion UCC"}]
   ["/about" {:name ::about :page-title "About Us - Mount Zion UCC"}]
   ["/worship" {:name ::worship :page-title "Worship - Mount Zion UCC"}]
   ["/events" {:name ::events :page-title "Events - Mount Zion UCC"}]
   ["/ministries" {:name ::ministries :page-title "Ministries - Mount Zion UCC"}]
   ["/contact" {:name ::contact :page-title "Contact Us - Mount Zion UCC"}]])

;; Navigation links for mtz-header (using :text not :label)
(def nav-links
  [{:href "/" :text "Home"}
   {:href "/about" :text "About"}
   {:href "/worship" :text "Worship"}
   {:href "/events" :text "Events"}
   {:href "/ministries" :text "Ministries"}
   {:href "/contact" :text "Contact"}])