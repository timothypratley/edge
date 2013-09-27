(ns edge.tools-test
  (:require [midje.sweet :refer :all]
            [edge.tools :refer :all]))

(facts "about tools"
       (fact (dict-inc {:foo 1} :foo)
             => {:foo 2})

       (fact (freq (re-seq #"\w+" "the quick brown fox jumped the quick brown chicken quick"))
             => {"the" 2, "quick" 3, "brown" 2, "fox" 1, "jumped" 1, "chicken" 1})

       (fact (comb {:foo 2} {:foo 1})
             => {:foo 3}))

;(word-freq (file "/git/lib-noir/src/noir/util/crypt.clj"))
;(clojure.pprint/pprint (freq-path "/git/lib-noir"))


