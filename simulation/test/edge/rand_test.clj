(ns edge.rand-test
  (:require [clojure.test :refer :all]
            [edge.rand :as rand]))

(deftest about-rand
  (is (rand-world [1024 800] 4 25 15)))
