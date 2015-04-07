(ns edge.tools
  "Investigating which clojure features are most used in the wild"
  (:require [clojure.java.io :refer :all]))

(defn dict-inc [m k]
  (update-in m [k] (fnil inc 0)))

(def comb (partial merge-with +))

(defn word-freq
  [f]
  (if (.isDirectory f)
    (reduce comb {} (map word-freq (rest (file-seq f))))
    (when (.endsWith (.getName f) ".clj")
      (frequencies (re-seq #"\w+" (slurp (.getPath f)))))))

(defn freq-path
  "Reports how often each word occurs in all clj files in path"
  [path]
  (sort-by last >
           (word-freq (file path))))

; I ran this on a well known library to get some statistics on what Clojure
; features are used in the wild, to make sure we focus on the important ones

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
