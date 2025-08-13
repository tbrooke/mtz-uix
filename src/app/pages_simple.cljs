(ns app.pages-simple
  (:require [app.ui :as ui]
            [uix.core :refer [$ defui]]))

(defui simple-home-page []
  ($ ui/mtz-app
     ($ :div.p-8
        ($ :h1.text-4xl.font-bold "Hello from Dynamic Content!")
        ($ :p "This is a test to see if the file loads properly."))))