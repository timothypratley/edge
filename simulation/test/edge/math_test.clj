(ns edge.math-test
  (:require [clojure.test :refer :all]
            [edge.math :as math]))

(deftest about-vector-math
  (is (= 4
         (square 2)))

  (is (= [4 4]
         (v-square [2 2])))

  (is (= [0 0]
         (v-sub [1 1] [1 1])))

  (is (= [2 2]
         (v-add [1 1] [1 1])))

  (is (= 5.0
         (v-length [3 4])))

  (is (= 1.1
         (v-length [1.1])))

  (is (= [4 6]
         (scale [2 3] 2)))

  (is (= 1.0
         (distance [1 1] [1 2])))

  (is (= [0.0 1.0]
         (normalize [0 3])))

  (is (= [0.0 0.0 1.0]
         (normalize [0 0 1])))

  (is (= [-1.0]
         (normalize [-1.1])))

  (is (= [0 1.0]
         (interpolate [0 0] [0 10] 1)))

  (is (= Math/PI
         (heading [0 1]))))
