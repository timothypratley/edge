(ns edge.tools
  (:require [clojure.java.io :refer :all]))

(defn dict-inc [m k]
  (update-in m [k] (fnil inc 0)))
;(dict-inc {:foo 1} :foo)

(defn freq [words]
  (reduce dict-inc {} words))
;(freq (re-seq #"\w+" "the quick brown fox jumped the quick brown chicken quick"))

(def comb (partial merge-with +))
;(comb {:foo 2} {:foo 1})

(defn word-freq
  [f]
  (if (.isDirectory f)
    (reduce comb {} (map word-freq (rest (file-seq f))))
    (when (.endsWith (.getName f) ".clj")
      (freq (re-seq #"\w+" (slurp (.getPath f)))))))
;(word-freq (file "/git/lib-noir/src/noir/util/crypt.clj"))

(defn freq-path
  [path]
  (sort-by last >
           (word-freq (file path))))
;(clojure.pprint/pprint (freq-path "/git/lib-noir"))

;is
;defn
;if
;get
;and
;map
;let
;or
;true/false
;not
;fn
;for
;nil
;any (some)
;assoc
;deftest
;set
;str
;type
;ns (use require refer)
;keys
;new
;apply
;swap!
;key
;atom
;every?
;_
;dissoc
;partial
;vector
;into
;doto
;sort
;switchp


