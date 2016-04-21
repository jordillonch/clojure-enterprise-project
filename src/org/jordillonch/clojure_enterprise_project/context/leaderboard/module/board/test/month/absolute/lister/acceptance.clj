(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.test.month.absolute.lister.acceptance
  (:require
    [clj-http.client :as client]
    [clj-time.core :as t]
    [org.jordillonch.clojure-enterprise-project.test.fixtures :refer :all]
    [org.jordillonch.clojure-enterprise-project.test.helpers :refer :all])
  (:use
    [clojure.test]))

(use-fixtures :once setup-system)
(use-fixtures :each clear-system)

(defn add-score-to-user [user-id score]
  (client/patch
    (str base-url "/leaderboard/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/user/" user-id)
    {:content-type :json
     :headers      oauth2-token-header
     :body         (str "{\"score-amount\": " score "}")}))

(defn get-board []
  (client/get
    (str base-url "/leaderboard_fix_me/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/year/" (t/year (t/now)) "/month/" (t/month (t/now)) "/absolute")
    {:content-type :json
     :headers      oauth2-token-header}))

(deftest lister-test []
                     (add-score-to-user "00000000-0000-0000-0000-000000000000" 50)
                     (add-score-to-user "11111111-0000-0000-0000-000000000000" 33)
                     (add-score-to-user "22222222-0000-0000-0000-000000000000" 100)
                     (is (= "[\"22222222-0000-0000-0000-000000000000\",\"100\",\"00000000-0000-0000-0000-000000000000\",\"50\",\"11111111-0000-0000-0000-000000000000\",\"33\"]"
                            (:body (get-board)))))
