(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.domain.month.absolute.lister
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.domain.month.absolute.repository :refer :all]))

; @todo use schema
; @todo offset limit for list-board
(defn leaderboard-month-id [leaderboard-id year month]
  {:leaderboard-id leaderboard-id
   :year           year
   :month          month})

; @todo use schema
(defn board->response [board-list]
  board-list)

; @todo throw exception if leaderboard does not exist
; @todo offset and limit
(defn query-handler [query repository]
  (-> (list-board
        repository
        (leaderboard-month-id (:leaderboard-id query)
                                                   (:year query)
                                                   (:month query))
        0
        100)
      (board->response)))
