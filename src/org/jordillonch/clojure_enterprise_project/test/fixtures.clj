(ns org.jordillonch.clojure-enterprise-project.test.fixtures
  (:require
    [clojure.tools.namespace.repl :refer [refresh refresh-all]]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.module :as module-score]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.board.module :as module-board]
    [org.jordillonch.clojure-enterprise-project.core :refer [custom-transformations]]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.test.shared-infrastructure-test :as shared-infrastructure-test]
    [org.jordillonch.lib.component.component-next :refer :all]
    [com.stuartsierra.component :as component]))

; @fixme now modules to be tested are harcoded here
(defn- clojure-enterprise-project-system-test []
  (system-map
    [shared-infrastructure-test/system-test
     module-score/system
     module-board/system]
    custom-transformations))

(def system nil)

(defn- init []
  (alter-var-root #'system
                  (constantly (clojure-enterprise-project-system-test))))

(defn- start []
  (alter-var-root #'system component/start))

(defn- stop []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s)))))

(defn- go []
  (init)
  (start))

(defn- reset []
  (stop)
  (refresh :after 'user/go))


(defn setup-system [f]
  (shared-infrastructure-test/init-databases)
  (go)
  (f)
  (stop))

(defn clear-system [f]
  (shared-infrastructure-test/reset-databases system)
  (shared-infrastructure-test/add-oauth2-test-token)
  (f))
