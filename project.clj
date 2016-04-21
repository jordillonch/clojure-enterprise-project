(defproject clojure-enterprise-project "0.1.1-SNAPSHOT"
  :description "Example of a Clojure enterprise project"
  :dependencies [[clj-http "2.0.0"]
                 [clj-time "0.11.0"]
                 [co.paralleluniverse/pulsar "0.7.3"]
                 [com.gearswithingears/shrubbery "0.3.1"]
                 [com.novemberain/langohr "3.4.1"]
                 [com.stuartsierra/component "0.2.3"]
                 [com.taoensso/timbre "4.1.4"]
                 [com.taoensso/carmine "2.12.1"]
                 [danlentz/clj-uuid "0.1.2-SNAPSHOT"]
                 [funcool/catacumba "0.8.1"]
                 [funcool/cats "1.2.0"]
                 [hikari-cp "1.3.1"]
                 [mysql/mysql-connector-java "5.1.34"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/java.jdbc "0.4.2"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/test.check "0.9.0"]
                 [org.clojure/tools.nrepl "0.2.11"]
                 [prismatic/schema "1.0.4"]
                 [slingshot "0.12.2"]]
  :java-agents [[co.paralleluniverse/quasar-core "0.7.3"]]
  :profiles {:dev     {:source-paths ["dev" "test"]
                       :dependencies [[javax.servlet/servlet-api "2.5"]
                                      [org.clojure/tools.namespace "0.2.10"]
                                      [org.clojure/java.classpath "0.2.2"]
                                      [pjstadig/humane-test-output "0.7.1"]]
                       :plugins      [[com.jakemccrary/lein-test-refresh "0.11.0"]]
                       :injections   [(require 'pjstadig.humane-test-output)
                                      (pjstadig.humane-test-output/activate!)]}
             :uberjar {:main org.jordillonch.clojure-enterprise-project.core
                       :aot  [org.jordillonch.clojure-enterprise-project.core]}}
  :test-paths ~(->> (clojure.java.io/file "src/")
                 (file-seq)
                 (filter #(.isDirectory %))
                 (filter #(= (.getName %) "test"))
                 (map #(.getPath %))
                 (vec)))
