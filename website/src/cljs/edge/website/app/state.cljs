(ns edge.website.app.state
  (:require [reagent.core :as reagent]))

(def test-world {:drones {1 {:name "D200"
                        :location [20 20]
                        :heading (/ Math/PI 4.0)
                        :speed 2}}
            :hospitals {1 {:name "Emergency One Hospital"
                           :location [70 70]}}
            :remotes {1 {:location [120 120]}}
            :missions {1 {:remote-id 1
                          :hospital-id 1
                          :cargo :blood}}})

(def app-state (reagent/atom {:world test-world}))
