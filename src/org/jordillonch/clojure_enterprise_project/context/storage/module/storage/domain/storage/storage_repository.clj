(ns org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.storage-repository)

(defprotocol StorageRepository
  (search [this owner namespace key])
  (delete [this owner namespace key])
  (save [this owner namespace key value]))
