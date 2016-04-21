(ns org.jordillonch.lib.component.bus.command-bus)

(defprotocol CommandBus
  (register [this command-name handler])
  (unregister [this command-name])
  (unregister-all [this])
  (handle [this command])
  (all-ok? [this responses]))
