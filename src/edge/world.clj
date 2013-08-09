(ns edge.world
  (:require [edge.drone :refer :all]))


#_(def world {:depots []
            :jobs []
            :drones {}})

(defn step [world]
  (update-in world [:drones] (partial map update-drone)))
