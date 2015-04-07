(ns edge.website.server
  (:require [edge.website.routes :as routes]
            [ring.adapter.jetty :as jetty])
  (:gen-class))

(defn -main [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (jetty/run-jetty routes/handler {:port port :join? false})))
