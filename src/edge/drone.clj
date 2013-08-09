(ns edge.drone)


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
  (let [squares (map * dims dims)
        length (Math/sqrt (reduce + squares))]
    (map #(/ % length) dims)))

;(normalize [3 4])
;(normalize [3 4 5])

(defn interpolate [a b step]
  (let [dir (map - b a)
        move (normalize dir)]
    (map + a move)))

;(interpolate [3 4] [30 40] 1)

(defn fly [drone]
  (-> drone
      (update-in [:location] #(interpolate % (drone :goal) (drone :speed)))
      ))

(defn update-drone [drone]
  (if (drone :goal)
    (fly drone)
    drone))
