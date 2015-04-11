(ns edge.website.app.main
  (:require [edge.website.app.communication :as communication]
            [edge.website.app.pages.about :as about]
            [edge.website.app.pages.home :as home]
            [edge.website.app.pages.map :as map]
            [reagent.core :as reagent]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [cljsjs.react :as react])
  (:import goog.History))

;; -------------------------
;; Routes

(def page {:home #'home/home-page
           :about #'about/about-page
           :map #'map/map-page})

(defn current-page []
  [:div [(page (session/get-in [:viewpoint :current-page]))]])

(secretary/set-config! :prefix "#")

(secretary/defroute "/" []
  (session/assoc-in! [:viewpoint :current-page] :home))

(secretary/defroute "/about" []
  (session/assoc-in! [:viewpoint :current-page] :about))

(secretary/defroute "/map" []
  (session/assoc-in! [:viewpoint :current-page] :map))

;; -------------------------
;; History
;; must be called after routes have been defined

(defn hook-browser-navigation! []
  (doto (History.)
    (events/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (hook-browser-navigation!)
  (mount-root))
