(ns org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.access-token-repository)

(defprotocol OAuth2AccessTokenRepository
  (add [this token token-data])
  (search [this token]))
