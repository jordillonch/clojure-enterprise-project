(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.finder
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.repository :as repository]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all])
  (:use
    [slingshot.slingshot :only [throw+]]))

(defn find-score [leaderboard-user repository]
  (let [result (repository/find-score repository leaderboard-user)]
    (if (nil? result)
      (throw+ {:type :leaderboard-score-not-found-exception})
      result)))

(defn leaderboard-user-score->response [leaderboard-user-score]
  (leaderboard-user-score-find-response (str (get-in leaderboard-user-score [:leaderboard-user :leaderboard-id]))
                                        (str (get-in leaderboard-user-score [:leaderboard-user :user-id]))
                                        (get-in leaderboard-user-score [:score])))

(defn query-handler [query repository]
  (-> (leaderboard-user (:leaderboard-id query)
                        (:user-id query))
      (find-score repository)
      (leaderboard-user-score->response)))
