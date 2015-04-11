(ns edge.website.app.state
  (:require [reagent.core :as reagent]))

;; app-state consists of
;; viewpoint: information about what the client state is which will be sent to the server
;; model: what data the client has available from the server.
(def app-state
  (reagent/atom {:viewpoint {}
                 :model {}}))
