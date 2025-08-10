(ns app.pages-debug
  "Step-by-step debugging version to isolate component issues"
  (:require [app.ui :as ui]
            [uix.core :refer [$ defui]]))  ;; FIX: Added defui to imports

;; Step 1: Test the ORIGINAL pages.cljs with the import fix
(defui home-page-fixed []
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

;; Step 2: Debugging components individually
(defui debug-step-1 []
  "Test: Can we create basic components?"
  ($ :div "Debug Step 1: Basic component works"))

(defui debug-step-2 []  
  "Test: Can we use ui/mtz-app?"
  ($ ui/mtz-app "Debug Step 2: mtz-app works"))

(defui debug-step-3 []
  "Test: Can we use ui/mtz-hero with minimal props?"
  ($ ui/mtz-app
     ($ ui/mtz-hero {:title "Debug Step 3: mtz-hero works"})))

(defui debug-step-4 []
  "Test: Can we use ui/mtz-hero with all props?"
  ($ ui/mtz-app
     ($ ui/mtz-hero
        {:welcome-text "Debug Welcome"
         :title "Debug Title"
         :subtitle "Debug Subtitle" 
         :description "Debug Description"
         :buttons [{:text "Debug Button" :variant :primary}]})))

(defui debug-step-5 []
  "Test: Can we use ui/heading?"
  ($ ui/mtz-app
     ($ ui/heading "Debug Step 5: heading works")))

(defui debug-step-6 []
  "Test: Can we use ui/p?"
  ($ ui/mtz-app
     ($ ui/p "Debug Step 6: paragraph works")))

(defui debug-step-7 []
  "Test: Full recreation with individual steps"
  ($ ui/mtz-app
     ($ ui/mtz-hero
        {:welcome-text "Welcome to"
         :title "Mount Zion United Church of Christ"
         :subtitle "China Grove, NC"})
     ($ :section.py-16.px-4
        ($ :div.max-w-4xl.mx-auto
           ($ ui/heading {:class "text-3xl text-center mb-8"} "Latest News")))))

;; SOLUTION: Fix the original pages.cljs import
;; Change line 3 in pages.cljs from:
;; [uix.core :refer [$]]
;; to: 
;; [uix.core :refer [$ defui]]