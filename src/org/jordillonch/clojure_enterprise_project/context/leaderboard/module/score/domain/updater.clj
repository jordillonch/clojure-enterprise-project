(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.updater
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.repository :as repository]
    [org.jordillonch.lib.component.bus.domain-event-publisher :refer :all]))

(defn update-score [leaderboard-user score-amount domain-event-publisher repository]
  (repository/update-score repository leaderboard-user score-amount)
  (publish domain-event-publisher (leaderboard-user-score-updated-domain-event leaderboard-user score-amount)))

(defn command-handler [command domain-event-publisher repository]
  (update-score (leaderboard-user (:leaderboard-id command)
                                  (:user-id command))
                (:score-amount command)
                domain-event-publisher
                repository))
