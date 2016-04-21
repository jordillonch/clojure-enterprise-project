(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.test.score.reseter.acceptance
  (:require
    [clj-http.client :as client]
    [org.jordillonch.clojure-enterprise-project.test.fixtures :refer :all]
    [org.jordillonch.clojure-enterprise-project.test.helpers :refer :all])
  (:use
    [clojure.test]))

(use-fixtures :once setup-system)
(use-fixtures :each clear-system)

(deftest it-reset-a-board []
                          (let [_ (client/patch
                                    (str base-url "/leaderboard/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/user/00000000-0000-0000-0000-000000000000")
                                    {:content-type :json
                                     :headers      oauth2-token-header
                                     :body         "{\"score-amount\": 100}"})
                                result-delete (client/delete
                                                (str base-url "/leaderboard/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                                                {:content-type :json
                                                 :headers      oauth2-token-header})]
                            (is (= 204 (:status result-delete)))))

(deftest it-try-to-reset-a-board-but-it-can-not-been-found []
                                                           (is (thrown? clojure.lang.ExceptionInfo
                                                                        (let [result-get (client/delete
                                                                                           (str base-url "/leaderboard/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/user/99999999-9999-9999-9999-999999999999")
                                                                                           {:content-type :json
                                                                                            :headers      oauth2-token-header})]
                                                                          (is (= 404 (:status result-get)))))))
