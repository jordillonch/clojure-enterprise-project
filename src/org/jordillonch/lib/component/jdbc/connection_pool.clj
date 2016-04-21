(ns org.jordillonch.lib.component.jdbc.connection-pool
  (:require
    [com.stuartsierra.component :as component]
    [hikari-cp.core :refer :all]
    [taoensso.timbre :as log]))

(defrecord JdbcPool [datasource-options connection]
  component/Lifecycle
  (start [component]
    (log/debug "Starting JDBC pool...")
    (let [datasource (make-datasource datasource-options)]
      (assoc component :connection {:datasource datasource})))

  (stop [component]
    (log/debug "Stopping JDBC pool...")
    (if-let [{datasource :datasource} connection]
      (close-datasource datasource))
    (assoc component :connection nil)))

(defn new-jdbc-connection-pool [datasource-options]
  (map->JdbcPool {:datasource-options datasource-options}))
