(ns edge.draw
  (:require [quil.core :refer :all]
            [edge.core :refer :all]))

(defn setup []
  (rect-mode :center)
  (smooth)
  (frame-rate 60))

(defn draw-hospital [{[x y] :location}]
  (stroke 255)
  (stroke-weight 5)
  (fill 255)
  (ellipse x y 30 30)
  (stroke 128 0 0)
  (fill 128 0 0)
  (rect x y 25 5)
  (rect x y 5 25))

(defn draw-remote [{[x y] :location}]
  (stroke 128)
  (stroke-weight 5)
  (fill 0)
  (ellipse x y 10 10))

; h = (/ Math/PI 4)
(defn draw-drone [{[x y] :location h :heading}]
  (with-translation [x y]
    (with-rotation [h]
      (stroke 0 200 0)
      (stroke-weight 1)
      (fill 0 200 0)
      (triangle -10 0
                0 -5
                10 0)
      (fill 0)
      (rect 0 0 10 3))))

(defn draw []
  (background-float 200)
  (doseq [h (@world :hospitals)]
    (draw-hospital h))
  (doseq [r (@world :remotes)]
    (draw-remote r))
  (doseq [d (@world :drones)]
    (draw-drone d))
  (step))

(defsketch example
  :title "Map of the world"
  :setup setup
  :draw draw
  :size [1024 800])