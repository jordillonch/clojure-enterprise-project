(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.leaderboard
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.module :as module-board]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.module :as module-score]))

(def system
  [module-board/system
   module-score/system])
