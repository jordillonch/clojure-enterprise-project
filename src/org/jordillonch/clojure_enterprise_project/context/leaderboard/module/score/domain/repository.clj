(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.repository)

(defprotocol ScoreRepository
  (find-score [this leaderboard-user])
  (update-score [this leaderboard-user score-update])
  (reset-leaderboard [this leaderboard-id]))
