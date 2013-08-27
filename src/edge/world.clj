(ns edge.world
  (:require [edge.drone :refer :all]))


#_(def world {:depots []
            :jobs []
            :drones {}})

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
      (update-in [:drones] (partial map update-drone))
      (maybe-add-missions)
      ;TODO: (plan)
      ))

