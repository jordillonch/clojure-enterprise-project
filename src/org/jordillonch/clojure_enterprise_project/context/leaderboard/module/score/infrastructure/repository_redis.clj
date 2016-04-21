(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.infrastructure.repository-redis
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.repository :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.config :refer :all]
    [taoensso.carmine :as car :refer (wcar)]))

(def connection (config [:redis-leaderboard]))

(defrecord ScoreRepositoryRedis []
  ScoreRepository
  (find-score [this leaderboard-user]
    (let [leaderboard-id (str (:leaderboard-id leaderboard-user))
             user-id (str (:user-id leaderboard-user))
             score (car/wcar connection (car/zscore leaderboard-id user-id))]
      (if (nil? score) nil
                       (leaderboard-user-score leaderboard-user score))))

  (update-score [this leaderboard-user score-update]
    (let [leaderboard-id (str (:leaderboard-id leaderboard-user))
          user-id (str (:user-id leaderboard-user))]
      (car/wcar connection (car/zincrby leaderboard-id score-update user-id))))

  (reset-leaderboard [this leaderboard-id]
    (car/wcar connection (car/del (str leaderboard-id)))))

(defn new-score-repository-redis []
  (map->ScoreRepositoryRedis {}))
