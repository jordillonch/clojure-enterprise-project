(ns org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.finder
  (:require
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.storage-repository :as repository]))

(defn storage-find [repository owner namespace key]
  (if-let [value (repository/search repository owner namespace key)]
    {:namespace namespace
     :key       key
     :value     value}
    (throw (RuntimeException. "value not found"))))
