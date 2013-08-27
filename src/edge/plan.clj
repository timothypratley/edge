(ns edge.plan
  (:require [edge.world :refer :all]
            [munkres :refer :all]))


(defn distance [a t]
  ;TODO: make a vector sub fuction?
  ;TODO: simplify
  (Math/sqrt (reduce + (map #(* % %) (map - (get-in t [:from :location]) (a :location))))))

(defn assign [world [drone mission]]
  (update-in world [:drones (drone :id) :missions] (fnil conj #{}) mission))

(defn plan [world]
  (let [agents (world :drones)
        tasks (seq (world :missions))
        solution (solve agents tasks
           (weight-matrix agents tasks distance))]
    (reduce assign world (solution :assignments))))

