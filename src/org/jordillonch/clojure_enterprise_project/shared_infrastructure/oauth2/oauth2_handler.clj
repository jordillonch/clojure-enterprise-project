(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.oauth2-handler
  (:require
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository-redis :refer :all]
    [catacumba.components :refer :all]
    [com.stuartsierra.component :as component]
    [catacumba.core :as ct]))

(defn- oauth2-handler [oauth2-access-token-repository context]
  (let [oauth2-token (-> (get-in context [:headers :authorization])
                         (clojure.string/replace #"Bearer " ""))
        oauth2-token-data (search oauth2-access-token-repository oauth2-token)]
    (ct/delegate {:oauth2-token-data        oauth2-token-data
                  :authentication-client-id (:clientId oauth2-token-data)
                  :authentication-id        (:id oauth2-token-data)
                  :authentication-type      (:grantType oauth2-token-data)
                  })))

(defrecord OAuth2Handler [server oauth2-access-token-repository]
  component/Lifecycle
  (start [component]
    (assoc-routes! server ::web [[:any (partial oauth2-handler oauth2-access-token-repository)]]))

  (stop [component]))

(defn add-oauth2-handler []
  (map->OAuth2Handler {}))
