(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.domain.month.absolute.repository)

(defprotocol LeaderboardMonthAbsoluteRepository
  (list-board [this leaderboard-month-id offset limit])
  (add [this leaderboard-month-id user-id score-amount]))
