(ns org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.deleter
  (:require
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.storage-repository :as repository]))

(defn storage-delete [repository owner namespace key]
  (repository/delete repository owner namespace key))
