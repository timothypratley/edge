(ns edge.website.pages
  (:require [hiccup.page :as page]))

(defn home [dev?]
  (page/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
    [:link {:rel "stylesheet"
            :href "css/site.css"}]]
   [:body
    [:div {:id "app"}]
    [:script {:src "js/app.js"
              :type "text/javascript"}]]))
