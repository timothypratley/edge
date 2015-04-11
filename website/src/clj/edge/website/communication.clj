(ns edge.website.communication
  (:require [clojure.tools.logging :as log]
            [timothypratley.patchin :as patchin]
            [taoensso.sente :as sente]
            [taoensso.sente.server-adapters.http-kit :refer [sente-web-server-adapter]]))

(defonce router (atom nil))
(defonce app-states (atom {}))

(def test-world {:drones {1 {:name "D200"
                        :location [20 20]
                        :heading (/ Math/PI 4.0)
                        :speed 2}}
            :hospitals {1 {:name "Emergency One Hospital"
                           :location [70 70]}}
            :remotes {1 {:location [120 120]}}
            :missions {1 {:remote-id 1
                          :hospital-id 1
                          :cargo :blood}}})

(defn update-models []
  )

(defn with-patch [app-states uid p]
  (-> app-states
      (update-in [uid :viewpoint] patchin/patch p)
      (assoc-in [uid :model] test-world)))

(let [{:keys [ch-recv send-fn ajax-post-fn ajax-get-or-ws-handshake-fn
              connected-uids]}
      (sente/make-channel-socket! sente-web-server-adapter {})]
  (def ring-ajax-post ajax-post-fn)
  (def ring-ajax-get-or-ws-handshake ajax-get-or-ws-handshake-fn)
  (def ch-chsk ch-recv)
  (def chsk-send! send-fn)
  (def connected-uids connected-uids))

(defmulti event-msg-handler :id)

(defn event-msg-handler* [{:as ev-msg :keys [id ?data event]}]
  (log/info "Event:" event)
  (event-msg-handler ev-msg))

(defn stop-router! []
  (when-let [stop-f @router]
    (stop-f)))

(defn start-router! []
  (stop-router!)
  (reset! router (sente/start-chsk-router! ch-chsk event-msg-handler*)))

(start-router!)

;; Message Handlers

(defmethod event-msg-handler :default ; Fallback
  [{:as ev-msg :keys [event id ?data ring-req ?reply-fn send-fn]}]
  (let [session (:session ring-req)
        uid (:uid session)]
    (log/info "Unhandled event:" event)
    (when ?reply-fn
      (?reply-fn {:umatched-event-as-echoed-from-from-server event}))))

(defmethod event-msg-handler :chsk/uidport-open
  [{:keys [ring-req ?data]}]
  (let [session (:session ring-req)
        uid (:uid session)]
    (chsk-send! uid [:edge/initial (patchin/diff {} test-world)])))

(defmethod event-msg-handler :edge/viewpoint
  [{:keys [ring-req ?data]}]
  (when-let [uid (get-in ring-req [:session :uid])]
    (swap! app-states with-patch uid ?data)))
