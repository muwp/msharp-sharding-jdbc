/*
 * Copyright (c) 2011-2018, Meituan Dianping. All Rights Reserved.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.msharp.single.jdbc.single.pool;

import com.msharp.single.jdbc.constants.Constants;
import com.msharp.single.jdbc.single.config.DataSourceConfig;
import com.msharp.single.jdbc.util.JdbcDriverClassHelper;

/**
 * DataSourcePoolFactory
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class DataSourcePoolFactory {

    public static DataSourcePool buildDataSourcePool(DataSourceConfig value) {

        JdbcDriverClassHelper.loadDriverClass(value.getDriverClass(), value.getJdbcUrl());

        if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_C3P0)) {
            return new C3p0DataSourcePool();
        } else if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_TOMCAT_JDBC)) {
            return new TomcatJdbcDataSourcePool();
        } else if (value.getType().equalsIgnoreCase("druid")) {
            return new DruidDataSourcePool();
        } else if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_DBCP2)) {
            return new Dbcp2DataSourcePool();
        } else if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_DBCP)) {
            return new DbcpDataSourcePool();
        } else if (value.getType().equalsIgnoreCase(Constants.CONNECTION_POOL_TYPE_HIKARICP)) {
            return new HikariCPDataSourcePool();
        } else {
            return new DruidDataSourcePool();
            //throw new MSharpConfigException("illegal datasource pool type : " + value.getType());
        }

    }
}
