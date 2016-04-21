(ns org.jordillonch.clojure-enterprise-project.core
  (:gen-class)
  (:require
    [com.stuartsierra.component :as component]
    [org.jordillonch.lib.component.bus.simplifier :refer :all]
    [org.jordillonch.lib.component.catacumba.simplifier :refer :all]
    [org.jordillonch.lib.component.component-next :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.contexts :as contexts]
    [org.jordillonch.clojure-enterprise-project.shared-infrastructure.shared-infrastructure :as shared-infrastructure])
  (:use
    [clojure.tools.nrepl.server :only (start-server stop-server)]))

(def custom-transformations
  (comp
    simplified-handler-definitions->system-definitions
    (partial simplified-routing-definitions->system-definitions :http-server-api "api-server-routing")))

(defn clojure-enterprise-project-system []
  (system-map
    [shared-infrastructure/system
     contexts/system]
    custom-transformations))


(def system (clojure-enterprise-project-system))

(defn start []
  (println "Starting...")
  (alter-var-root #'org.jordillonch.clojure-enterprise-project.core/system component/start))

(defn stop []
  (println "Stopping...")
  (alter-var-root #'org.jordillonch.clojure-enterprise-project.core/system component/stop))

(defn -main [& args]
  (defonce server (start-server :port 7888))
  (start))
