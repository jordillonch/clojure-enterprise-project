(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.reseter
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.repository :as repository]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all]
    [org.jordillonch.lib.component.bus.domain-event-publisher :refer :all]))

(defn reset-leaderboard [leaderboard-id domain-event-publisher repository]
  (repository/reset-leaderboard repository leaderboard-id)
  (publish domain-event-publisher (leaderboard-reset-domain-event leaderboard-id)))

(defn command-handler [command domain-event-publisher repository]
  (reset-leaderboard (:leaderboard-id command)
                     domain-event-publisher
                     repository))
