(ns org.jordillonch.clojure-enterprise-project.context.contexts
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.leaderboard :as leaderboard]
    [org.jordillonch.clojure-enterprise-project.context.storage.storage :as storage]))

(def system
  [storage/system
   leaderboard/system])
