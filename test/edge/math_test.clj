(ns edge.math-test
  (:require [clojure.test :refer :all]
            [edge.math :refer :all]
            [midje.sweet :refer :all]))

(facts "about vector math"
       (fact (v-sub [1 1] [1 1]) => [0 0])
       (fact (v-add [1 1] [1 1]) => [2 2])
       (fact (v-square [2 2]) => [4 4])
       (fact (v-length [3 4]) => 5.0)
       (fact (v-length [1.1]) => 1.1)
       (fact (normalize [0 3]) => [0.0 1.0])
       (fact (normalize [0 0 1]) => [0.0 0.0 1.0])
       (fact (normalize [-1.1]) => [-1.0]))
