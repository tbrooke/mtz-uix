(ns app.pages
  (:require [app.ui :as ui]
            [uix.core :refer [$ defui]]))

(defui home-page []
  ($ ui/mtz-app
     ($ ui/mtz-hero
        {:welcome-text "Welcome to"
         :title "Mount Zion United Church of Christ"
         :subtitle "China Grove, NC"
         :description "A welcoming community of faith where all are invited to experience God's love, grow in spiritual understanding, and serve others with compassion."
         :buttons [{:text "Join Us Sunday" :variant :primary}
                   {:text "Learn More" :variant :secondary}]})

     ;; Static announcements section (no API calls)
     ($ :section.py-16.px-4
        ($ :div.max-w-4xl.mx-auto
           ($ ui/heading {:class "text-3xl text-center mb-8"} "Latest News")
           ($ :div.grid.md:grid-cols-2.gap-6
              ($ :div.bg-white.rounded-lg.p-6.shadow-md
                 ($ :h4.text-xl.font-bold.mb-2 "Welcome New Members")
                 ($ ui/p "We're excited to welcome new families to our church community."))
              ($ :div.bg-white.rounded-lg.p-6.shadow-md
                 ($ :h4.text-xl.font-bold.mb-2 "Community Dinner")
                 ($ ui/p "Join us for our monthly community dinner this Friday.")))))))

(defui about-page []
  ($ ui/mtz-app
     ($ :section.py-16.px-4
        ($ :div.max-w-4xl.mx-auto
           ($ ui/heading {:class "text-5xl text-center mb-8"} "About Mount Zion UCC")
           ($ ui/p "Our church has been serving the China Grove community for generations, providing a welcoming space for worship, fellowship, and spiritual growth.")))))

(defui worship-page []
  ($ ui/mtz-app
     ($ :section.py-16.px-4
        ($ :div.max-w-4xl.mx-auto.text-center
           ($ ui/heading {:class "text-5xl mb-8"} "Worship With Us")
           ($ ui/p {:class "text-xl mb-8"} "Join us every Sunday for meaningful worship, inspiring music, and community fellowship.")
           ($ :div.bg-blue-50.rounded-lg.p-8
              ($ :h3.text-3xl.font-bold.mb-4.text-blue-800 "Sunday Service")
              ($ ui/p "10:00 AM every Sunday")
              ($ ui/p "1234 Church Street, China Grove, NC"))))))

(defui events-page []
  ($ ui/mtz-app
     ($ :section.py-16.px-4
        ($ :div.max-w-4xl.mx-auto
           ($ ui/heading {:class "text-5xl text-center mb-8"} "Upcoming Events")
           ($ :div.grid.gap-6
              ($ :div.bg-blue-50.rounded-lg.p-6
                 ($ :h3.text-2xl.font-bold.mb-2.text-blue-800 "Sunday Worship")
                 ($ ui/p "Every Sunday at 10:00 AM")
                 ($ ui/p "Join us for worship and fellowship."))
              ($ :div.bg-blue-50.rounded-lg.p-6
                 ($ :h3.text-2xl.font-bold.mb-2.text-blue-800 "Bible Study")
                 ($ ui/p "Wednesdays at 7:00 PM")
                 ($ ui/p "Deepen your faith through scripture study.")))))))

(defui ministries-page []
  ($ ui/mtz-app
     ($ :section.py-16.px-4
        ($ :div.max-w-4xl.mx-auto
           ($ ui/heading {:class "text-5xl text-center mb-8"} "Our Ministries")
           ($ ui/p "Discover the many ways to get involved and serve in our church community.")))))

(defui contact-page []
  ($ ui/mtz-app
     ($ :section.py-16.px-4
        ($ :div.max-w-4xl.mx-auto
           ($ ui/heading {:class "text-5xl text-center mb-8"} "Contact Us")
           ($ :div.grid.md:grid-cols-2.gap-8
              ($ :div
                 ($ :h3.text-2xl.font-bold.mb-4 "Visit Us")
                 ($ ui/p "1234 Church Street")
                 ($ ui/p "China Grove, NC 28023"))
              ($ :div
                 ($ :h3.text-2xl.font-bold.mb-4 "Service Times")
                 ($ ui/p "Sunday Worship: 10:00 AM")))))))