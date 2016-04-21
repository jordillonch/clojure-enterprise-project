(ns org.jordillonch.lib.component.bus.handler-registrator
  (:require
    [org.jordillonch.lib.component.bus.command-bus :as command-bus]
    [org.jordillonch.lib.component.bus.domain-event-publisher :as domain-event-publisher]
    [org.jordillonch.lib.component.bus.oracle :as oracle]
    [com.stuartsierra.component :as component]))

(defn- get-dependencies [dependencies component-dependencies]
  (remove nil? (map #(get component-dependencies %) dependencies)))

(defn- handler-with-dependencies [handler dependencies component-dependencies]
  #(apply handler % (get-dependencies dependencies component-dependencies)))

(defn register-oracle-handlers [oracle handlers component-dependencies]
  (dorun
    (map
      (fn [[query-name [handler dependencies]]]
        (oracle/register oracle query-name (handler-with-dependencies handler dependencies component-dependencies)))
      handlers)))

(defn register-command-bus-handlers [command-bus handlers component-dependencies]
  (dorun
    (map
      (fn [[command-name [handler dependencies]]]
        (command-bus/register command-bus command-name (handler-with-dependencies handler dependencies component-dependencies)))
      handlers)))

(defn register-domain-event-handlers [domain-event-publisher handlers component-dependencies]
  (dorun
    (map
      (fn [[domain-event-name [handler dependencies]]]
        (domain-event-publisher/register domain-event-publisher domain-event-name (handler-with-dependencies handler dependencies component-dependencies)))
      handlers)))

(defrecord BusHandlerRegistrator [oracle command-bus domain-event-publisher handlers]
  component/Lifecycle
  (start [component]
    (register-oracle-handlers oracle (get handlers :oracle []) component)
    (register-command-bus-handlers command-bus (get handlers :command-bus []) component)
    (register-domain-event-handlers domain-event-publisher (get handlers :domain-event []) component)
    component)

  ;@todo
  (stop [component]
    ;(unregister command-bus oracle domain-event-publisher handlers)
    component
    ))

;Handlers:
;{:oracle                 [{:query   :query-storage-find
;                           :handler [storage-find-handler [:database-main]}]
; :command-bus            [{:command :command-storage-update
;                           :handler [storage-update-handler [:database-main]}
;                          {:command :command-storage-delete
;                           :handler [storage-delete-handler [:database-main]}]
; :domain-event-publisher [{:domain-event :domain-event-storage-updated
;                           :handler      [do-something-on-storage-updated]}]}
(defn register-handlers [handlers]
  (map->BusHandlerRegistrator {:handlers handlers}))
