(ns edge.world
  (:require [edge.math :refer :all]
            [edge.event-sourcing :refer :all]
            [edge.commands :refer :all]
            [edge.plan :refer :all]))


(def medicine #{:medX :medY :medZ})
(def sample #{:blood :urine})

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
     :from (if (medicine cargo) :hospital remote)
     :to (if (medicine cargo) remote :hospital)
     :created created
     :deadline created}))

(defn weighter [world a t]
  (distance (a :location)
            (get-location world a t)))


(def ticks-per-mission (* 3 60))
(defn add-missions []
  (when (zero? (rand-int ticks-per-mission))
    (create-mission-command (rand-mission @world))
    (assign-missions @world #(weighter @world %1 %2))))


; excercise: thread this
; excercise: this is O(n*log(n)),
; find an O(n) and O(log(n)) solution
(defn closest-hospital [world location]
  (first (sort-by #(distance location (% :location))
                  (world :hospitals))))

(defn get-location [world place from]
  (if (= :hospital place)
    ((closest-hospital world from) :location)
    (place :location)))

; nested named :keys destructuring
; excercise: write this with deep binding and no binding
(defn go-to
  "Returns the next location that drone should go to"
  [world drone]
  (let [{:keys [cargo location mission]} drone
        {:keys [from to]} mission
        place (if cargo to from)]
    (get-location world place location)))
;(go-to nil {:cargo :x, :location [1 1], :mission {:from {:location [1 1]}, :to {:location [1 1]}}})

; excercise: filter drones instead
(defn update-drones []
  (doseq [drone (@world :drones)
          :when (drone :mission)
          :let [{:keys [id location speed]} drone
                dest (go-to @world drone)
                new-location (interpolate location dest speed)
                heading (get-heading (v-sub dest location))]]
    (update-drone-command id new-location heading)
    (pickup-command drone)
    (complete-mission-command drone)))


(defn step []
  (update-drones)
  (add-missions))

