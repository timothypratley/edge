(ns edge.rand-test
  (:require [midje.sweet :refer :all]
            [edge.rand :refer :all]))

(facts "about rand"
       (fact (rand-world [1024 800] 4 25 15)
             => truthy))

