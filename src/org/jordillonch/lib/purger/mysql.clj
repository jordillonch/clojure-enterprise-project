(ns org.jordillonch.lib.purger.mysql
  (:require
    [clojure.java.jdbc :as j]))

(defn reset [connection]
  (j/db-do-commands connection false "SET FOREIGN_KEY_CHECKS = 0")
  (->> (j/query connection ["SHOW TABLES"])
       (map #(first (vals %)))
       (map #(j/db-do-commands connection false (str "TRUNCATE TABLE " %)))
       (dorun))
  (j/db-do-commands connection false "SET FOREIGN_KEY_CHECKS = 0"))
