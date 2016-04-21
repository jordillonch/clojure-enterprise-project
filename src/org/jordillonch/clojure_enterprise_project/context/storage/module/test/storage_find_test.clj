(ns org.jordillonch.clojure-enterprise-project.context.storage.module.test.storage-find-test
  (:require
    [org.jordillonch.lib.component.bus.dummy.domain-event-publisher :refer :all]
    [org.jordillonch.lib.component.bus.oracle :as oracle]
    [org.jordillonch.lib.component.bus.dummy.oracle :as dummy-oracle]))

(use 'clojure.test)

; We need to have some "test helpers" in the /test namespace in order to register handlers, fill the database
; or create a new "fake" implementation...
; This is failing :P
#_(deftest it_should_find_an_existing_storage_item
  (let [storage-value (oracle/ask dummy-oracle/new-oracle-dummy {:query     :query-storage-find
                                                                 :owner     "owner"
                                                                 :namespace "namespace"
                                                                 :key       "key"})]
    (is (= storage-value {:namespace "namespace"
                          :key       "key"
                          :value     "value"}))))
