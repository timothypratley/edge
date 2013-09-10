(ns edge.examples-test)

(re-seq #"\w+" "the quick brown fox")

(zipmap [:a :b :c] [1 2 3])
(into {} [[:a 1] [:b 2] [:c 3]])
(into [1 2] [3 4 5])
((comp reverse sort) [2 1 3])


(clojure.pprint/pprint (zipmap [:a :b :c :d :e :f] (repeatedly rand)))

(with-out-str (clojure.pprint/pprint {:a 1 :b 2}))

(spit "test.txt" "some text that we wrote to a file")
(slurp "test.txt")


