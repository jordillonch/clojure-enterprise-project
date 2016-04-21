(ns org.jordillonch.lib.component.catacumba.routing-registrator
  (:require
    [catacumba.components :refer [assoc-routes!]]
    [catacumba.handlers :as hs]
    [catacumba.http :as http]
    [com.stuartsierra.component :as component]))

(defn- create-handle-with-dependencies [[handler raw-dependencies] component]
  (let [dependencies (map #(get component %) raw-dependencies)]
    #(apply handler % dependencies)))

; @todo found better way
; @todo improve, now only work for :by-method routings
(defn- update-methods-with-the-handle-with-dependency [component routing]
  (-> routing
      (update-in [2 1 :get] create-handle-with-dependencies component)
      (update-in [2 1 :post] create-handle-with-dependencies component)
      (update-in [2 1 :put] create-handle-with-dependencies component)
      (update-in [2 1 :delete] create-handle-with-dependencies component)
      (update-in [2 1 :patch] create-handle-with-dependencies component)))

(defn- add-dependencies [routes-with-raw-dependencies component]
  (map (partial update-methods-with-the-handle-with-dependency component) routes-with-raw-dependencies))

(defn- add-body-params-handler [routes]
  (conj routes [:any (hs/body-params)]))

(defn my-error-handler
  [context error]
  (http/internal-server-error (.getMessage error)))

(defn- add-error-handler [routes]
  (conj routes [:error my-error-handler]))

(defrecord RoutingRegistrator [server routes]
  component/Lifecycle
  (start [component]
    (->> (add-dependencies routes component)
         (add-body-params-handler)
         (add-error-handler)
         (assoc-routes! server ::web))
    component)

  (stop [this]
    this))

;Routes
;[
; [:prefix "storage/:namespace/:key" [:by-method
;                                     {:get    [controller-get [:oracle]]
;                                      :put    [controller-put [:command-bus]]
;                                      :delete [controller-delete [:command-bus]]}]]
;]
(defn register-routes [routes]
  (map->RoutingRegistrator {:routes routes}))
