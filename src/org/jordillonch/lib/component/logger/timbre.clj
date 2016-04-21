(ns org.jordillonch.lib.component.logger.timbre
  (:require
    [taoensso.timbre :as timbre]
    [com.stuartsierra.component :as component]))

; @todo improve settings
(defrecord TimbreLogger [settings]
  component/Lifecycle

  (start [component]
    (timbre/merge-config! {:level     (:level settings)
                           :appenders {
                                       :spit (timbre/spit-appender
                                               {:fname (:spit-path settings)})}})
    component)

  (stop [component] component))

(defn new-logger-timbre [settings]
  (map->TimbreLogger {:settings settings}))

(defn set-loglevel-debug []
  (timbre/merge-config! {:level :debug}))

(defn set-loglevel-info []
  (timbre/merge-config! {:level :info}))
