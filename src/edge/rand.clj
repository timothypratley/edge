(ns edge.rand)


(def tau (* 2 Math/PI))
; PI is wrong http://www.youtube.com/watch?v=jG7vhMMXagQ

(defn rand-loc [dimensions]
  (map rand-int dimensions))

(defn rand-hospital [dimensions]
  {:location (rand-loc dimensions)})

(defn rand-remote [dimensions]
  {:location (rand-loc dimensions)})

(defn rand-drone [dimensions hospitals]
  {:location (:location (rand-nth hospitals))
   :heading (rand tau)
   :speed 0.01
   :goal (rand-loc dimensions)})

(defn rand-world
  "Generate a random world. Arguments are the desired number of each type."
  [dimensions hospitals remotes drones]
  (let [hs (repeatedly hospitals #(rand-hospital dimensions))
        rs (repeatedly remotes #(rand-remote dimensions))
        ds (repeatedly drones #(rand-drone dimensions hs))]
    {:dimensions dimensions
     :hospitals hs
     :remotes rs
     :drones ds}))

(rand-world [1024 800] 4 25 15)
