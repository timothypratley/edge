(ns edge.math)


(defn v-sub
  "Subtraction is the difference in each dimension of the supplied vectors"
  [& vs]
  (apply map - vs))

(defn v-add
  "Addition is the sum in each dimension of the supplied vectors"
  [& vs]
  (apply map + vs))

(defn square
  "Squares a number"
  [x]
  (* x x))

(defn v-square
  "Squares each dimension of vector"
  [v]
  (map square v))

; excercise
(defn v-square
  "Squares each dimension of vector"
  [v]
  (map * v v))

; excercise: write this as nested and let chained
(defn v-length
  "The length of a vector is the root of the sum of squares of dimensions"
  [v]
  (Math/sqrt (reduce + (v-square v))))

(defn scale
  [v c]
  (for [dim v]
    (* dim c)))

(defn distance
  "The cartesian distance between two points is the root of the sum of squares
  of the difference in each dimension"
  ([a b]
   (v-length (v-sub a b)))
  ([a b & more]
   (+ (distance a b) (apply distance b more))))

;; bad:  x and y must be extracted
;TODO: show in simpler function
(defn normalize
  "Divide all dimensions by the sum of squares"
  [v]
  (let [x (first v)
        y (second v)
        length (Math/sqrt (+ (* x x) (* y y)))]
    [(/ x length) (/ y length)]))

(defn normalize
  "Divide all dimensions by the sum of squares"
  [[x y]]
  (let [length (Math/sqrt (+ (* x x) (* y y)))]
    [(/ x length) (/ y length)]))

(defn normalize
  "Divide all dimensions by the sum of squares"
  [dims]
  (let [squares (v-square dims)
        length (Math/sqrt (reduce + squares))
        by-length #(/ % length)]
    (map by-length dims)))

;; for is like map combined with lambda + let, when and while (list comprehension)
(defn normalize
  "Returns a vector of length 1 with the same direction
  (the zero vector cannot be normalized)"
  [v]
  (let [length (v-length v)]
    (for [dim v]
      (/ dim length))))

;(normalize [3 4])
;(normalize [3 4 5])

(defn interpolate
  "Returns a new position moving from a to b by step length"
  [a b step]
  (if (< (distance a b) step)
    b
    (-> (v-sub b a)
        normalize
        (scale step)
        (v-add a))))

;(interpolate [3 4] [30 40] 1)

(defn get-heading
  [[x y]]
  (+ (Math/atan2 y x) (/ Math/PI 2)))



