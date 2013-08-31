(defproject edge "0.1.0-SNAPSHOT"
  :description "Using drones to deliver medicine and test samples"
  :url "http://timothypratley.blogspot.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [quil "1.6.0"]
                 [munkres "0.1.0"]]
  :profiles {:dev {:dependencies [[midje "1.5.1"]]}}
  :plugins [[lein-midje "3.0.0"]])


