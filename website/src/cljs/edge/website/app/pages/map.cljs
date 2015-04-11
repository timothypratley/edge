(ns edge.website.app.pages.map
  (:require [edge.website.app.state :as state]))

(def radians-to-degrees (/ 180.0 Math/PI))

(defn draw-hospital [{[x y] :location
                      label :name}]
  [:g {:transform (str "translate(" x "," y ")")
       :fill "white"}
   [:circle {:r 15 :fill "red"}]
   [:rect {:width 20 :height 8 :x -10 :y -4}]
   [:rect {:width 8 :height 20 :x -4 :y -10}]
   [:text {:y 25
           :text-anchor "middle"
           :font-size 10
           :fill "black"}
    label]])

(defn mission-label [mission idx]
  [:text {:y (* idx 10)
          :text-anchor "middle"
          :font-size 10
          :fill "black"}
   (name (:cargo mission))])

(defn draw-remote [{[x y] :location} missions]
  (into
   [:g {:transform (str "translate(" x "," y ")")}
    [:circle {:r 5
              :stroke "darkred"
              :stroke-width 3
              :fill "black"}]]
   (map mission-label missions (iterate inc 2))))

(defn draw-drone [{[x y] :location
                   heading :heading
                   cargo :cargo
                   label :name}]
  [:g {:transform (str "translate(" x "," y ") rotate(" (* heading radians-to-degrees) ")")
       :stroke "green"
       :stroke-width 1
       :fill "green"}
   [:polygon {:points "-10,0 0,-5 10,0"}]
   [:rect {:x -5 :y -1 :width 10 :height 4
           :fill (case cargo
                   nil "rgb(0,200,0)"
                   :medX "rgb(0,0,0)"
                   :medY "rgb(128,0,0)"
                   :medZ "rgb(0,0,128)"
                   :urine "yellow"
                   :blood "red"
                   "white")}]])

(defn draw-world [world]
  (into
   [:svg {:style {:width "100%"
                  :height "100%"}}]
   (concat
    (for [[id hospital] (:hospitals world)]
      (draw-hospital hospital))
    (for [[id remote] (:remotes world)
          :let [missions (filter #(= (:remote-id %) id)
                                 (vals (:missions world)))]]
      (draw-remote remote missions))
    (for [[id drone] (:drones world)]
      (draw-drone drone)))))

(defn map-page []
  [:div
   [:h2 "Map"]
   [:div
    [:a {:href "#/"}
     "go to home page"]]
   [draw-world (:model @state/app-state)]])
