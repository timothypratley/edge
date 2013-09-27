(ns edge.repl.scratch
  (:require [edge.core :refer :all]
            [edge.world :refer :all]
            [edge.commands :refer :all]
            [edge.event-sourcing :refer :all]
            [munkres :refer :all]
            [clojure.repl :refer :all]
            [criterium.core :refer :all]
            ;[midje.sweet :refer :all]
            ;[midje.repl]
            ))

#_(bench
 (let [agents (range 20)
      tasks (range 20)
      weights (vec (repeatedly 20 #(vec (range 20))))]
  (solve agents tasks weights)))

(start)

(step)
(map :cargo (@world :drones))
(doc assoc-in)
@world
(edge.plan/assign-missions @world #(weighter @world %1 %2))
(count (@world :missions))
(count (@world :drones))
(@world :drones)
(doseq [drone (@world :drones)]
  (pickup-command drone))
(first (filter (comp (partial = 0) :id) (@world :drones)))
(go-to @world
       (first (filter (comp (partial = 0) :id) (@world :drones))))

(count (filter :mission (@world :drones)))
(def d (first (filter :mission (@world :drones))))
(go-to @world d)
d
(can-complete-mission (get-in @world [:drones 7]))
(get-in @world [:drones 0])
(d :cargo)

(add-missions)

(update-drones)

(midje.repl/autotest)

(raise! :mission-assigned
        {:seq 193,
 :event :mission-assigned,
 :drone-id 0,
 :mission
 {:cargo :medX,
  :remote
  {:id 28,
   :type :remote,
   :name "Harbor Island Remote",
   :location [955 41]}}}
)


