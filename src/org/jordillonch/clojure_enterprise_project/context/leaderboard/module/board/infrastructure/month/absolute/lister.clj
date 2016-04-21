(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.infrastructure.month.absolute.lister
  (:require
    [catacumba.http :as http]
    [clojure.data.json :as json]
    [org.jordillonch.lib.component.bus.oracle :refer [ask]])
  (:use
    [slingshot.slingshot :only [try+]]))

; @todo use schema
(defn controller [{{leaderboard-id :leaderboard_id year :year month :month} :route-params :as context}
                  oracle]
  (try+
    (->> [:leaderboard-month-absolute-query {:leaderboard-id leaderboard-id
                                             :year           year
                                             :month          month}]
         (ask oracle)
         (json/write-str))
    (catch [:type :leaderboard-month-absolute-not-found-exception] {}
      (http/not-found ""))))
