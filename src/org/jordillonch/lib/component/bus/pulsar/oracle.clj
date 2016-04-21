(ns org.jordillonch.lib.component.bus.pulsar.oracle
  (:require
    [org.jordillonch.lib.component.bus.dispatcher :as dispatcher]
    [org.jordillonch.lib.component.bus.oracle :refer :all]
    [org.jordillonch.lib.component.bus.pulsar.dispatcher :refer :all]
    [com.stuartsierra.component :refer :all])
  (:use
    [slingshot.slingshot :only [throw+]]))

; @fixme now is using the dispatcher implementation that allow multiple handlers
; @fixme that not make sense for the oracle and it should not be allowed
(defrecord OraclePulsar [dispatcher]
  Oracle
  (register [this query-name handler]
    (dispatcher/register dispatcher query-name handler)
    this)

  (unregister [this query-name]
    (dispatcher/unregister dispatcher query-name)
    this)

  (ask [_ [query-name query]]
    (let [response (-> (dispatcher/handle dispatcher query-name query)
                       (first))]
      (if (instance? Exception response)
        (throw+ response)
        response)))

  Lifecycle
  (start [this] this)
  (stop [this]
    (dispatcher/unregister-all dispatcher)
    this))

(defn new-oracle []
  (map->OraclePulsar {:dispatcher (new-dispatcher)}))
