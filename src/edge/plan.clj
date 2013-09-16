(ns edge.plan
  (:require [edge.math :refer :all]
            [edge.commands :refer :all]
            [munkres :refer :all]))


;(defn assign [world [drone mission]]
;  (update-in world [:drones (drone :id) :missions]
;             (fnil conj #{}) mission))
;(reduce assign world (solution :assignments))))

(defn plan [world weight]
  (let [agents (world :drones)
        tasks (seq (world :missions))
        weights (weight-matrix agents tasks weight)
        _ (println weights)
        solution (solve agents tasks weights)]
    (solution :assignments)))

(defn assign-missions
  [world weight]
  (doseq [[drone mission] (plan world weight)
          :when mission]
    (assign-mission-command drone mission)))



#_(map assign-closest
     (sort-by :deadline
              (concat (map :remotes :missions world)
                      (map :drone :missions world))))
