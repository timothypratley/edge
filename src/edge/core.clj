(ns edge.core
  (:require [edge.world :refer :all]
            [edge.draw :refer :all]
            [edge.rand :refer :all]
            [edge.event-sourcing :refer :all]))


#_(sketch-world (iterate step (rand-world [1024 800] 4 50 20)))

(snapshot (rand-world [1024 800] 4 50 20))


(defn start []
  (sketch-world))

#_(defn stop []
  (sketch-stop))

