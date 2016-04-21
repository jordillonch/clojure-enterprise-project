(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.test.score.finder.behaviour
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.finder :as finder]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.repository :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.user.schema :refer :all]
    [schema.experimental.generators :as g]
    [shrubbery.core :refer :all]
    [slingshot.test])
  (:use
    [clojure.test]))

(deftest it-should-find-a-score
  (let [leaderboard-id-stub (g/generate LeaderboardId)
        user-id-stub (g/generate UserId)
        query-stub (leaderboard-user-score-find-query leaderboard-id-stub user-id-stub)
        leaderboard-user-stub (leaderboard-user leaderboard-id-stub user-id-stub)
        leaderboard-user-score-stub (assoc (g/generate LeaderboardUserScore) :leaderboard-user leaderboard-user-stub)
        repository (mock ScoreRepository {:find-score leaderboard-user-score-stub})
        expected-response (leaderboard-user-score-find-response (str leaderboard-id-stub) (str user-id-stub) (:score leaderboard-user-score-stub))]
    (is (= expected-response (finder/query-handler query-stub repository)))))

(deftest it-should-not-find-score
  (let [leaderboard-id-stub (g/generate LeaderboardId)
        user-id-stub (g/generate UserId)
        query-stub (leaderboard-user-score-find-query leaderboard-id-stub user-id-stub)
        repository (mock ScoreRepository {:find-score nil})]
    (is (thrown+? #(= % {:type :leaderboard-score-not-found-exception}) (finder/query-handler query-stub repository)))))
