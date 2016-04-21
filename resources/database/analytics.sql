CREATE TABLE `domain_event_2` (
  `id`           BIGINT(20)     NOT NULL AUTO_INCREMENT,
  `aggregate_id` VARCHAR(255)   NOT NULL,
  `name`         VARCHAR(255)   NOT NULL,
  `data`         VARCHAR(21000) NOT NULL,
  `occurred_on`  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `in_aggregate_id` (`aggregate_id`),
  KEY `in_name` (`name`),
  KEY `in_occurred_on` (`occurred_on`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
