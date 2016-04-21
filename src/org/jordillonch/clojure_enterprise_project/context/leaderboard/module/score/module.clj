(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.module
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.finder :as finder]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.updater :as updater]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.reseter :as reseter]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.infrastructure.finder :as finder-controller]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.infrastructure.updater :as updater-controller]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.infrastructure.reseter :as reseter-controller]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.infrastructure.repository-redis :refer :all]))

(def system
  {::repository-score     {:component    (new-score-repository-redis)
                           :dependencies {:database :database-main}}
   ::handlers-oracle      {:leaderboard-user-score-find-query [finder/query-handler [::repository-score]]}
   ::handlers-command-bus {:leaderboard-user-score-update-command [updater/command-handler [:domain-event-publisher ::repository-score]]
                           :leaderboard-reset-command             [reseter/command-handler [:domain-event-publisher ::repository-score]]}
   ::api-server-routing   [[:prefix "leaderboard/:leaderboard_id/user/:user_id"
                            [:by-method
                             {:get   [finder-controller/controller [:oracle]]
                              :patch [updater-controller/controller [:command-bus]]}]]
                           [:prefix "leaderboard/:leaderboard_id"
                            [:by-method
                             {:delete [reseter-controller/controller [:command-bus]]}]]]
   })
