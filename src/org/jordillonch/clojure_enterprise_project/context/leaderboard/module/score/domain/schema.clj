(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema
  (:require
    [org.jordillonch.clojure-enterprise-project.context.user.schema :refer :all]
    [schema.coerce :as sc]
    [schema.core :as s]))

(def LeaderboardId s/Uuid)

(s/defschema LeaderboardUser {:leaderboard-id LeaderboardId
                              :user-id        UserId})

(s/defschema LeaderboardUserScore {:leaderboard-user LeaderboardUser
                                   :score            s/Int})

(s/defschema LeaderboardUserScoreUpdatedDomainEvent [(s/one (s/eq :leaderboard-user-score-domain-event) "domain event")
                                                     (s/one {:id    LeaderboardUser
                                                             :score s/Int}
                                                            "data")])

(s/defschema LeaderboardResetDomainEvent [(s/one (s/eq :leaderboard-reset-domain-event) "query")
                                          (s/one {:id LeaderboardId}
                                                 "data")])

(s/defschema LeaderboardUserScoreFindQuery [(s/one (s/eq :leaderboard-user-score-find-query) "query")
                                            (s/one {:leaderboard-id LeaderboardId
                                                    :user-id        UserId}
                                                   "data")])

(s/defschema LeaderboardUserScoreFindResponse {:leaderboard-id s/Str
                                               :user-id        s/Str
                                               :score          s/Int})

(s/defschema LeaderboardUserScoreUpdateCommand [(s/one (s/eq :leaderboard-user-score-update-command) "command")
                                                (s/one {:leaderboard-id LeaderboardId
                                                        :user-id        UserId
                                                        :score-amount   s/Int}
                                                       "data")])

(s/defschema LeaderboardResetCommand [(s/one (s/eq :leaderboard-reset-command) "command")
                                      (s/one {:leaderboard-id LeaderboardId} "data")])

(defn leaderboard-id [id]
  (s/validate LeaderboardId id))

(defn leaderboard-user [leaderboard-id user-id]
  ((sc/coercer LeaderboardUser sc/string-coercion-matcher)
    {:leaderboard-id leaderboard-id
     :user-id        user-id}))

(defn leaderboard-user-score [leaderboard-user score]
  ((sc/coercer LeaderboardUserScore sc/string-coercion-matcher)
    {:leaderboard-user leaderboard-user
     :score            score}))

; @todo add occurred-on
(defn leaderboard-user-score-updated-domain-event [leaderboard-user score]
  ((sc/coercer LeaderboardUserScoreUpdatedDomainEvent sc/string-coercion-matcher)
    [:leaderboard-user-score-domain-event {:id    leaderboard-user
                                           :score score}]))

; @todo add occurred-on
(defn leaderboard-reset-domain-event [leaderboard-id]
  ((sc/coercer LeaderboardResetDomainEvent sc/string-coercion-matcher)
    [:leaderboard-reset-domain-event {:id leaderboard-id}]))

(defn leaderboard-user-score-find-query [leaderboard-id user-id]
  ((sc/coercer LeaderboardUserScoreFindQuery sc/string-coercion-matcher)
    [:leaderboard-user-score-find-query {:leaderboard-id leaderboard-id
                                         :user-id        user-id}]))

(defn leaderboard-user-score-find-response [leaderboard-id user-id score]
  ((sc/coercer LeaderboardUserScoreFindResponse sc/string-coercion-matcher)
    {:leaderboard-id leaderboard-id
     :user-id        user-id
     :score          score}))

(defn leaderboard-user-score-update-command [leaderboard-id user-id score-amount]
  ((sc/coercer LeaderboardUserScoreUpdateCommand sc/string-coercion-matcher)
    [:leaderboard-user-score-update-command {:leaderboard-id leaderboard-id
                                             :user-id        user-id
                                             :score-amount   score-amount}]))

(defn leaderboard-reset-command [leaderboard-id]
  ((sc/coercer LeaderboardResetCommand sc/string-coercion-matcher)
    [:leaderboard-reset-command {:leaderboard-id leaderboard-id}]))
