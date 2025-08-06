(ns app.components
  (:require [app.ui :as ui]
            [uix.core :refer [$ use-state defui]]))

(defui counter []
  (let [[count set-count!] (use-state 0)]
    ($ :.flex.flex-row.gap-12.items-center.ml-4.mt-2
      ($ :pre.text-3xl.mb-2.w-4.dark:text-white count)
      ($ :.gap-2
        ($ ui/button {:on-click #(set-count! inc)} "+")
        ($ ui/button {:on-click #(set-count! dec)} "-")))))

(defui app []
  ($ :main.flex.flex-col.gap-8.min-h-screen.justify-center.items-center.dark:bg-neutral-950.px-12.py-12
    ($ ui/heading "Mount Zion United Church of Christ"
       ($ ui/p {:class '[my-2]} "We are a family oriented church located in China Grove. If you’re looking for a place to call home, please come join us one Sunday morning or at one of our community events. Maybe you’ll find that Mount Zion is the family you’ve been looking for. "))
    ($ :.bg-neutral-100.dark:bg-neutral-900.rounded-xl.py-8.px-12
      ($ counter))
    ($ ui/button {:class '[my-20] 
                  :on-click #(js/window.open "https://github.com/harismh/utsb-cljs-starter")}
      "Clone Repo")
    ($ :pre.text-sm.text-gray-600.dark:text-gray-400.text-center.text-wrap
      "React 18.20 * UIx 1.1.1 * TailwindCSS 3.4.13 * ShadowCLJS 2.28.16 * Bun 1.1.24")))
