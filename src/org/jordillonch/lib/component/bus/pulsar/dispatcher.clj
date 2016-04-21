(ns org.jordillonch.lib.component.bus.pulsar.dispatcher
  (:use
    co.paralleluniverse.pulsar.core)
  (:require
    [org.jordillonch.lib.component.bus.dispatcher])
  (:refer-clojure :exclude [promise await])
  (:import
    (org.jordillonch.lib.component.bus.dispatcher Dispatcher)))

(defn- get-value [promise]
  @promise)

(defn- get-values [promises]
  "note that you get a lazy sequence of promises values so if you want to raise exceptions,
  you should execute doall"
  (map get-value promises))

(defrecord DispatcherPulsar [handlers]
  Dispatcher
  (register [this event-name handler]
    (let [handlers-for-event-name (get @handlers event-name [])
          new-handlers-for-event-name (conj handlers-for-event-name handler)
          new-handlers (assoc @handlers event-name new-handlers-for-event-name)]
      (reset! handlers new-handlers))
    this)

  (unregister [this name]
    (reset! handlers (dissoc @handlers name))
    this)

  (handle [this event-name params]
    "dispatch event to handlers async and get a lazy sequence of promises values,
    so handlers are executed async and we will just wait for the promises values
    that we want to get"
    (->> (get @handlers event-name [])
         (map (fn [handler] (promise #(try
                                       (handler params)
                                       (catch Exception e e)))))
         (doall)
         (get-values)))

  (all-ok? [_ promises]
    (not-any? #(instance? Exception %) promises))

  (unregister-all [this]
    (reset! handlers {})))

(defn new-dispatcher []
  (map->DispatcherPulsar {:handlers (atom {})}))
