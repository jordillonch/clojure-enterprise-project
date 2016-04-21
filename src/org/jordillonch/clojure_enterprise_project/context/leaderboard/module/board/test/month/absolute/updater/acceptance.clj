(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.test.month.absolute.updater.acceptance
  (:require
    [clj-http.client :as client]
    [org.jordillonch.clojure-enterprise-project.test.fixtures :refer :all]
    [org.jordillonch.clojure-enterprise-project.test.helpers :refer :all])
  (:use
    [clojure.test]))

(use-fixtures :once setup-system)
(use-fixtures :each clear-system)

(defn add-100-to-user-score []
  (client/patch
    (str base-url "/leaderboard/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/user/00000000-0000-0000-0000-000000000000")
    {:content-type :json
     :headers      oauth2-token-header
     :body         "{\"score-amount\": 100}"}))

(deftest finder-updater-test []
                             (add-100-to-user-score))
