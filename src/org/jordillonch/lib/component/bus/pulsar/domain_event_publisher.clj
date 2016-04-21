(ns org.jordillonch.lib.component.bus.pulsar.domain-event-publisher
  (:require
    [org.jordillonch.lib.component.bus.domain-event-publisher :refer :all]
    [org.jordillonch.lib.component.bus.dispatcher :as dispatcher]
    [org.jordillonch.lib.component.bus.pulsar.dispatcher :refer :all]
    [com.stuartsierra.component :refer :all]))

(defrecord DomainEventPublisherPulsar [dispatcher]
  DomainEventPublisher
  (register [this domain-event-name handler]
    (dispatcher/register dispatcher domain-event-name handler)
    this)

  (unregister [this domain-event-name]
    (dispatcher/unregister dispatcher domain-event-name)
    this)

  (publish [_ [domain-event-name domain-event]]
    (dispatcher/handle dispatcher domain-event-name domain-event))

  (all-ok? [_ responses]
    (dispatcher/all-ok? dispatcher responses))

  Lifecycle
  (start [this] this)
  (stop [this]
    (dispatcher/unregister-all dispatcher)
    this))

(defn new-domain-event-publisher []
  (map->DomainEventPublisherPulsar {:dispatcher (new-dispatcher)}))
