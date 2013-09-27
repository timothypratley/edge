(ns edge.world-test
  (:require [midje.sweet :refer :all]
            [edge.world :refer :all]))


(facts "about world"
       (fact (choose-waypoint [[1 1] [2 2] [3 3]] [2.1 2.1])
             => [2 2]))


