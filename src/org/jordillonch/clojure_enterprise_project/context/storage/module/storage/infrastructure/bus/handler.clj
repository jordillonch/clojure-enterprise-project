(ns org.jordillonch.clojure-enterprise-project.context.storage.module.storage.infrastructure.bus.handler
  (:require
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.deleter :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.finder :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.storage.module.storage.domain.storage.updater :refer :all]))

(defn storage-find-handler [query repository]
  (storage-find repository (:owner query) (:namespace query) (:key query)))

(defn storage-update-handler [command repository]
  (storage-update repository
                  (:owner command)
                  (:namespace command)
                  (:key command)
                  (:value command)))

(defn storage-delete-handler [command repository]
  (storage-delete repository
                  (:owner command)
                  (:namespace command)
                  (:key command)))
