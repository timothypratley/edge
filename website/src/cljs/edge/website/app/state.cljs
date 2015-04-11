(ns edge.website.app.state
  (:require [reagent.core :as reagent]))

(def app-state (reagent/atom {:world {}}))
