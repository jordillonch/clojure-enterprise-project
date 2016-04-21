(ns org.jordillonch.clojure-enterprise-project.context.storage.module.storage.infrastructure.api-server.controller
  (:require
    [catacumba.core :as ct]
    [catacumba.http :as http]
    [clojure.data.json :as json]
    [org.jordillonch.lib.component.bus.command-bus :refer [handle]]
    [org.jordillonch.lib.component.bus.dummy.domain-event-publisher :refer :all]
    [org.jordillonch.lib.component.bus.oracle :refer [ask]]))

; @todo found a json output handler
; @todo 404 not found
(defn controller-get [{{namespace :namespace key :key} :route-params
                       owner                           :authentication-id
                       :as                             context}
                      oracle command-bus]
  (->> [:query-storage-find {:owner     owner
                             :namespace namespace
                             :key       key}]
       (ask oracle)
       (json/write-str)))

(defn controller-put [{{namespace :namespace key :key} :route-params
                       owner                           :authentication-id
                       data                            :data
                       :as                             context}
                      oracle command-bus]
  (->> [:command-storage-update {:owner     owner
                                 :namespace namespace
                                 :key       key
                                 :value     (:value data)}]
       (handle command-bus))
  (http/created ""))

(defn controller-delete [{{namespace :namespace key :key} :route-params
                          owner                           :authentication-id
                          :as                             context}
                         oracle command-bus]
  (->> [:command-storage-delete {:owner     owner
                                 :namespace namespace
                                 :key       key}]
       (handle command-bus))
  (http/no-content))
