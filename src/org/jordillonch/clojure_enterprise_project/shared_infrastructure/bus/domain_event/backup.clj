(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.bus.domain-event.backup
  (:require
    [clojure.java.jdbc :as j]
    [clojure.data.json :as json]
    [clj-time.format :as f]
    [taoensso.timbre.profiling :refer [sampling-profile]]
    [org.jordillonch.lib.component.jdbc.connection-pool :refer :all]
    [org.jordillonch.lib.component.rabbitmq.connection :refer :all]
    [org.jordillonch.lib.component.rabbitmq.worker :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.config :refer :all]))

(defn handler [{{connection :connection} :database-backup} _ _ ^bytes payload]
  (let [domain-event (json/read-str (String. payload "UTF-8") :key-fn keyword)]
    (sampling-profile :info 0.001 :mysql-insert
                      (j/insert! connection :domain_event_2
                                 {
                                  :aggregate_id (:aggregate_id domain-event)
                                  :name         (:name domain-event)
                                  :occurred_on  (f/unparse (f/formatters :mysql) (f/parse (:occurred_on domain-event)))
                                  :data         payload}))))

(def system
  {:backup-rabbitmq-connection {:component (new-rabbitmq-connection (config [:rabbitmq-connection]))}
   :backup-worker-all-domain-events-persister
                               {:component    (new-rabbitmq-worker
                                                (config [:rabbitmq-worker :exchange-name])
                                                "clojure-enterprise-project.analytics.all_domain_events_persister"
                                                handler)
                                :dependencies {:rabbitmq-connection :backup-rabbitmq-connection
                                               :database-backup     :database-backup}}})
