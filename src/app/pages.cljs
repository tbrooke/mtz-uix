(ns app.pages
  (:require
   [app.ui :as ui]
   [uix.core :refer [$ defui]]))


(defui home-page []
  ($ ui/mtz-hero
     {:welcome-text "Welcome to"
      :title "Mount Zion United Church of Christ"
      :subtitle "China Grove, NC"
      :description "A welcoming community of faith where all are invited to experience God's love, grow in spiritual understanding, and serve others with compassion."
      :buttons [{:text "Join Us Sunday" :href "/worship" :variant "primary"}
                {:text "Learn More" :href "/about" :variant "secondary"}]}))




(defui about-page []
  ($ ui/mtz-app
    ($ :section {:class "py-16 px-4"}
      ($ :div {:class "max-w-4xl mx-auto"}
        ($ ui/heading {:class "text-3xl font-bold text-center text-gray-900 mb-8"}
          "About Mount Zion UCC")
        ($ :div {:class "grid md:grid-cols-2 gap-8"}
          ($ :div
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "Our Mission")
            ($ ui/p {:class "text-gray-600 mb-6"}
              "Mount Zion United Church of Christ is committed to being an open and affirming congregation that welcomes all people regardless of background, race, gender, or sexual orientation. We strive to follow Christ's example of unconditional love and acceptance.")
            ($ ui/p {:class "text-gray-600"}
              "Our mission is to worship God, nurture spiritual growth, serve our community, and work for justice and peace in the world."))
          ($ :div {:class "bg-blue-50 rounded-lg p-8"}
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "Our Values")
            ($ :ul {:class "space-y-3 text-gray-600"}
              ($ :li "• Faith in God's transforming love")
              ($ :li "• Radical hospitality and inclusion")
              ($ :li "• Justice and compassion for all")
              ($ :li "• Lifelong spiritual growth")
              ($ :li "• Service to our community")
              ($ :li "• Care for God's creation"))))))))

(defui worship-page []
  ($ ui/mtz-app
    ($ :section {:class "py-16 px-4"}
      ($ :div {:class "max-w-4xl mx-auto text-center"}
        ($ ui/heading {:class "text-3xl font-bold text-gray-900 mb-8"}
          "Sunday Worship")
        ($ :div {:class "bg-blue-50 rounded-lg p-8 mb-8"}
          ($ ui/heading {:class "text-2xl font-semibold text-gray-800 mb-4"}
            "Join Us Every Sunday")
          ($ ui/p {:class "text-xl text-gray-700 mb-2"}
            "10:00 AM")
          ($ ui/p {:class "text-gray-600"}
            "All are welcome to worship with us"))
        ($ :div {:class "grid md:grid-cols-2 gap-8 text-left"}
          ($ :div
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "What to Expect")
            ($ ui/p {:class "text-gray-600 mb-4"}
              "Our worship services blend traditional and contemporary elements, featuring meaningful music, thoughtful sermons, and opportunities for prayer and reflection.")
            ($ ui/p {:class "text-gray-600"}
              "Whether you're a lifelong Christian or just beginning your spiritual journey, you'll find a warm welcome at Mount Zion."))
          ($ :div
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "Special Services")
            ($ :ul {:class "space-y-2 text-gray-600"}
              ($ :li "• First Sunday: Communion")
              ($ :li "• Children's Message each week")
              ($ :li "• Special holiday services")
              ($ :li "• Community prayer time"))))))))

(defui events-page []
  ($ ui/mtz-app
    ($ :section {:class "py-16 px-4"}
      ($ :div {:class "max-w-4xl mx-auto text-center"}
        ($ ui/heading {:class "text-3xl font-bold text-gray-900 mb-8"}
          "Upcoming Events")
        ($ :div {:class "bg-blue-50 rounded-lg p-8"}
          ($ ui/p {:class "text-lg text-gray-700 mb-4"}
            "Stay tuned for upcoming events and activities!")
          ($ ui/p {:class "text-gray-600"}
            "Event information will be updated regularly with details about special services, community gatherings, and fellowship opportunities."))))))

(defui ministries-page []
  ($ ui/mtz-app
    ($ :section {:class "py-16 px-4"}
      ($ :div {:class "max-w-4xl mx-auto"}
        ($ ui/heading {:class "text-3xl font-bold text-center text-gray-900 mb-8"}
          "Ministries & Programs")
        ($ :div {:class "grid md:grid-cols-2 gap-8"}
          ($ :div
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "Worship & Music")
            ($ ui/p {:class "text-gray-600 mb-4"}
              "Join our choir or help with worship leadership. Musical talents of all levels are welcome.")
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4 mt-6"}
              "Christian Education")
            ($ ui/p {:class "text-gray-600"}
              "Adult Bible study and children's Sunday school programs help deepen our understanding of faith."))
          ($ :div {:class "bg-blue-50 rounded-lg p-8"}
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "Community Outreach")
            ($ ui/p {:class "text-gray-600 mb-4"}
              "We support local food pantries, participate in community service projects, and work for social justice.")
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "Fellowship")
            ($ ui/p {:class "text-gray-600"}
              "Regular potluck dinners, game nights, and seasonal celebrations build our church family bonds.")))
        ($ :div {:class "text-center mt-8"}
          ($ ui/p {:class "text-lg text-gray-700"}
            "Interested in getting involved? Contact us to learn more about volunteer opportunities."))))))

(defui contact-page []
  ($ ui/mtz-app
    ($ :section {:class "py-16 px-4"}
      ($ :div {:class "max-w-4xl mx-auto"}
        ($ ui/heading {:class "text-3xl font-bold text-center text-gray-900 mb-8"}
          "Contact Us")
        ($ :div {:class "grid md:grid-cols-2 gap-8"}
          ($ :div {:class "bg-blue-50 rounded-lg p-8"}
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "Visit Us")
            ($ ui/p {:class "text-gray-700 mb-2"}
              "Mount Zion United Church of Christ")
            ($ ui/p {:class "text-gray-600 mb-4"}
              "China Grove, NC")
            ($ ui/heading {:class "text-lg font-semibold text-gray-800 mb-2"}
              "Sunday Worship")
            ($ ui/p {:class "text-gray-600"}
              "10:00 AM"))
          ($ :div
            ($ ui/heading {:class "text-xl font-semibold text-gray-800 mb-4"}
              "Get Connected")
            ($ ui/p {:class "text-gray-600 mb-6"}
              "We'd love to hear from you! Whether you're interested in visiting, have questions about our church, or want to get involved, please reach out.")
            ($ ui/p {:class "text-gray-600"}
              "Contact information will be updated soon. In the meantime, we invite you to join us for Sunday worship at 10:00 AM.")))))))