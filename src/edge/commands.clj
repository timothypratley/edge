(ns edge.commands
  (:require [edge.event-sourcing :refer :all]))


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
  (= (drone :location)
     (get-in drone [:mission :to :location])))

(defn complete-mission-command
  [drone]
  (if (can-complete-mission drone)
    (raise! :mission-complete
            {:drone-id (drone :id)})))

(defmethod accept :mission-complete
  [world e]
  (-> world
      (update-in [:missions] disj (get-in world [:drone (e :drone-id) :mission]))
      (update-in [:drones (e :drone-id)] dissoc :mission)))



(defn can-pickup
  [drone]
  (= (drone :location)
     (get-in drone [:mission :from :location])))

(defn pickup-command
  [drone]
  (if (can-pickup drone)
    (raise! :pickup
            {:drone-id (drone :id)})))

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



