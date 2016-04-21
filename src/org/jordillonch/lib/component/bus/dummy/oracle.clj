(ns org.jordillonch.lib.component.bus.dummy.oracle
  (:require
    [org.jordillonch.lib.component.bus.dispatcher :as dispatcher]
    [org.jordillonch.lib.component.bus.dummy.dispatcher :refer :all]
    [org.jordillonch.lib.component.bus.oracle :refer :all]))

; @fixme now is using the dispatcher implementation that allow multiple handlers
; @fixme that not make sense for the oracle and it should not be allowed
(defrecord OracleDummy [dispatcher]
  Oracle
  (register [this query-name handler]
    (dispatcher/register dispatcher query-name handler)
    this)

  (unregister [this query-name]
    (dispatcher/unregister dispatcher query-name)
    this)

  (ask [_ [query-name query]]
    (-> (dispatcher/handle dispatcher query-name query)
        (first))))

(defn new-oracle-dummy []
  (map->OracleDummy {:dispatcher (new-dispatcher-dummy)}))
