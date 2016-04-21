(ns org.jordillonch.clojure-enterprise-project.test.helpers
  (:require
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.config :refer :all]))

(def base-url (str "http://localhost:" (config [:http-server-api :port])))

(def oauth2-token-header {"Authorization" "Bearer foo"})