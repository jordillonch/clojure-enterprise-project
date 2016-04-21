(ns org.jordillonch.lib.component.rabbitmq.connection
  (:require
    [com.stuartsierra.component :as component]
    [langohr.core :as rmq]
    [taoensso.timbre :as log]))

(defrecord RabbitMQConnection [connection settings]
  component/Lifecycle

  (start [component]
    (log/debug "Starting RabbitMQ connection...")
    (assoc component :connection (rmq/connect settings)))

  (stop [component]
    (log/debug "Stopping RabbitMQ connection...")
    (when connection (rmq/close connection))
    (assoc component :connection nil)))

(defn new-rabbitmq-connection [settings]
  (map->RabbitMQConnection {:settings settings}))
