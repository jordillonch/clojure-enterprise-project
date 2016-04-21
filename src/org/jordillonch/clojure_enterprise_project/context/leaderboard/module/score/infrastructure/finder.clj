(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.infrastructure.finder
  (:require
    [catacumba.http :as http]
    [clojure.data.json :as json]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all]
    [org.jordillonch.lib.component.bus.oracle :refer [ask]])
  (:use
    [slingshot.slingshot :only [try+]]))

(defn controller [{{leaderboard-id :leaderboard_id user-id :user_id} :route-params :as context}
                  oracle]
  (try+
    (->> (leaderboard-user-score-find-query leaderboard-id user-id)
         (ask oracle)
         (json/write-str))
    (catch [:type :leaderboard-score-not-found-exception] {}
      (http/not-found ""))))
