(ns edge.command-test
  (:require [clojure.test :refer :all]
            [edge.command :refer :all]
            [edge.event-sourcing :refer :all]))

(deftest command-test
  (testing "commands"
    (let [world {}
          mission {}
          drone {}]
      (snapshot world)
      (is (not (nil? (assign-mission-command mission drone))))
      (is (not (nil? (complete-mission-command mission drone)))))))

;TODO: this doesn't belong here
;(run-tests)
