msharp-sharding-jdbc框架入门向导

什么是msharp-sharding-jdbc

msharp-sharding-jdbc扩展了Spring的JdbcTemplate, 在JdbcTemplate上增加了分库分表，读写分离和失效转移等功能，并与Spring JDBC保持相同的风格，简单实用，避免外部依赖，不需要类似cobar的代理服务器，堪称可伸缩的Spring JdbcTemplate。
一方面，它对于单库单表扩展了JdbcTemplate模板, 使其成为一个简单的ORM框架，可以直接对领域对象模型进行持久和搜索操作，并且实现了读写分离。
另一方面，对于分库分表它与JdbcTemplate保持同样的风格，不但提供了一个简单的ORM框架，可以直接对领域对象模型进行持久和搜索操作，还是先了数据分片和读写分离等高级功能。
另外，扩展的msharp-sharding-jdbc保持与原有JdbcTemplate完全兼容，对于特殊需求，完全可以回溯到原有JdbcTemplate提供的功能，即使用JDBC的方式来解决，这里面体现了通用和专用原则，通用原则解决80%的事情，而专用原则解决剩余的20%的事情。
此项目也提供了一个方便的脚本，可以一次性的建立多库多表。

怎么使用msharp-sharding-jdbc

我们已经完整的实现了一个具有分库分表功能的框架sharding-jdbc，现在，让我们提供一个示例演示在我们的应用中怎么来使用这个框架，sharding-core/src/main/test中的源代码。

首先，假设我们应用中有个表需要增删改查，它的DDL脚本如下：
CREATE DATABASE if not exists sharding_jdbc_I; #(I指1,2,3,4);

create table test_0
(
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `appkey`      VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'appkey',
  `name`        VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'name',
  `age`         INT(11)         NOT NULL DEFAULT 0 COMMENT 'age',
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = 'test表1';

create table test_1
(
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `appkey`      VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'appkey',
  `name`        VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'name',
  `age`         INT(11)         NOT NULL DEFAULT 0 COMMENT 'age',
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = 'test表2';

create table test_2
(
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
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
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `appkey`      VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'appkey',
  `name`        VARCHAR(25)     NOT NULL DEFAULT '' COMMENT 'name',
  `age`         INT(11)         NOT NULL DEFAULT 0 COMMENT 'age',
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = 'test表4';
  
  
 我们把这个DDL脚本保存到resources/schema.sql文件中，然后，我们需要准备好一个Mysql的数据库实例，实例端口为localhost:3306, 因为环境的限制，我们用着一个数据库实例，我们为sharding_jdbc设计了1个数据库实例、每个实例1个数据库、每个数据库4个表，共4个分片表。
 
 待续。。。。。。