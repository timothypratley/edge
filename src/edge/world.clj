(ns edge.world
  (:require [edge.drone :refer :all]))


;denormalize
;(let [drones (concat (world :drones) (future-drones world))] )

#_(map assign-closest
     (sort-by :deadline
              (concat (map :remotes :missions world)
                      (map :drone :missions world))))

(defn make-plans [world]
  ;denormalize mission list instead of traversing for it
  (world :missions)
  world)

(defn maybe-done [world drone]
    world)


(defn update-missions [world]
  (maybe-done world nil)
  world)

;; todo this is now a command, move it there
(defn rand-mission [world remote]
  (if (> 0.001 (rand))
    (update-in remote [:missions] conj {:needs :medX})
    remote))

(defn create-missions [world]
  (update-in world [:remotes] #(map (partial rand-mission world) %)))


(def medicine #{:medX :medY :medZ})
(def sample #{:blood :urine})
(def all-cargo (into [] (concat medicine sample)))

(defn ^:private source [cargo hospital remote]
  (if (medicine cargo)
    hospital
    remote))

(defn ^:private choose-cargo []
  (rand-nth all-cargo))

(defn ^:private choose-remote [world]
  (rand-nth (world :remotes)))

(defn ^:private choose-hospital [world]
  (rand-nth (world :hospitals)))

(defn rand-mission [world]
  (let [cargo (choose-cargo)
        remote (choose-remote world)
        hospital (choose-hospital world)
        ;TODO: use clj-time
        created (java.util.Date.)
        duration 1] ;(hours (rand 64))]
    {:cargo cargo
     :from (source cargo hospital remote)
     :to (source cargo remote hospital)
     :created created
     :deadline created}))

(defn maybe-add-missions [world]
  (if (rand)
    (update-in world [:missions] (fnil conj #{}) (rand-mission world))
    world))

(defn step [world]
  (-> world
      (update-in [:drones] #(map update-drone %))
      update-missions
      create-missions
      make-plans))

