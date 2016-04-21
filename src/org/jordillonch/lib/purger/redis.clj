(ns org.jordillonch.lib.purger.redis
  (:require
    [taoensso.carmine :as car :refer (wcar)]))

(defn reset [connection]
  (car/wcar connection (car/flushall)))
