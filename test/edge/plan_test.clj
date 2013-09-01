(ns edge.plan-test
  (:require [clojure.test :refer :all]
            [edge.plan :refer :all]
            [edge.world :refer :all]
            [edge.rand :refer :all]))


(def world (atom (rand-world [1024 800] 4 25 15)))

(dotimes [i 10]
  (swap! world maybe-add-missions))

world

#_(map #(vector (:name (key %)) (val %)) ((plan @world) :assignments))
((plan @world) :drones)

