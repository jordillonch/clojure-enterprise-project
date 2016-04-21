(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.domain.month.absolute.updater
  (:require
    [clj-time.core :as t]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.domain.month.absolute.repository :as repository]))

; @todo use schema
(defn domain-event-handler [domain-event repository]
  (let [leaderboard-id (get-in domain-event [:id :leaderboard-id])
        user-id (get-in domain-event [:id :user-id])
        score (get-in domain-event [:score])
        leaderboard-month-id {:leaderboard-id leaderboard-id
                              :month          (t/month (t/now))
                              :year           (t/year (t/now))}] ; @todo use occurred-on
    (repository/add repository leaderboard-month-id user-id score)))
