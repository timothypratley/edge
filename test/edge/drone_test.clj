(ns edge.plan-test
  (:require [clojure.test :refer :all]
            [edge.drone :refer :all]))

(testing "normalize"
  (is (= (normalize [0 3]) [0 1]))
  (is (= (normalize [0 0 1]) [0 0 1]))
  (is (= (normalize [-1.1] [1]))))


(run-tests)
