(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.infrastructure.month.absolute.repository-redis
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.domain.month.absolute.repository :refer :all]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.config :refer :all]
    [taoensso.carmine :as car :refer (wcar)]))

(def connection (config [:redis-leaderboard-month-absolute]))

(defn- leaderboard-month-id-key [leaderboard-id year month]
  (str year "." month "." leaderboard-id))

(defrecord LeaderboardMonthAbsoluteRepositoryRedis [database]
  LeaderboardMonthAbsoluteRepository

  (list-board [this leaderboard-month-id offset limit]
    (car/wcar connection (car/zrevrange (leaderboard-month-id-key (:leaderboard-id leaderboard-month-id)
                                                                  (:year leaderboard-month-id)
                                                                  (:month leaderboard-month-id))
                                        offset
                                        limit
                                        :withscores)))

  (add [this leaderboard-month-id user-id score-amount]
    (let [leaderboard-id (str (:leaderboard-id leaderboard-month-id))
          month (str (:month leaderboard-month-id))
          year (str (:year leaderboard-month-id))
          leaderboard-month-id-key (leaderboard-month-id-key leaderboard-id year month)
          user-id-key (str user-id)]
      (car/wcar connection (car/zincrby leaderboard-month-id-key score-amount user-id-key)))))

(defn new-leaderboard-month-absolute-repository-redis []
  (map->LeaderboardMonthAbsoluteRepositoryRedis {}))
