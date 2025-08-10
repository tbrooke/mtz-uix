(ns app.pages-test
  "Minimal test version of pages.cljs to isolate component issues"
  (:require [app.ui :as ui]
            [uix.core :refer [$ defui]]))

;; Test 1: Basic component with no UI components
(defui test-basic []
  ($ :div "Basic test - no UI components"))

;; Test 2: Test ui/mtz-app component only
(defui test-mtz-app []
  ($ ui/mtz-app "Testing mtz-app component"))

;; Test 3: Test ui/mtz-hero component only
(defui test-mtz-hero []
  ($ ui/mtz-hero
     {:welcome-text "Test Welcome"
      :title "Test Title"
      :subtitle "Test Subtitle"
      :description "Test Description"}))

;; Test 4: Test ui/mtz-app + ui/mtz-hero together
(defui test-app-hero []
  ($ ui/mtz-app
     ($ ui/mtz-hero
        {:welcome-text "Welcome"
         :title "Mount Zion Test"
         :subtitle "Test Mode"
         :description "Testing components"})))

;; Test 5: Test ui/heading component
(defui test-heading []
  ($ ui/mtz-app
     ($ ui/heading "Test Heading")))

;; Test 6: Test ui/p component
(defui test-paragraph []
  ($ ui/mtz-app
     ($ ui/p "Test paragraph")))

;; Test 7: Minimal version of original home-page
(defui test-home-minimal []
  ($ ui/mtz-app
     ($ ui/mtz-hero
        {:welcome-text "Welcome to"
         :title "Mount Zion United Church of Christ"
         :subtitle "China Grove, NC"})))