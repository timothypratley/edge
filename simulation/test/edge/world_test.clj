(ns edge.world-test
  (:require [clojure.test :refer :all]
            [edge.world :as world]))

(deftest about-world
  (is (= [2 2]
         (world/choose-waypoint [[1 1] [2 2] [3 3]] [2.1 2.1]))))
