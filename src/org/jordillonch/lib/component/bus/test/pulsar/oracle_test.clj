(ns org.jordillonch.lib.component.bus.test.pulsar.oracle-test
  (:require
    [org.jordillonch.lib.component.bus.oracle :refer :all]
    [org.jordillonch.lib.component.bus.pulsar.oracle :refer :all])
  (:use
    [clojure.test]))

(deftest it-should-ask-with-no-handlers
  (is (nil? (-> (new-oracle)
                (ask [:query-1])))))

(deftest it-should-ask-with-handler
  (is (= "foo" (-> (new-oracle)
                   (register :query-1 (fn [_] "foo"))
                   (ask [:query-1])))))

(deftest it-should-ask-with-handler-and-parameters
  (is (= "1foo" (-> (new-oracle)
                    (register :query-1 (fn [param] (str "1" param)))
                    (ask [:query-1 "foo"])))))

(deftest it-should-ask-with-two-handlers
  (is (= "1foo" (-> (new-oracle)
                    (register :query-1 (fn [param] (str "1" param)))
                    (register :query-2 (fn [param] (str "2" param)))
                    (ask [:query-1 "foo"])))))

(deftest it-should-ask-to-handler-that-fails
  (is (thrown? ArithmeticException (-> (new-oracle)
                                       (register :query-1 (fn [_] (/ 1 0)))
                                       (ask [:query-1])))))
