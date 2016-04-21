(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.test.access-token-repository-redis-test
  (:require
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository-redis :refer :all])
  (:use
    [clojure.test]))

(def token-data
  {:type     "bearer"
   :token    "foo"
   :clientId "b70ea8a1-3d8a-3232-8e75-2d65cc7faecc"
   :id       "test_oauth2_client_56786b4b90e7a"
   :scope    "grantType"})

(deftest it-should-set-and-get-using-raw-functions
  (let [token "foo"
        repository (new-oauth2-access-token-repository)
        result-add (add repository token token-data)
        result-find (search repository token)]
    (is (= "OK" result-add))
    (is (= token-data result-find))))

(defn add-token-example [bearer]
  (add (new-oauth2-access-token-repository) bearer token-data))
