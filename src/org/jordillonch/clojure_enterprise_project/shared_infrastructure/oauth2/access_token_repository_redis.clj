(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository-redis
  (:require
    [clojure.data.json :as json]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.config :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository]
    [taoensso.carmine :as car :refer (wcar)])
  (:import (org.jordillonch.clojure_enterprise_project.shared_infrastructure.oauth2.access_token_repository OAuth2AccessTokenRepository)))

(def connection (config [:redis-oauth2]))

(defn- search-raw [token]
  (car/wcar connection (car/get token)))

(defn- add-raw [token data]
  (car/wcar connection (car/set token data)))

(defn- json->hashmap [data]
  (->> (json/read-str data)
       (map (fn [[key value]] [(keyword key) value]))
       (into {})))


(deftype OAuth2AccessTokenRepositoryRedis []
  OAuth2AccessTokenRepository
  (add [this token token-data]
    (->> (json/write-str token-data)
         (add-raw token)))

  (search [this token]
    (-> (search-raw token)
        (json->hashmap))))

(defn new-oauth2-access-token-repository []
  (OAuth2AccessTokenRepositoryRedis.))
