(ns app.routes)

;; Reitit route format
(def routes
  [["/" {:name ::home :page-title "Welcome - Mount Zion UCC"}]
   ["/about" {:name ::about :page-title "About Us - Mount Zion UCC"}]
   ["/worship" {:name ::worship :page-title "Worship - Mount Zion UCC"}]
   ["/events" {:name ::events :page-title "Events - Mount Zion UCC"}]
   ["/ministries" {:name ::ministries :page-title "Ministries - Mount Zion UCC"}]
   ["/contact" {:name ::contact :page-title "Contact Us - Mount Zion UCC"}]])

;; Navigation links for mtz-header with hierarchical dropdown structure
(def nav-links
  [{:href "/" :text "Home" :has-children false}
   {:href "/about" :text "About" :has-children true
    :children [{:href "/about/history" :text "Our History"}
               {:href "/about/staff" :text "Pastor & Staff"}
               {:href "/about/mission" :text "Mission Statement"}
               {:href "/about/beliefs" :text "Beliefs"}]}
   {:href "/worship" :text "Worship" :has-children true
    :children [{:href "/worship/sunday-services" :text "Sunday Services"}
               {:href "/worship/special-events" :text "Special Events"}
               {:href "/worship/music-ministry" :text "Music Ministry"}
               {:href "/worship/online-services" :text "Online Services"}]}
   {:href "/events" :text "Events" :has-children true
    :children [{:href "/events/upcoming" :text "Upcoming Events"}
               {:href "/events/calendar" :text "Church Calendar"}
               {:href "/events/community" :text "Community Events"}]}
   {:href "/ministries" :text "Ministries" :has-children true
    :children [{:href "/ministries/youth" :text "Youth Group"}
               {:href "/ministries/women" :text "Women's Ministry"}
               {:href "/ministries/men" :text "Men's Fellowship"}
               {:href "/ministries/outreach" :text "Community Outreach"}]}
   {:href "/contact" :text "Contact" :has-children true
    :children [{:href "/contact/get-in-touch" :text "Get in Touch"}
               {:href "/contact/directions" :text "Directions"}
               {:href "/contact/office-hours" :text "Office Hours"}]}])