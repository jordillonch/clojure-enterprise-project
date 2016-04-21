(ns org.jordillonch.lib.component.bus.domain-event-publisher)

(defprotocol DomainEventPublisher
  (register [this domain-event-name handler])
  (unregister [this domain-event-name])
  (unregister-all [this])
  (publish [this domain-event])
  (all-ok? [this responses]))
