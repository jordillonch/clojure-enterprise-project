(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.test.score.updater.acceptance
  (:require
    [clj-http.client :as client]
    [org.jordillonch.clojure-enterprise-project.test.fixtures :refer :all]
    [org.jordillonch.clojure-enterprise-project.test.helpers :refer :all])
  (:use
    [clojure.test]))

(use-fixtures :once setup-system)
(use-fixtures :each clear-system)

(deftest finder-updater-test []
                             (let [result (client/patch
                                            (str base-url "/leaderboard/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/user/00000000-0000-0000-0000-000000000000")
                                            {:content-type :json
                                             :headers      oauth2-token-header
                                             :body         "{\"score-amount\": 100}"})]
                               (is (= 204 (:status result)))))
