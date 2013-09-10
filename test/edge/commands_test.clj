(ns edge.commands-test
  (:require [midje.sweet :refer :all]
            [edge.commands :refer :all]
            [edge.event-sourcing :refer :all]))

(facts "about commands"
       (let [world {}
          mission {}
          drone {}]
         (snapshot world)
         (fact (assign-mission-command mission drone) => truthy)
         (fact (complete-mission-command drone) => truthy)))

(facts "about accept"
       (fact (accept {} {:event :assign-mission}) => truthy))
