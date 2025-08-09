(ns app.ui
  "UI components for the Mount Zion UCC application.
   
   This namespace provides reusable UI components including both generic
   components (button, heading, p, a) and Mount Zion-specific components
   that integrate with the CSS classes defined in mtz-components.css."
  (:require [uix.core :refer [defui $]]))

;; Generic UI Components

(defui button 
  "Generic button component with gradient styling.
   Props:
   - :on-click - Click handler function
   - :class - Additional CSS classes (vector of strings/keywords)
   - :children - Button content"
  [{:keys [on-click class children]}]
  ($ :button {:on-click on-click
              :class (merge
                       '[text-white
                         bg-gradient-to-r 
                         bg-gradient-to-r 
                         "from-[#A2DA5F]"
                         from-10%
                         "to-[#97B3F8]"
                         hover:bg-gradient-to-l 
                         transition 
                         ease-in-out
                         font-medium 
                         rounded-full 
                         text-sm 
                         px-3.5
                         py-2 
                         text-center 
                         select-none
                         me-2 
                         mb-2] class)}
    children))

(defui heading 
  "Generic heading component with gradient text.
   Props:
   - :children - Heading text content
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [children class]}]
  ($ :h1 {:class (merge 
                   '[text-transparent
                     bg-clip-text
                     bg-gradient-to-r 
                     "from-[#A2DA5F]"
                     from-0%
                     "to-[#97B3F8]"
                     mb-4 
                     text-6xl 
                     text-center
                     font-extrabold 
                     text-gray-900] class)}
    children))

(defui p 
  "Generic paragraph component.
   Props:
   - :children - Paragraph text content
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [children class]}]
  ($ :p {:class (merge 
                  '[my-2
                    text-lg
                    font-normal 
                    text-gray-700 
                    dark:text-gray-100] class)}
    children))

(defui a 
  "Generic anchor/link component.
   Props:
   - :href - Link URL
   - :children - Link text content
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [href children class]}]
  ($ :a {:href href
         :target "_blank"
         :class (merge '[hover:underline] class)}
    children))

;; Mount Zion Specific Components
;; These components integrate with the CSS classes in mtz-components.css

(defui mtz-logo
  "Mount Zion logo component with consistent styling.
   Props:
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [class]}]
  ($ :a {:href "/"
         :class (merge ["mtz-logo"] class)}
    ($ :span.mtz-logo-text "Mount Zion UCC")))

(defui mtz-nav-link
  "Navigation link with Mount Zion hover effects.
   Props:
   - :href - Link URL
   - :text - Link text
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [href text class]}]
  ($ :a {:href href
         :class (merge ["mtz-nav-link"] class)}
    text))

(defui mtz-nav
  "Main navigation component for Mount Zion.
   Props:
   - :links - Vector of maps with :href and :text keys
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [links class]}]
  ($ :nav {:class (merge ["mtz-nav"] class)}
    (for [link links]
      ($ mtz-nav-link {:key (:href link)
                       :href (:href link)
                       :text (:text link)}))))

(defui mtz-mobile-toggle
  "Mobile menu toggle button with hamburger animation.
   Props:
   - :on-click - Click handler function
   - :expanded? - Boolean indicating if menu is expanded (for future animation state)
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [on-click expanded? class]}]
  ($ :div {:class (merge ["mtz-mobile-toggle"] class)}
    ($ :button {:class "mtz-mobile-button"
                :on-click on-click
                :aria-label "Toggle mobile menu"}
      ($ :div.mtz-mobile-line)
      ($ :div.mtz-mobile-line)
      ($ :div.mtz-mobile-line))))

(defui mtz-header
  "Sticky header with Mount Zion logo and navigation.
   Props:
   - :nav-links - Vector of navigation link maps
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [nav-links class]}]
  ($ :header {:class (merge ["mtz-header"] class)}
    ($ :div.mtz-header-container
      ($ mtz-logo)
      ;; Desktop navigation (hidden on mobile via CSS)
      ($ mtz-nav {:links nav-links
                  :class ["hidden" "md:flex"]})
      ;; Mobile toggle (hidden on desktop via CSS)  
      ($ mtz-mobile-toggle {:class ["md:hidden"]}))))

(defui mtz-button
  "Mount Zion styled button with primary/secondary variants.
   Props:
   - :variant - :primary or :secondary (keyword, defaults to :primary)
   - :on-click - Click handler function
   - :class - Additional CSS classes (vector of strings/keywords)
   - :children - Button content"
  [{:keys [variant on-click class children] 
    :or {variant :primary}}]
  (let [variant-class (case variant
                        :primary "mtz-btn-primary"
                        :secondary "mtz-btn-secondary"
                        "mtz-btn-primary")]
    ($ :button {:class (merge [variant-class] class)
                :on-click on-click}
      children)))

(defui mtz-hero
  "Hero section with animated background and call-to-action buttons.
   Props:
   - :welcome-text - Welcome message (string, optional)
   - :title - Main title (string, optional)
   - :subtitle - Subtitle (string, optional)  
   - :description - Description text (string, optional)
   - :buttons - Vector of button configurations with :text, :variant, :on-click, :class keys (optional)
   - :class - Additional CSS classes (vector of strings/keywords)"
  [{:keys [welcome-text title subtitle description buttons class]}]
  ($ :section {:class (merge ["mtz-hero"] class)}
    ($ :div.mtz-hero-background)
    ($ :div.mtz-hero-content
      (when welcome-text
        ($ :div.mtz-hero-welcome welcome-text))
      ($ :div.mtz-hero-title-container
        (when title
          ($ :h1.mtz-hero-title title))
        (when subtitle
          ($ :h2.mtz-hero-subtitle subtitle)))
      (when description
        ($ :p.mtz-hero-description description))
      (when buttons
        ($ :div.mtz-hero-buttons
          (for [button buttons]
            ($ mtz-button {:key (:text button)
                           :variant (:variant button :primary)
                           :on-click (:on-click button)
                           :class (:class button)}
              (:text button))))))))

(defui mtz-app
  "Main application container with Mount Zion styling.
   Props:
   - :class - Additional CSS classes (vector of strings/keywords)
   - :children - App content"
  [{:keys [class children]}]
  ($ :div {:class (merge ["mtz-app"] class)}
    children))

