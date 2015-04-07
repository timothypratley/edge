(ns edge.event-sourcing-test
  (:require [clojure.test :refer :all]
            [edge.event-sourcing :as event-sourcing]))

(defmethod accept :test
  [world event]
  :test-world)

(defmethod accept :replace
  [world event]
  :replaced-world)

(deftest raise!-test
  (testing "hydration"
    (is (= :test-world (event-sourcing/hydrate "0"))))
  (testing "raising events"
    (let [event {:token 1}
          published (atom nil)
          on-event #(reset! published %)]
      (event-sourcing/subscribe on-event)
      (event-sourcing/raise! :test event)
      (is (= :test-world @world))
      (is (= (:token @published) 1))))
  (testing "rehydration"
    (event-sourcing/raise! :replace {})
    (is (= :replaced-world (hydrate "0")))))
