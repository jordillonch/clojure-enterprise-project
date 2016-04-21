(ns org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.updater
  (:require
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.storage-repository :as repository]))

(defn storage-update [repository owner namespace key value]
  (repository/save repository owner namespace key value))
