{
 :database-main                    {:minimum-idle      10
                                    :maximum-pool-size 10
                                    :adapter           "mysql"
                                    :username          "root"
                                    :password          ""
                                    :database-name     "operational"
                                    :server-name       "localhost"
                                    :port-number       3306}

 :database-backup                  {:minimum-idle      10
                                    :maximum-pool-size 10
                                    :adapter           "mysql"
                                    :username          "root"
                                    :password          ""
                                    :database-name     "analytics"
                                    :server-name       "localhost"
                                    :port-number       3306}

 :logger                           {:level     :info
                                    :spit-path "/tmp/clojure-enterprise-project/spit.log"}

 :rabbitmq-connection              {:host     "localhost"
                                    :port     5672
                                    :vhost    "/"
                                    :username "guest"
                                    :password "guest"}

 :rabbitmq-worker                  {:exchange-name "domain_events"}

 :redis-oauth2                     {:host "localhost"
                                    :port 6379}

 :redis-leaderboard                {:host "localhost"
                                    :port 6379}

 :redis-leaderboard-month-absolute {:host "localhost"
                                    :port 6379}

 :http-server-api                  {:port 5050}}

