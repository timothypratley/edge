(ns edge.core)


(def world {:depots []
            :jobs []
            :drones {}})


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

(defn update [drone]
  (if (drone :goal)
    (fly drone)
    drone))

(defn rand-location []
  [(int (rand 1024)) (int (rand 800))])

(defn generate-hospital []
  {:location (rand-location)})

(defn generate-remote []
  {:location (rand-location)})

(def tau (* 2 Math/PI))
(defn generate-drone []
  {:location (rand-location)
   :heading (rand tau)
   :speed 0.01
   :goal (rand-location)})

(defn generate-world [hospitals remotes drones]
  {:hospitals (repeatedly hospitals generate-hospital)
   :remotes (repeatedly remotes generate-remote)
   :drones (repeatedly drones generate-drone)})

(def world (ref (generate-world 4 50 20)))

(defn step []
  (dosync (alter world
                 #(update-in % [:drones] (partial map update)))))

(@world :drones)
(step)
