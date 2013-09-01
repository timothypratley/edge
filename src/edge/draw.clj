(ns edge.draw
  (:require [edge.world :refer :all]
            [quil.core :refer :all]))


(defn setup []
  (text-align :center)
  (rect-mode :center)
  (smooth)
  (frame-rate 60))

(defn draw-hospital [{[x y] :location}]
  (with-translation [x y]
    (text "hospital" 0 -10)
    (stroke 255)
    (stroke-weight 5)
    (fill 255)
    (ellipse 0 0 30 30)
    (stroke 128 0 0)
    (fill 128 0 0)
    (rect 0 0 25 5)
    (rect 0 0 5 25)))

(defn draw-remote [{[x y] :location
                    ms :missions}]
  (with-translation [x y]
    (doseq [[m i] (map vector ms (range))]
      (text (str (m :needs)) 0 (* i 10)))
    (stroke 128)
    (stroke-weight 5)
    (fill 0)
    (ellipse 0 0 10 10)))

; h = (/ Math/PI 4)
(defn draw-drone [{[x y] :location
                   h :heading
                   p :payload}]
  (with-translation [x y]
    (text "drone" 0 -10)
    (with-rotation [h]
      (stroke 0 200 0)
      (stroke-weight 1)
      (fill 0 200 0)
      (triangle -10 0
                0 -5
                10 0)

      ; how would you do this in C#? (possibly a redirect function)
      ;payload
      (apply fill (condp = p
                        nil [0 200 0]
                        :medX [0 0 0]
                        :medY [128 0 0]
                        :medZ [0 0 128]
                        :default [255 0 0]))
      (rect 0 0 10 3))))

(defn draw [world]
  (background-float 200)
  (doseq [h (world :hospitals)]
    (draw-hospital h))
  (doseq [r (world :remotes)]
    (draw-remote r))
  (doseq [d (world :drones)]
    (draw-drone d)))

(defn sketch-world
  [next-world]
  (sketch
    :title "Map of the world"
    :setup setup
    :draw #(draw (next-world))
    :size [1024 800]))
