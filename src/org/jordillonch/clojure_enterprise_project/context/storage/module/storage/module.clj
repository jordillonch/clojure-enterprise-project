(ns org.jordillonch.clojure-enterprise-project.context.storage.module.storage.module
  (:require
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.infrastructure.api-server.controller :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.infrastructure.bus.handler :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.infrastructure.mysql.storage-repository :refer :all]
    [org.jordillonch.lib.component.bus.handler-registrator :refer :all]))

(def system
  {:storage-repository-storage   {:component    (new-storage-repository-mysql)
                                  :dependencies {:database :database-main}}
   :storage-handlers-oracle      {:query-storage-find [storage-find-handler [:storage-repository-storage]]}
   :storage-handlers-command-bus {:command-storage-update [storage-update-handler [:storage-repository-storage]]
                                  :command-storage-delete [storage-delete-handler [:storage-repository-storage]]}
   :storage-api-server-routing   [[:prefix "storage/:namespace/:key" [:by-method
                                                                      {:get    [controller-get [:oracle :command-bus]]
                                                                       :put    [controller-put [:oracle :command-bus]]
                                                                       :delete [controller-delete [:oracle :command-bus]]}]]]
   })
