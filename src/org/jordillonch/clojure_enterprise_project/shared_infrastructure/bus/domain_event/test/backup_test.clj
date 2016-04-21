(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.bus.domain-event.test.backup-test
  (:require [langohr.core :as rmq]
            [langohr.channel :as lch]
            [langohr.queue :as lq]
            [langohr.consumers :as lc]
            [langohr.basic :as lb]))

(defn publish [exchange-name routing-key payload]
  (let [conn (rmq/connect)
        ch   (lch/open conn)]
    (lb/publish ch exchange-name routing-key payload)
    (rmq/close ch)
    (rmq/close conn)))

(defn publish-domain-events-test []
  (publish "domain_events" "routing-key" "{\n  \"aggregate_id\": \"de2e749c-16a7-422b-bacc-c467acb1eff1\",\n  \"name\": \"user_registered\",\n  \"occured_on\": \"2015-11-14T16:21:25+0000\",\n  \"body\": {\n    \"applicationId\": \"a7cda525-1e21-4af9-91f4-d922191bdfd4\",\n    \"ip\": \"213.23.1.56\",\n    \"countryCode\": \"CT\",\n    \"countryName\": \"Catalunya\",\n    \"analyticsContextId\": \"11f8ccf0-b9b1-4b45-81bd-13b5861ffadc\",\n  }\n}\n"))

(defn stress-test []
  (dorun (repeatedly 100000 #(publish-domain-events-test))))
