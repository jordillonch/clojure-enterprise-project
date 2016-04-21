(ns org.jordillonch.lib.component.bus.test.dummy.dispatcher-test
  (:require
    [org.jordillonch.lib.component.bus.dispatcher :refer :all]
    [org.jordillonch.lib.component.bus.dummy.dispatcher :refer :all])
  (:use
    [clojure.test]))

(def event-1 "event-1")
(def event-2 "event-2")

(deftest it-should-dispatch-with-no-handlers
  (is (= '() (-> (new-dispatcher-dummy)
                 (handle event-1 "foo")))))

(deftest it-should-dispatch-to-one-handler
  (is (= '("foo") (-> (new-dispatcher-dummy)
                      (register event-1 #(str %))
                      (handle event-1 "foo")))))

(deftest it-should-dispatch-to-two-handlers
  (is (= '("1foo" "2foo") (-> (new-dispatcher-dummy)
                              (register event-1 #(str "1" %))
                              (register event-1 #(str "2" %))
                              (register event-2 #(str "3" %))
                              (handle event-1 "foo")))))
