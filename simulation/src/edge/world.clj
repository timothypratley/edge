(ns edge.world
  (:require [edge.math :refer :all]
            [edge.event-sourcing :refer :all]
            [edge.commands :refer :all]
            [edge.plan :refer :all]))


(let [all-cargo (vec (concat medicine sample))]
  (defn ^:private rand-cargo []
    (rand-nth all-cargo)))

; TODO: hospital selection is actually free
(defn rand-mission [world]
  (let [cargo (rand-cargo)
        remote (rand-nth (world :remotes))
        ;TODO: use clj-time
        created (java.util.Date.)
        duration 1] ;(hours (rand 64))]
    {:cargo cargo
     :remote remote
     :created created
     :deadline created}))

(defn weighter [world a t]
  (distance (a :location)
            (go-to world (assoc a :mission t))))

(def ticks-per-mission (* 3 60))
(defn add-missions []
  (when (zero? (rand-int ticks-per-mission))
    (create-mission-command (rand-mission @world))
    (assign-missions @world #(weighter @world %1 %2))))


; excercise: this is O(n*log(n)),
; find an O(n) and O(log(n)) solution
#_(defn closest-hospital [world location]
  (first (sort-by #(distance location (% :location))
                  (world :hospitals))))


; excercise: filter drones instead
(defn update-drones []
  (doseq [drone (@world :drones)
          :when (drone :mission)
          :let [{:keys [id location speed]} drone
                dest (go-to @world drone)
                new-location (interpolate location dest speed)
                new-heading (heading (v-sub new-location location))]]
    (update-drone-command id new-location new-heading)
    (pickup-command drone)
    (complete-mission-command drone)))


(defn step []
  (update-drones)
  (add-missions))


