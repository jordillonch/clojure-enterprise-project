(ns org.jordillonch.lib.component.bus.dummy.dispatcher
  (:import
    (org.jordillonch.lib.component.bus.dispatcher Dispatcher)))

(defrecord DispatcherDummy [handlers]
  Dispatcher
  (register [this event-name handler]
    (let [handlers-for-event-name     (get @handlers event-name [])
          new-handlers-for-event-name (conj handlers-for-event-name handler)
          new-handlers                (assoc @handlers event-name new-handlers-for-event-name)]
      (reset! handlers new-handlers)
      this))

  (unregister [this name]
    (reset! handlers (dissoc @handlers name))
    this)

  (handle [_ event-name params]
    (->> (get @handlers event-name [])
         (map #(% params))
         (doall))))

(defn new-dispatcher-dummy []
  (map->DispatcherDummy {:handlers (atom {})}))
