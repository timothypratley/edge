(ns edge.plan-test
  (:require [midje.sweet :refer :all]
            [edge.plan :refer :all]
            [edge.world :refer :all]
            [edge.rand :refer :all]
            [edge.commands :refer :all]
            [edge.event-sourcing :refer :all]))


(facts "about planning"
       (let [w (atom (rand-world [1024 800] 4 25 15))]
         (rand-mission @w)
         (accept @w {:event :mission-created :mission (rand-mission @w)})
         (dotimes [i 10]
           (swap! w accept {:event :mission-created :mission (rand-mission @w)}))
         (println ((plan @w #(weighter @w %1 %2)) :drones))
         (update-drones @w)
         ))

