(ns org.jordillonch.lib.component.bus.pulsar.command-bus
  (:require
    [org.jordillonch.lib.component.bus.command-bus :refer :all]
    [org.jordillonch.lib.component.bus.dispatcher :as dispatcher]
    [org.jordillonch.lib.component.bus.pulsar.dispatcher :refer :all]
    [com.stuartsierra.component :refer :all]))

(defrecord CommandBusPulsar [dispatcher]
  CommandBus
  (register [this command-name handler]
    (dispatcher/register dispatcher command-name handler)
    this)

  (unregister [this command-name]
    (dispatcher/unregister dispatcher command-name)
    this)

  (handle [_ [command-name command]]
    (dispatcher/handle dispatcher command-name command))

  (all-ok? [_ responses]
    (dispatcher/all-ok? dispatcher responses))

  Lifecycle
  (start [this] this)
  (stop [this]
    (dispatcher/unregister-all dispatcher)
    this))

(defn new-command-bus []
  (map->CommandBusPulsar {:dispatcher (new-dispatcher)}))
