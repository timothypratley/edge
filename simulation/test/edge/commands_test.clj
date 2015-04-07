(ns edge.commands-test
  (:require [clojure.test :refer :all]
            [edge.commands :as commands]
            [edge.event-sourcing :as event-sourcing]))

(deftest about-commands
  (let [world {}
        mission {}
        drone {}]
    (event-sourcing/snapshot world)
    (is (commands/assign-mission-command mission drone))
    (is (commands/complete-mission-command drone))))

(deftest about-accept
  (is (event-sourcing/accept {} {:event :mission-assigned}))
  (is (event-sourcing/accept {} {:event :pickup :drone-id 1})))
