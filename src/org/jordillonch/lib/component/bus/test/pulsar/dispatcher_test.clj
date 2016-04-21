(ns org.jordillonch.lib.component.bus.test.pulsar.dispatcher-test
  (:require
    [org.jordillonch.lib.component.bus.dispatcher :refer :all]
    [org.jordillonch.lib.component.bus.pulsar.dispatcher :refer :all])
  (:use
    [clojure.test])
  (:import
    [co.paralleluniverse.strands Strand]))

(deftest it-should-dispatch-with-no-handlers
  (let [dispatcher (new-dispatcher)
        values (-> dispatcher
                   (handle :event-1 "foo"))]
    (is (= '() values))
    (is (all-ok? dispatcher values))))

(deftest it-should-dispatch-to-one-handler
  (let [dispatcher (new-dispatcher)
        values (-> dispatcher
                   (register :event-1 str)
                   (handle :event-1 "foo"))]
    (is (= '("foo") values))
    (is (all-ok? dispatcher values))))

(deftest it-should-dispatch-to-two-handlers
  (let [dispatcher (new-dispatcher)
        values (-> dispatcher
                   (register :event-1 #(str "1" %))
                   (register :event-1 #(str "2" %))
                   (register :event-2 #(str "3" %))
                   (handle :event-1 "foo"))]
    (is (= '("1foo" "2foo") values))
    (is (all-ok? dispatcher values))))

;; @todo improve, use a helper to know it is an exception
(deftest it-should-dispatch-to-one-handler-that-fails-with-promise
  (is (instance? ArithmeticException (-> (new-dispatcher)
                                         (register :event-1 (fn [_] (/ 1 0)))
                                         (handle :event-1 "foo")
                                         (first)))))

; after handle a doall should be executed in order to raise the exception
; because the derefer promises values are in a lazy sequence
; this way we can be sure that all handles are executed but we can not
; have to wait for the results if we do not want them
; @todo improve, use a helper to know it is an exception
(deftest it-should-dispatch-to-one-handler-that-fails-and-another-that-not
  (let [dispatcher (new-dispatcher)
        values (-> dispatcher
                   (register :event-1 (fn [_] (/ 1 0)))
                   (register :event-1 str)
                   (handle :event-1 "foo"))]
    (is (instance? ArithmeticException (first values)))
    (is (= "foo" (second values)))
    (is (not (all-ok? dispatcher values)))))


(deftest it-should-dispatch-to-a-lot-of-handlers
  (let [handler-count 100000
        dispatcher (new-dispatcher)
        _ (dorun (repeatedly handler-count #(register dispatcher :event-1 (fn [x] (Strand/sleep 1000) (str x)))))
        values (handle dispatcher :event-1 "foo")]
    (is (= handler-count (count values)))
    (is (all-ok? dispatcher values))))

; demo-test with sleep
;(deftest time-test
;  (-> (new-dispatcher)
;      (register :event-1 (fn [a] (println "1") (Thread/sleep 1000) (println " a") (str "x" a)))
;      (register :event-1 (fn [a] (println "2") (Thread/sleep 500) (println " b") (str "y" a)))
;      ;(register :event-1 (fn [a] (println "1") (Strand/sleep 1000) (println " a") (str "x" a)))
;      ;(register :event-1 (fn [a] (println "2") (Strand/sleep 500) (println " b") (str "y" a)))
;      (register :event-1 (fn [a] (println "3") (println " c") (str "z" a)))
;      (handle :event-1 "foo")
;      ;(doall)                                               ; await
;      ;(println)                                             ; await
;      ))

;; demo-test long sleep, you will see:
;;   WARNING: fiber Fiber@10000029:fiber-10000029[task: ParkableForkJoinTask@3d665acc(Fiber@10000029), target: co.paralleluniverse.strands.SuspendableUtils$VoidSuspendableCallable@6d344bd4, scheduler: co.paralleluniverse.fibers.FiberForkJoinScheduler@1a01317c] is blocking a thread (Thread[ForkJoinPool-default-fiber-pool-worker-11,5,main]).
;(deftest time-test
;  (-> (new-dispatcher)
;      (register :event-1 (fn [a] (println "1") (Thread/sleep 1000) (println " a") (str "x" a)))
;      (handle :event-1 "foo")
;      ;(doall)                                               ; await
;      ;(println)                                             ; await
;      ))
