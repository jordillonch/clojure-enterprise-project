(ns org.jordillonch.lib.component.bus.oracle)

(defprotocol Oracle
  (register [this query-name handler])
  (unregister [this query-name])
  (ask [this query]))
