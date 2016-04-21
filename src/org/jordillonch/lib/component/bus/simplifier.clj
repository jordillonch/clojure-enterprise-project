(ns org.jordillonch.lib.component.bus.simplifier
  (:require
    [org.jordillonch.lib.component.bus.handler-registrator :refer [register-handlers]]))

(defn- get-dependencies-from-handlers [definitions]
  (map (fn [[_ [_ dependencies]]] dependencies) definitions))

(defn- get-all-dependencies-from-handlers [definitions]
  (-> definitions
      (get-dependencies-from-handlers)
      (flatten)
      (distinct)))

(defn- filter-definitions-by-key [definitions key-to-filter]
  (->> (filter (fn [[key _]] (.contains (str key) key-to-filter)) definitions)
       (map (fn [[_ handlers]] handlers))
       (apply merge)))

(defn- remove-simplified-handlers [definitions]
  (->> (filter (fn [[key _]] (not (.contains (str key) "handlers-"))) definitions)
       (map (fn [[key value]] {key value}))
       (apply merge)))

(defn simplified-handler-definitions->system-definitions [definitions]
  (let [oracle-handler-definitions                (filter-definitions-by-key definitions "handlers-oracle")
        command-bus-handler-definitions           (filter-definitions-by-key definitions "handlers-command-bus")
        domain-event-handler-definitions          (filter-definitions-by-key definitions "handlers-domain-event")
        oracle-dependencies                       (get-all-dependencies-from-handlers oracle-handler-definitions)
        command-bus-dependencies                  (get-all-dependencies-from-handlers command-bus-handler-definitions)
        domain-event-dependencies                 (get-all-dependencies-from-handlers domain-event-handler-definitions)
        all-dependencies                          (distinct (flatten [oracle-dependencies command-bus-dependencies domain-event-dependencies]))
        register-handler-definitions              {:oracle       oracle-handler-definitions
                                                   :command-bus  command-bus-handler-definitions
                                                   :domain-event domain-event-handler-definitions
                                                   }
        register-handler-dependencies             (into [:oracle :command-bus :domain-event-publisher] all-dependencies)
        bus-handler-register-component-definition {:bus-handler-register {:component    (register-handlers register-handler-definitions)
                                                                          :dependencies register-handler-dependencies}}]
    (-> (remove-simplified-handlers definitions)
        (merge bus-handler-register-component-definition))))
