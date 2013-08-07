(ns edge.core
  (:require [edge.draw :refer :all]
            [edge.rand :refer :all]
            [edge.drone :refer :all]))


(def world (ref (rand-world [1024 800] 4 50 20)))
(sketch-world world)

(defn step []
  (dosync (alter world
                 #(update-in % [:drones] (partial map update)))))

(@world :drones)
(dotimes [i 1000]
  (Thread/sleep 10)
  (step))

