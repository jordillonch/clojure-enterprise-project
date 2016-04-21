(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.test.shared-infrastructure-test
  (:require
    [clojure.java.jdbc :as j]
    [org.jordillonch.lib.component.jdbc.connection-pool :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.config :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository-redis :as oauth2-access-token-repository]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.test.access-token-repository-redis-test :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.shared-infrastructure :refer :all]
    [org.jordillonch.lib.purger.redis :as purger-redis]
    [org.jordillonch.lib.purger.mysql :as purger-mysql]
    [hikari-cp.core :refer :all]))

; this can evolve...
(def system-test system)


; functions to set the system to test
(def general-mysql-connection
  {:datasource (-> (config [:database-main])
                   (dissoc :database-name)
                   (make-datasource))})

(defn- database-not-exists? [connection database-name]
  (->> (j/query connection ["SHOW DATABASES"])
       (map #(:database %))
       (not-any? #(= database-name %))))

(defn- init-database [connection database-name creation-sql-path]
  (when (database-not-exists? connection database-name)
    (j/db-do-commands connection false (str "CREATE DATABASE " database-name))
    (j/db-do-commands connection false (str "USE " database-name))
    (j/db-do-commands connection false (slurp creation-sql-path))))

(defn- init-database-main []
  (init-database
    general-mysql-connection
    (config [:database-main :database-name])
    "resources/database/operational.sql"))

(defn- init-database-backup []
  (init-database
    general-mysql-connection
    (config [:database-backup :database-name])
    "resources/database/analytics.sql"))

(defn init-databases []
  (init-database-main)
  (init-database-backup))

(defn reset-databases [system]
  (purger-redis/reset oauth2-access-token-repository/connection)
  (purger-mysql/reset (get-in system [:database-main :connection]))
  (purger-mysql/reset (get-in system [:database-backup :connection])))

(defn add-oauth2-test-token []
  (add-token-example "foo"))
