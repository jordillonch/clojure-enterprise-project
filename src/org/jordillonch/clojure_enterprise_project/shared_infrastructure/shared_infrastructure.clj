(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.shared-infrastructure
  (:require
    [catacumba.components :refer (catacumba-server assoc-routes!)]
    [org.jordillonch.lib.component.bus.pulsar.command-bus :refer :all]
    [org.jordillonch.lib.component.bus.pulsar.domain-event-publisher :refer :all]
    [org.jordillonch.lib.component.bus.pulsar.oracle :refer :all]
    [org.jordillonch.lib.component.jdbc.connection-pool :refer :all]
    [org.jordillonch.lib.component.logger.timbre :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository-redis :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.oauth2-handler :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.bus.domain-event.backup :as domain-event-backup]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.config :refer :all]))

(def system
  [{:logger                         {:component (new-logger-timbre (config [:logger]))}
    :database-main                  {:component (new-jdbc-connection-pool (config [:database-main]))}
    :database-backup                {:component (new-jdbc-connection-pool (config [:database-backup]))}
    :command-bus                    {:component (new-command-bus)}
    :oracle                         {:component (new-oracle)}
    :domain-event-publisher         {:component (new-domain-event-publisher)}
    :oauth2-access-token-repository {:component (new-oauth2-access-token-repository)}
    :http-server-api                {:component (catacumba-server {:port (config [:http-server-api :port])})}
    :http-server-api-oauth2         {:component    (add-oauth2-handler)
                                     :dependencies {:server                         :http-server-api
                                                    :oauth2-access-token-repository :oauth2-access-token-repository}}}
   domain-event-backup/system])
