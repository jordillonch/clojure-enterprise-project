(ns org.jordillonch.lib.component.bus.test.pulsar.domain-event-publisher-test
  (:require
    [org.jordillonch.lib.component.bus.domain-event-publisher :refer :all]
    [org.jordillonch.lib.component.bus.pulsar.domain-event-publisher :refer :all])
  (:use
    [clojure.test]))

(deftest it-should-publish-with-no-handlers
  (let [domain-event-publisher (new-domain-event-publisher)
        responses (-> domain-event-publisher
                      (publish [:event-1]))]
    (is (= '() responses))
    (is (all-ok? domain-event-publisher responses))))

(deftest it-should-publish-with-handler
  (let [responses (-> (new-domain-event-publisher)
                      (register :event-1 (fn [_] "foo"))
                      (publish [:event-1]))]
    (is (= '("foo") responses))))

(deftest it-should-publish-with-handler-and-parameters
  (let [responses (-> (new-domain-event-publisher)
                      (register :event-1 #(str "1" %))
                      (publish [:event-1 "foo"]))]
    (is (= '("1foo") responses))))

(deftest it-should-publish-with-two-handlers
  (let [responses (-> (new-domain-event-publisher)
                      (register :event-1 #(str "1" %))
                      (register :event-2 #(str "2" %))
                      (publish [:event-1 "foo"]))]
    (is (= '("1foo") responses))))

; after handle a doall should be executed in order to raise the exception
; because the derefer promises values are in a lazy sequence
; this way we can be sure that all handles are executed but we can not
; have to wait for the results if we do not want them
(deftest it-should-publish-to-handler-that-fails
  (let [responses (-> (new-domain-event-publisher)
                      (register :event-1 (fn [_] (/ 1 0)))
                      (publish [:event-1])
                      (first))]
    (is (instance? ArithmeticException responses))))
