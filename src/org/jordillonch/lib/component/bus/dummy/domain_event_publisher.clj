(ns org.jordillonch.lib.component.bus.dummy.domain-event-publisher
  (:require
    [org.jordillonch.lib.component.bus.domain-event-publisher :refer :all]
    [org.jordillonch.lib.component.bus.dispatcher :as dispatcher]
    [org.jordillonch.lib.component.bus.dummy.dispatcher :refer :all]))

(defrecord DomainEventPublisherDummy [dispatcher]
  DomainEventPublisher
  (register [this domain-event-name handler]
    (dispatcher/register dispatcher domain-event-name handler)
    this)

  (unregister [this domain-event-name]
    (dispatcher/unregister dispatcher domain-event-name)
    this)

  (publish [this [domain-event-name domain-event]]
    (dispatcher/handle dispatcher domain-event-name domain-event)
    this))

(defn new-domain-event-publisher-dummy []
  (map->DomainEventPublisherDummy {:dispatcher (new-dispatcher-dummy)}))
