(ns org.jordillonch.lib.component.bus.dispatcher)

(defprotocol Dispatcher
  (register [this event-name handler])
  (unregister [this event-name])
  (unregister-all [this])
  (handle [this event-name params])
  (all-ok? [this promises]))
