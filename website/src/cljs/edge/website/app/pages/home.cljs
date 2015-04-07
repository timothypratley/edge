(ns edge.website.app.pages.home)

(defn home-page []
  [:div
   [:h2 "Welcome to website"]
   [:div [:a {:href "#/about"} "go to about page"]]])
