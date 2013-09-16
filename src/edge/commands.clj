(ns edge.commands
  (:require [edge.event-sourcing :refer :all]
            [edge.math :refer :all]))
;; TODO: don't like refering these
(def medicine #{:medX :medY :medZ})
(def sample #{:blood :urine})

(defn choose-waypoint
  ([waypoints from]
   (apply min-key #(distance from %) waypoints))
  ([waypoints from to]
   (apply min-key #(distance from % to) waypoints)))

; nested named :keys destructuring
; excercise: write this with deep binding and no binding
(defn go-to
  "Returns the next location that drone should go to"
  [world drone]
  (let [{:keys [cargo location mission]} drone
        rloc (get-in mission [:remote :location])
        mcargo (mission :cargo)
        hlocs (map :location (world :hospitals))]
    (if cargo
      (if (medicine mcargo)
        rloc
        (choose-waypoint hlocs location)) ; TODO: nearest next job
      (if (medicine mcargo)
        (choose-waypoint hlocs location rloc)
        rloc))))

;(go-to nil {:cargo :x, :location [1 1], :mission {:from {:location [1 1]}, :to {:location [1 1]}}})


(defn update-drone-command
  [drone-id location heading]
  (raise! :drone-update
          {:drone-id drone-id
           :heading heading
           :location location}))

(defmethod accept :drone-update
  [world {:keys [drone-id location heading]}]
  (update-in world [:drones drone-id]
             #(-> %
               (assoc :location location)
               (assoc :heading heading))))



(defn can-complete-mission
  [drone]
  (and (drone :cargo)
       (= (drone :location)
          (go-to @world drone))))

(defn complete-mission-command
  [drone]
  (if (can-complete-mission drone)
    (raise! :mission-complete
            {:drone-id (drone :id)})))

(defmethod accept :mission-complete
  [world e]
  (-> world
      (update-in [:missions] disj (get-in world [:drone (e :drone-id) :mission]))
      (update-in [:drones (e :drone-id)] dissoc :mission :cargo)))



; TODO: using @world is cheezy
(defn can-pickup
  [drone]
  (and (not (drone :cargo))
       (if (medicine (get-in drone [:mission :cargo]))
         (some #(= (drone :location) %)
               (map :location (@world :hospitals)))
         (= (drone :location)
            (get-in drone [:mission :remote :location])))))

(defn pickup-command
  [drone]
  (if (can-pickup drone)
    (raise! :pickup
            {:drone-id (drone :id)})))

;TODO: flag the cargo as taken in the mission??
(defmethod accept :pickup
  [world e]
  (assoc-in world [:drones (e :drone-id) :cargo]
            (get-in world [:drones (e :drone-id) :mission :cargo])))



(defn assign-mission-command
  [drone mission]
  (raise! :mission-assigned
          {:drone-id (drone :id)
           :mission mission}))

(defmethod accept :mission-assigned
  [world e]
  (assoc-in world [:drones (e :drone-id) :mission]
            (e :mission)))



(defn create-mission-command
  [mission]
  (raise! :mission-created
          {:mission mission}))

(defmethod accept :mission-created
  [world e]
  (update-in world [:missions]
             (fnil conj #{}) (e :mission)))








