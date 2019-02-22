## 创建数据库msharp_sharding_jdbc
CREATE DATABASE if not exists sharding_jdbc_I; #(I指1,2,3,4);

# noinspection SqlNoDataSourceInspection
create table product
(
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appkey`      VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'appkey',
  `name`        VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'name',
  `age`         INT(11)         NOT NULL DEFAULT 0 COMMENT 'age',
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'product表';

create table test_0
(
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appkey`      VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'appkey',
  `name`        VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'name',
  `age`         INT(11)         NOT NULL DEFAULT 0 COMMENT 'age',
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'test表1';

create table test_1
(
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appkey`      VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'appkey',
  `name`        VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'name',
  `age`         INT(11)         NOT NULL DEFAULT 0 COMMENT 'age',
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'test表2';

create table test_2
(
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appkey`      VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'appkey',
  `name`        VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'name',
  `age`         INT(11)         NOT NULL DEFAULT 0 COMMENT 'age',
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'test表3';

create table test_3
(
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `appkey`      VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'appkey',
  `name`        VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'name',
  `age`         INT(11)         NOT NULL DEFAULT 0 COMMENT 'age',
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = 'test表4';