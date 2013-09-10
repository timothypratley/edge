(ns edge.repl.scratch
  (:require [edge.core :refer :all]
            [edge.world :refer :all]
            [edge.event-sourcing :refer :all]
            [clojure.repl :refer :all]
            [clojure.test :refer :all]))




(start)


(step)
@world

(@world :missions)
(filter :mission (@world :drones))
(add-missions)

(update-drones)
