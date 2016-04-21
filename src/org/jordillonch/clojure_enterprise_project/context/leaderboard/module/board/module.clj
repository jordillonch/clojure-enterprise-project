(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.module
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.domain.month.absolute.updater :as month-absolute-updater]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.domain.month.absolute.lister :as month-absolute-lister]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.infrastructure.month.absolute.lister :as month-absolute-get]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.infrastructure.month.absolute.repository-redis :refer :all]))

; @fixme I do not know why but if I use as prefix "leaderboard" in the api it not work, so for now I left this waiting to replace catacumba to yada
(def system
  {::repository-month-absolute {:component    (new-leaderboard-month-absolute-repository-redis)
                                :dependencies {:database :database-main}}
   ::handlers-domain-event     {:leaderboard-user-score-domain-event [month-absolute-updater/domain-event-handler [::repository-month-absolute]]}
   ::handlers-oracle           {:leaderboard-month-absolute-query [month-absolute-lister/query-handler [::repository-month-absolute]]}
   ::api-server-routing        [[:prefix "leaderboard_fix_me/:leaderboard_id/year/:year/month/:month/absolute"
                                 [:by-method
                                  {:get [month-absolute-get/controller [:oracle]]}]]]})
