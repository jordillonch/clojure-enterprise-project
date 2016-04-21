CREATE TABLE `storage` (
  `owner`     VARCHAR(255) NOT NULL,
  `namespace` VARCHAR(255) NOT NULL,
  `key`       VARCHAR(255) NOT NULL,
  `value`     MEDIUMTEXT,
  PRIMARY KEY (`owner`, `namespace`, `key`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

