(ns edge.website.app.communication
  (:require [taoensso.sente :as sente]))

;; TODO: shouldn't need this??
(enable-console-print!)

(let [{:keys [chsk ch-recv send-fn state]}
      (sente/make-channel-socket! "/chsk"
                                  {:type :auto})]
  (def chsk chsk)
  (def ch-chsk ch-recv)
  (def chsk-send! send-fn)
  (def chsk-state state))

(defn login [user-id]
  (println "Logging in with user-id" user-id (:csrf-token @chsk-state))
  (sente/ajax-call "/login"
                   {:method :post
                    :params {:user-id (str user-id)
                             :csrf-token (:csrf-token @chsk-state)}}
                   (fn [ajax-resp]
                     (if (:?error ajax-resp)
                       (println "Login failed:" ajax-resp)
                       (do
                         (println "Login successful")
                         (sente/chsk-reconnect! chsk))))))


;;;; Routing handlers

(defmulti event-msg-handler :id)
(defn event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (println "Event:" event)
  (event-msg-handler ev-msg))

(defmethod event-msg-handler :default
  [ev-msg]
  (println "YO!" (:id ev-msg)))

(def router (atom nil))
(defn stop-router! []
  (when-let [stop-f @router] (stop-f)))
(defn start-router! []
  (stop-router!)
  (reset! router (sente/start-chsk-router! ch-chsk event-msg-handler*)))

(start-router!)

(.setTimeout js/window (fn []
                         (login "tim"))
             1000)
(.setTimeout js/window (fn []
                         (println "Send the super")
                         (chsk-send! [:edge/super {:great "stuff"}]))
             3000)
