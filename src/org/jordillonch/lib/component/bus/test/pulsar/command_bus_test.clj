(ns org.jordillonch.lib.component.bus.test.pulsar.command-bus-test
  (:require
    [org.jordillonch.lib.component.bus.command-bus :refer :all]
    [org.jordillonch.lib.component.bus.pulsar.command-bus :refer :all])
  (:use
    [clojure.test]))

(deftest it-should-handle-with-no-handlers
  (let [command-bus (new-command-bus)
        responses (-> command-bus
                      (handle [:command-1]))]
    (is (= '() responses))
    (is (all-ok? command-bus responses))))

(deftest it-should-handle-with-handler
  (let [command-bus (new-command-bus)
        responses (-> command-bus
                      (register :command-1 (fn [_] "foo"))
                      (handle [:command-1]))]
    (is (= '("foo") responses))
    (is (all-ok? command-bus responses))))

(deftest it-should-handle-with-handler-and-parameters
  (let [command-bus (new-command-bus)
        responses (-> command-bus
                      (register :command-1 #(str "1" %))
                      (handle [:command-1 "foo"]))]
    (is (= '("1foo") responses))
    (is (all-ok? command-bus responses))))

(deftest it-should-handle-with-two-handlers
  (let [command-bus (new-command-bus)
        responses (-> command-bus
                      (register :command-1 #(str "1" %))
                      (register :command-2 #(str "2" %))
                      (handle [:command-1 "foo"]))]
    (is (= '("1foo") responses))
    (is (all-ok? command-bus responses))))

; after handle a doall should be executed in order to raise the exception
; because the derefer promises values are in a lazy sequence
; this way we can be sure that all handles are executed but we can not
; have to wait for the results if we do not want them
(deftest it-should-publish-to-handler-that-fails
  (let [command-bus (new-command-bus)
        responses (-> command-bus
                      (register :command-1 (fn [_] (/ 1 0)))
                      (handle [:command-1]))]
    (is (instance? ArithmeticException (first responses)))
    (is (not (all-ok? command-bus responses)))))
