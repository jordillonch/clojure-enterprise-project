(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.infrastructure.reseter
  (:require
    [catacumba.http :as http]
    [clojure.data.json :as json]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all]
    [org.jordillonch.lib.component.bus.command-bus :refer [handle all-ok?]]
    [org.jordillonch.lib.component.bus.oracle :refer [ask]])
  (:use
    [slingshot.slingshot :only [try+]]))

; @fixme score-amount should be extracted from a patch-json format
; @fixme log errors
(defn controller [{{leaderboard-id :leaderboard_id} :route-params
                   data                             :data :as context}
                  command-bus]
  (let [result (handle command-bus (leaderboard-reset-command leaderboard-id))]
    (if (all-ok? command-bus result)
      (http/no-content)
      (http/internal-server-error (str result)))))
