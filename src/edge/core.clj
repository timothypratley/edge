(ns edge.core
  (:require [edge.world :refer :all]
            [edge.draw :refer :all]
            [edge.rand :refer :all]
            [edge.drone :refer :all]))


#_(sketch-world (iterate step (rand-world [1024 800] 4 50 20)))
(def world (ref (rand-world [1024 800] 4 50 20)))
(sketch-world world)

(dotimes [i 10000]
  (Thread/sleep 10)
  (dosync (alter world step)))