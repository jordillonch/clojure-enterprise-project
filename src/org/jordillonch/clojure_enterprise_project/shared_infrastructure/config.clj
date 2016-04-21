(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.config
  (:require
    [org.jordillonch.lib.config.loader :refer [load-config]]))

(def analytics-config (load-config "config_shared_infrastructure.clj"))

(defn config [key]
  (get-in analytics-config key))
