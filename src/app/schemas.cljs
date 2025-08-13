(ns app.schemas
  (:require [malli.core :as m]
            [malli.error :as me]))

(def sermon
  "Sermon"
  [:map
 {:seo [:maybe :map],
  :tags [:maybe :any],
  :date :string,
  :slug :string,
  :notes_pdf [:maybe :map],
  :scripture_text :string,
  :featured [:maybe :boolean],
  :duration [:maybe :int],
  :video_url [:maybe :string],
  :topic
  [:maybe
   [:enum
    [:salvation
     :faith
     :hope
     :love
     :discipleship
     :worship
     :prayer
     :service
     :missions
     :family
     :stewardship]]],
  :title :string,
  :summary [:maybe :string],
  :video_embed [:maybe :string],
  :series_part [:maybe :int],
  :audio_url [:maybe :string],
  :transcript [:maybe :string],
  :speaker :string,
  :series_name [:maybe :string]}]
)

(def blog-entry
  "Blog Entry"
  [:map
 {:seo [:maybe :map],
  :tags [:maybe :any],
  :slug :string,
  :content :any,
  :excerpt [:maybe :string],
  :featured [:maybe :boolean],
  :title :string,
  :author :string,
  :featured_image [:maybe :map],
  :categories
  [:maybe
   [:enum
    [:news
     :teaching
     :testimony
     :community
     :missions
     :youth
     :worship]]],
  :publish_date :string}]
)

(def homepage-feature
  "Homepage Feature"
  [:map
 {:expires_at [:maybe :string],
  :button_text [:maybe :string],
  :button_style [:maybe [:enum [:primary :secondary :outline]]],
  :content :string,
  :button_url [:maybe :string],
  :display_order :int,
  :title :string,
  :active [:maybe :boolean],
  :subtitle [:maybe :string],
  :image :map}]
)

(def event
  "Event"
  [:map
 {:description :string,
  :seo [:maybe :map],
  :tags [:maybe :any],
  :slug :string,
  :recurrence_pattern [:maybe [:enum [:weekly :monthly :yearly]]],
  :contact_email [:maybe [:re #".+@.+\..+"]],
  :featured [:maybe :boolean],
  :max_attendees [:maybe :int],
  :contact_person [:maybe :string],
  :registration_deadline [:maybe :string],
  :registration_url [:maybe :string],
  :recurring [:maybe :boolean],
  :title :string,
  :featured_image [:maybe :map],
  :end_date [:maybe :string],
  :gallery [:maybe :map],
  :start_date :string,
  :all_day [:maybe :boolean],
  :contact_phone [:maybe :string],
  :cost [:maybe :map],
  :event_type
  [:enum
   [:service
    :meeting
    :social
    :outreach
    :study
    :conference
    :retreat
    :fundraiser]],
  :location [:maybe :map],
  :registration_required [:maybe :boolean]}]
)

(def all-schemas
  {:sermon sermon
   :blog-entry blog-entry
   :homepage-feature homepage-feature
   :event event})
