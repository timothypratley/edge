(ns edge.event-sourcing
  "Event sourcing http://martinfowler.com/eaaDev/EventSourcing.html
  This file has io, globals and state.
  Fortunately there is not very much of it, and it is concentrated in one place."
  (:require [clojure.java.io :refer :all]
            [clojure.pprint]
            [clojure.edn]))


; order of arguments is important for reduce
; TODO: wish I didn't have to define it here
(defmulti accept
  "Signature for accepting an event, applying it to world.
  This will be implemented by your command/accept pairs.
  Commands check the validity, and raise events.
  accept performs a data transform, updating the world,
  which is called from raise, or when hydrating an event stream."
  (fn [world event] (:event event)))


;TODO: make configurable

(def world
  "The domain, the state of everything."
  (atom nil))

(let [eof (Object.)]
  (defn- read-all
    "A sequence of reading clojure data from a file."
    [^java.io.PushbackReader reader]
    (take-while #(not= eof %)
                (repeatedly #(clojure.edn/read {:eof eof} reader)))))

(defn- data-reader
  "Constructs a PushbackReader for a data file"
  [& more]
  (java.io.PushbackReader.
   (reader (file "data" (apply str more)))))

; example of a closure
(let [current-label (atom nil)
      event-count (atom 0)
      subscriptions (atom #{})
      events-per-snapshot 1000]

  (defn snapshot
    "Switches to a new label and writes out the current world state asynchronously."
    [new-world]
    (let [sf (java.text.SimpleDateFormat. "yyyy_MM_dd__HH_mm_ss__SSS")
          now (java.util.Date.)
          label (.format sf now)]
      (reset! current-label label)
      (reset! event-count 0)
      (reset! world new-world)
      ; write the snapsot asynchronously on another thread to avoid delays
      ; the world we are looking at is immutable
      ; TODO: we do need to know when the state file is fully written though
      (future (io! (with-open [w (writer (file "data" (str label ".state")))]
                     (clojure.pprint/pprint world w))))))

  (defn- store
    "Writes an event to file"
    [event]
    ;(io! (with-open [w (writer (file "data" (str @current-label ".events"))
;                               :append true)]
;           (clojure.pprint/pprint event w)))
    (when (>= (event :seq) events-per-snapshot)
      (snapshot @world)))

  ;TODO: hydration up to a point in time for debugging
  (defn hydrate
    "Load world state and process the event log.
    Specify the event-id for a particular point in time,
    or event-id 0 if you do not want any events applied."
    ([label] (hydrate label nil))
    ([label event-id]
     (reset! current-label label)
     (io! (with-open [state-reader (data-reader label ".state")
                      event-reader (data-reader label ".events")]
            (let [world (clojure.edn/read state-reader)
                  all-events (read-all event-reader)
                  events (if event-id
                           (take-while #(<= (% :seq) event-id) all-events)
                           all-events)]
              (reset! event-count (or (:seq (last events)) 0))
              (reduce accept world events))))))

  (defn hydrate-latest
    "Fully hydrate the most recent world state snapshot and event log."
    []
    (let [get-name #(.getName %)
          file-name (-> "data" file file-seq sort reverse first get-name)
          label (.replace file-name ".state" "")]
      (hydrate label)))

  (defn subscribe [f]
    (swap! subscriptions conj f))

  (defn unsubscribe [f]
    (swap! subscriptions disj f))

  (defn- publish [event]
    (doseq [f @subscriptions]
      (f event)))

  ; exclaimation point because of state change (and io)
  ; TODO: this is the most important function, I don't like burying it down here
  ; the symantics I want are:  May be called from multiple threads, needs to be done in serial, want a return value to indicate success
  (let [o (Object.)]
    (defn raise!
      "Raising an event stores it, publishes it, and updates the world model.
      Publishing allows for external read models to be denormalized, providing CQRS.
      http://martinfowler.com/bliki/CQRS.html"
      [event-type event]
      (assert @current-label "Must call hydrate or snapshot prior to recording events")
      (locking o
        (let [event (assoc event
                      :event event-type
                      :when (java.util.Date.)
                      :seq (swap! event-count inc))]
          (store event)
          (publish event)
          (swap! world accept event))))))

