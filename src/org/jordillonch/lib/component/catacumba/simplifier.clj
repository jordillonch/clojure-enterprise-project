(ns org.jordillonch.lib.component.catacumba.simplifier
  (:require
    [org.jordillonch.lib.component.catacumba.routing-registrator :refer :all]))

(defn- get-all-routings [definition-key-to-match system-with-routings]
  (->> system-with-routings
       (filter (fn [[key _]] (.contains (str key) definition-key-to-match)))
       (vals)
       (apply concat)))

(defn- remove-simplified-routings [definition-key-to-match definitions]
  (->> (filter (fn [[key _]] (not (.contains (str key) definition-key-to-match))) definitions)
       (map (fn [[key value]] {key value}))
       (apply merge)))

(defn- http-server-routings-register-component-definition [http-server-dependency definition-key-to-match definitions]
  {:http-server-routing {:component    (register-routes (get-all-routings definition-key-to-match definitions))
                         :dependencies {:server      http-server-dependency
                                        :oracle      :oracle
                                        :command-bus :command-bus}}})

(defn simplified-routing-definitions->system-definitions [http-server-dependency definition-key-to-match definitions]
  (-> (remove-simplified-routings definition-key-to-match definitions)
      (merge (http-server-routings-register-component-definition http-server-dependency definition-key-to-match definitions))))
