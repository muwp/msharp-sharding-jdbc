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

create table product_1
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
  COMMENT = 'product_1表';

create table test
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
  COMMENT = 'test表';

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


create table test_4
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
  COMMENT = 'test表5';

create table test_5
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
  COMMENT = 'test表6';

create table test_6
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
  COMMENT = 'test表7';

create table test_7
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
  COMMENT = 'test表8';



CREATE TABLE `biz_shop_product_15`
(
  `id`             bigint(22) unsigned NOT NULL AUTO_INCREMENT,
  `standard_id`    int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `supplier_id`    int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `brand_id`       int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `category_id`    int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `name`           varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `code`           varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `specification`  varchar(500)        NOT NULL DEFAULT '' COMMENT ' ',
  `photo`          varchar(500)        NOT NULL DEFAULT '' COMMENT ' ',
  `unit`           varchar(20)         NOT NULL DEFAULT '' COMMENT ' ',
  `stock`          int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `original_price` decimal(19,2)       NOT NULL DEFAULT '0.00' COMMENT ' ',
  `price`          decimal(19,2)       NOT NULL DEFAULT '0.00' COMMENT ' ',
  `status`         int(2)              NOT NULL DEFAULT '0' COMMENT ' ',
  `sale_amount`    int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `category_name`  varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `supplier_name`  varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `brand_name`     varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `delivery_time`  int(3)              NOT NULL DEFAULT '0' COMMENT ' ',
  `create_time`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `sales_volume`   decimal(11,0)       NOT NULL DEFAULT '0' COMMENT ' ',
  `is_deleted`     int(1)              NOT NULL DEFAULT '0' COMMENT ' ',
  PRIMARY KEY (`id`),
  KEY `IDX_SUPPID_CODE` (`supplier_id`, `code`) USING BTREE,
  KEY `IDX_SUPPID_STATUS` (`supplier_id`, `status`),
  KEY `IDX_CATEGORYID` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `biz_shop_product_1`
(
  `id`             bigint(22) unsigned NOT NULL AUTO_INCREMENT,
  `standard_id`    int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `supplier_id`    int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `brand_id`       int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `category_id`    int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `name`           varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `code`           varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `specification`  varchar(500)        NOT NULL DEFAULT '' COMMENT ' ',
  `photo`          varchar(500)        NOT NULL DEFAULT '' COMMENT ' ',
  `unit`           varchar(20)         NOT NULL DEFAULT '' COMMENT ' ',
  `stock`          int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `original_price` decimal(19,2)       NOT NULL DEFAULT '0.00' COMMENT ' ',
  `price`          decimal(19,2)       NOT NULL DEFAULT '0.00' COMMENT ' ',
  `status`         int(2)              NOT NULL DEFAULT '0' COMMENT ' ',
  `sale_amount`    int(11)             NOT NULL DEFAULT '0' COMMENT ' ',
  `category_name`  varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `supplier_name`  varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `brand_name`     varchar(255)        NOT NULL DEFAULT '' COMMENT ' ',
  `delivery_time`  int(3)              NOT NULL DEFAULT '0' COMMENT ' ',
  `create_time`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `sales_volume`   decimal(11,0)       NOT NULL DEFAULT '0' COMMENT ' ',
  `is_deleted`     int(1)              NOT NULL DEFAULT '0' COMMENT ' ',
  PRIMARY KEY (`id`),
  KEY `IDX_SUPPID_CODE` (`supplier_id`, `code`) USING BTREE,
  KEY `IDX_SUPPID_STATUS` (`supplier_id`, `status`),
  KEY `IDX_CATEGORYID` (`category_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `biz_shop_product_ext`
(
  `id`               bigint(22) unsigned NOT NULL AUTO_INCREMENT,
  `product_id`       int(11)             NOT NULL DEFAULT '0',
  `description`      varchar(255)        NOT NULL DEFAULT '' COMMENT '',
  `description_size` int(11)             NOT NULL DEFAULT '0',
  `detail_photo`     varchar(1000)       NOT NULL DEFAULT '' COMMENT '',
  `create_time`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`      datetime            NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `dangerous_type`   int(2)              NOT NULL DEFAULT '0' COMMENT '',
  `regulatory_type`  int(1)              NOT NULL COMMENT '',
  `cas_no`           varchar(50)         NOT NULL DEFAULT '' COMMENT 'cas号',
  `supplier_id`      int(11)             NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_PRODUCTID` (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;


CREATE TABLE `biz_shop_product_items`
(
  `id`              bigint(22) unsigned NOT NULL AUTO_INCREMENT,
  `product_id`      int(11)             NOT NULL DEFAULT '0' COMMENT '基础库商品id',
  `attribute_name`  varchar(255)        NOT NULL DEFAULT '' COMMENT '属性名称',
  `attribute_value` varchar(255)        NOT NULL DEFAULT '' COMMENT '属性值',
  `is_search`       int(1)              NOT NULL DEFAULT '1' COMMENT '0:否，1:是',
  `is_sale`         int(1)              NOT NULL DEFAULT '0' COMMENT '0:否，1:是',
  `supplier_id`     int(11)             NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IDX_PRODUCTID` (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;