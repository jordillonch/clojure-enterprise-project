# Example of a Clojure enterprise project


## Installation

- Create a MySQL database called `analytics`.
- Dump this SQL:

```
CREATE TABLE `domain_event_2` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `aggregate_id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `data` varchar(21000) NOT NULL,
  `occurred_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `in_aggregate_id` (`aggregate_id`),
  KEY `in_name` (`name`),
  KEY `in_occurred_on` (`occurred_on`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

- Create a MySQL database called `operational`.
- Dump this SQL:

```
CREATE TABLE `storage` (
  `owner` varchar(255) NOT NULL,
  `namespace` varchar(255) NOT NULL,
  `key` varchar(255) NOT NULL,
  `value` mediumtext,
  PRIMARY KEY (`owner`,`namespace`,`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8
```

- Configure:
    - Copy `resources/config/config_shared_infrastructure-sample.clj` to `./config_shared_infrastructure.clj`
    - Configure files created.

- Install and run RabbitMQ.

- Go to the REPL:

`(go)`


## Test

To test you can execute `lein test`, but if you're in development and want the tests to auto-execute run `lein test-refresh`


## Compile, run and connect to the REPL

- Compile and run:

```
lein uberjar
java -javaagent:target/quasar-core-0.7.3.jar -jar target/clojure-enterprise-project-0.1.1-SNAPSHOT-standalone.jar
lein repl :connect localhost:7888
```

- Note: probably you have to copy the quasar jar:
    
```
cp ~/.m2/repository/co/paralleluniverse/quasar-core/0.7.3/quasar-core-0.7.3.jar target/
```

## Test storage

- Add an oauth2 access token from REPL:
```
(org.jordillonch.clojure-enterprise-project.shared-infrastructure.oauth2.test.access-token-repository-redis-test/add-token-example "foo")
```

- From console:

```
curl http://localhost:5050/storage/namespace/key -X PUT -v --data '{"value": "some application new data"}' -H "Content-Type: application/json" --header "Authorization: Bearer foo"
curl http://localhost:5050/storage/namespace/key -v --header "Authorization: Bearer foo" 
curl http://localhost:5050/storage/namespace/key -X DELETE -v --header "Authorization: Bearer foo"

curl http://localhost:5051/leaderboard/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/user/00000000-0000-0000-0000-000000000000 -X PATCH -v --data '{"value": "some application new data2"}' -H "Content-Type: application/json" --header "Authorization: Bearer foo"
curl http://localhost:5051/leaderboard/aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa/user/00000000-0000-0000-0000-000000000000 -v --header "Authorization: Bearer foo"
```
