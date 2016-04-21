(ns org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.test.score.updater.behaviour
  (:require
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.finder :as finder]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.schema :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.leaderboard.module.score.domain.repository :refer :all]
    [org.jordillonch.clojure-enterprise-project.context.user.schema :refer :all]
    [schema.experimental.generators :as g]
    [shrubbery.core :refer :all]
    [slingshot.test])
  (:use
    [clojure.test]))

