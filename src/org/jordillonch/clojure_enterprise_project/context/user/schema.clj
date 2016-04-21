(ns org.jordillonch.clojure-enterprise-project.context.user.schema
  (:require
    [schema.core :as s]))

(def UserId s/Uuid)

(defn user-id [id]
  (s/validate UserId id))