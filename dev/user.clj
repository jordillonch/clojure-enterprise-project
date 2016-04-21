(ns user
  (:require [com.stuartsierra.component :as component]
            [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [org.jordillonch.clojure-enterprise-project.core :refer [clojure-enterprise-project-system]]))

(def system nil)

(defn init []
  (alter-var-root #'system
                  (constantly (clojure-enterprise-project-system))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (refresh :after 'user/go))
