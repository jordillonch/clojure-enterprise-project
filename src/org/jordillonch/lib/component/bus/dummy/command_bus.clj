(ns org.jordillonch.lib.component.bus.dummy.command-bus
  (:require
    [org.jordillonch.lib.component.bus.command-bus :refer :all]
    [org.jordillonch.lib.component.bus.dispatcher :as dispatcher]
    [org.jordillonch.lib.component.bus.dummy.dispatcher :refer :all]))

(defrecord CommandBusDummy [dispatcher]
  CommandBus
  (register [this command-name handler]
    (dispatcher/register dispatcher command-name handler)
    this)

  (unregister [this command-name]
    (dispatcher/unregister dispatcher command-name)
    this)

  (handle [_ [command-name command]]
    (dispatcher/handle dispatcher command-name command)))

(defn new-command-bus-dummy []
  (map->CommandBusDummy {:dispatcher (new-dispatcher-dummy)}))
