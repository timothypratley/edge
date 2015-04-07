(ns ^:figwheel-no-load edge.website.app.dev
  (:require [edge.website.app.main :as main]
            [figwheel.client :as figwheel :include-macros true]
            [weasel.repl :as weasel]
            [reagent.core :as r]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://localhost:3449/figwheel-ws"
  :jsload-callback main/mount-root)

(weasel/connect "ws://localhost:9001" :verbose true)

(main/init!)
