(ns edge.world
  (:require [edge.drone :refer :all]))


;denormalize
;(let [drones (concat (world :drones) (future-drones world))] )

#_(map assign-closest
     (sort-by :deadline
              (concat (map :remotes :missions world)
                      (map :drone :missions world))))

(defn make-plans [world]
  ;denormalize mission list instead of traversing for it
  (world :missions)
  world)

(defn maybe-done [world drone]
    world)


(defn update-missions [world]
  (maybe-done world nil)
  world)

;; todo this is now a command, move it there
(defn rand-mission [world remote]
  (if (> 0.001 (rand))
    (update-in remote [:missions] conj {:needs :medX})
    remote))

(defn create-missions [world]
  (update-in world [:remotes] #(map (partial rand-mission world) %)))


(defn step [world]
  (-> world
      (update-in [:drones] #(map update-drone %))
      update-missions
      create-missions
      make-plans))

