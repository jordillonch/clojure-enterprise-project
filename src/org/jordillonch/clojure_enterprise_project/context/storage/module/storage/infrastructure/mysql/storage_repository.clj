(ns org.jordillonch.clojure-enterprise-project.context.storage.module.storage.infrastructure.mysql.storage-repository
  (:require
    [clojure.java.jdbc :as j]
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.storage-repository :as repository])
  (:import (org.jordillonch.clojure_enterprise_project.context.storage.module.storage.domain.storage.storage_repository StorageRepository)))


(defrecord StorageRepositoryMySql [database]
  StorageRepository
  (search [_ owner namespace key]
    (if-let [result (j/query (:connection database) ["SELECT value FROM storage WHERE owner=? AND namespace=? AND `key`=?" owner namespace key])]
      (-> (first result)
          (:value))))

  (delete [_ owner namespace key]
    (j/delete! (:connection database)
               :storage
               ["owner=? AND namespace=? AND `key`=?" owner namespace key]))

  ; @fixme avoid delete, use replace?
  ; @fixme use insert with field names (there is a problem with the field "key"...)
  (save [this owner namespace key value]
    (repository/delete this owner namespace key)
    (j/insert! (:connection database)
               :storage
               nil
               [owner namespace key value])))

(defn new-storage-repository-mysql []
  (map->StorageRepositoryMySql {}))
