(ns edge.tools-test
  (:require [clojure.test :refer :all]
            [edge.tools :as tools]))

(deftest about-tools
  (is (= {:foo 2}
         (tools/dict-inc {:foo 1} :foo)))

  (is (= {:foo 3}
         (tools/comb {:foo 2} {:foo 1}))))

;;(word-freq (file "/git/lib-noir/src/noir/util/crypt.clj"))
;;(clojure.pprint/pprint (freq-path "/git/lib-noir"))
