(ns edge.draw
  "Drawing the location of drones, hospitals and remotes"
  (:require [edge.world :as world]
            [quil.core :refer :all]))


(defn draw-hospital
  "Draw a hospital as a red circle with a white +"
  [{[x y] :location
    n :name}]
  (with-translation [x y]
    (stroke 255)
    (stroke-weight 5)
    (fill 255)
    (ellipse 0 0 30 30)
    (stroke 128 0 0)
    (fill 128 0 0)
    (text n 0 -25)
    (rect 0 0 25 5)
    (rect 0 0 5 25)))

(defn draw-remote
  "Draw a remote as a grey circle"
  [{[x y] :location} missions]
  (with-translation [x y]
    (stroke 128)
    (stroke-weight 5)
    (fill 0)
    (doseq [[m i] (map vector missions (iterate inc 2))]
      (text (str (m :cargo)) 0 (* i 10)))
    (ellipse 0 0 10 10)))

;; condp example
(defn draw-drone
  "Draw a drone as a green wedge with color coded cargo"
  [{[x y] :location
    heading :heading
    cargo :cargo
    label :name}]
  (with-translation [x y]
    (fill 0 100 0)
    (text label 0 -10)
    (with-rotation [heading]
      (stroke 0 200 0)
      (stroke-weight 1)
      (fill 0 200 0)
      (triangle -10 0
                0 -5
                10 0)
      ; how would you do this in C#? (possibly a redirect function)
      (apply fill (condp = cargo
                    nil [0 200 0]
                    :medX [0 0 0]
                    :medY [128 0 0]
                    :medZ [0 0 128]
                    :urine [255 255 0]
                    :blood [255 0 0]
                    [255 255 255]))
      (rect 0 0 10 3))))

;; TODO: make this a multimethod?
(defn draw-world
  "Draw everything in the world"
  [world]
  (background-float 200)
  (doseq [h (world :hospitals)]
    (draw-hospital h))
  (doseq [r (world :remotes)
          :let [missions (filter #(= (% :remote) r) (world :missions))]]
    (draw-remote r missions))
  (doseq [d (world :drones)]
    (draw-drone d)))

(defn setup []
  (text-align :center)
  (rect-mode :center)
  (smooth)
  (frame-rate 60))

(defn draw []
  (draw-world @world/world)
  (world/step))

(defn sketch-world
  "Open an applet window to display the world"
  []
  (sketch
    :title "Map of the world"
    :setup setup
    :draw draw
    :size [1024 800]))
