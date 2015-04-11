(ns edge.website.app.communication
  (:require [edge.website.app.state :as state]
            [edge.website.app.logging :as log]
            [timothypratley.patchin :as patchin]
            [taoensso.sente :as sente]))

(let [{:keys [chsk ch-recv send-fn state]}
      (sente/make-channel-socket! "/chsk"
                                  {:type :auto})]
  (def chsk chsk)
  (def ch-chsk ch-recv)
  (def chsk-send! send-fn)
  (def chsk-state state))

(defn login [user-id]
  (log/debug "Logging in with user-id" user-id (:csrf-token @chsk-state))
  (sente/ajax-call "/login"
                   {:method :post
                    :params {:user-id (str user-id)
                             :csrf-token (:csrf-token @chsk-state)}}
                   (fn [ajax-resp]
                     (if (:?error ajax-resp)
                       (log/debug "Login failed:" ajax-resp)
                       (do
                         (log/debug "Login successful")
                         (sente/chsk-reconnect! chsk))))))


;;;; Routing handlers

(defmulti event-msg-handler :id)
(defn event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (log/debug "Event:" event)
  (event-msg-handler ev-msg))

(defmethod event-msg-handler :default ; Fallback
  [{:as ev-msg :keys [event]}]
  (log/debug "Unhandled event:" event))

(defmethod event-msg-handler :chsk/state
  [{:as ev-msg :keys [?data]}]
  (if (= ?data {:first-open? true})
    (log/debug "Channel socket successfully established!")
    (log/debug "Channel socket state change:" ?data)))

(defmethod event-msg-handler :chsk/recv
  [{:as ev-msg :keys [?data]}]
  (log/debug "Push event from server:" ?data)
  (swap! state/app-state update-in [:model] patchin/patch (second ?data)))

(defmethod event-msg-handler :chsk/handshake
  [{:as ev-msg :keys [?data]}]
  (let [[?uid ?csrf-token ?handshake-data] ?data]
    (log/debug "Handshake:" ?data)))

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
                         (log/debug "Send the super")
                         (chsk-send! [:edge/super {:great "stuff"}]))
             3000)
