(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.infrastructure.updater
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all]
    [org.jordillonch.lib.component.bus.command-bus :refer [handle all-ok?]]
    [catacumba.http :as http]))

; @fixme score-amount should be extracted from a patch-json format
; @fixme log errors
(defn controller [{{leaderboard-id :leaderboard_id user-id :user_id} :route-params
                   data                                              :data :as context}
                  command-bus]
  (let [result (handle command-bus (leaderboard-user-score-update-command leaderboard-id user-id (:score-amount data)))]
    (if (all-ok? command-bus result)
      (http/no-content)
      (http/internal-server-error (str result)))))
