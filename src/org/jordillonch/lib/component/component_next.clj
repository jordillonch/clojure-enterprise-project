(ns org.jordillonch.lib.component.component-next
  (:require
    [com.stuartsierra.component :as component]))

(defn- add-dependencies [[key {component :component dependencies :dependencies}]]
  (if (nil? dependencies)
    [key component]
    [key (component/using component dependencies)]))

(defn- system-definition->stuart-system [system-map-definition]
  (map add-dependencies system-map-definition))

(defn system-map [system-definition]
  (system-map system-definition identity))

(defn system-map [system-definition custom-transformations]
  (->> (flatten system-definition)
       (apply merge)
       (custom-transformations)
       (system-definition->stuart-system)
       (component/map->SystemMap)))
