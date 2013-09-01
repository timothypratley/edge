(ns edge.command
  (:require [edge.event-sourcing :refer :all]
            [edge.drone :refer :all]))


; TODO: commands should return success/failure
(defn complete-mission-command
  [drone mission]
  (if (and (drone :fly-to) (= (drone :location) (drone :fly-to)))
    (raise! :mission-complete
            {:drone-id (drone :id)
             :remote-id (mission :remote-id)
             :mission-id (mission :id)})
    world))

(defmethod accept :mission-completed
  [e world]
  (-> world
      (dissoc :drones (e :drone-id) :fly-to)
      (dissoc :remotes (e :remote-id) :missions (e :mission-id))))


(defn assign-mission-command
  [mission drone]
  ;TODO: condition
  (if true
    (raise! :mission-assigned
            {:drone-id (drone :id)
             :mission-id (mission :id)})))

(defmethod accept :mission-assigned
  [e world]
  (-> world
      (assoc :drones (e :drone-id) :fly-to (e :location))
      (assoc :remotes (e :remote-id) :missions (e :mission-id))))


(defn create-mission-command
  [remote mission]
  (if true
    (raise! :mission-created
            {:remote-id (remote :id)
             :needs (mission :needs)})))

(defmethod accept :mission-created
  [e world]
  (-> world
      (assoc :remotes (e :remote-id) :missions (e :mission-id) (e :mission))))


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


(defn step [world]
  (-> world
      (update-in [:drones] #(map update-drone %))
      update-missions
      create-missions
      make-plans))
