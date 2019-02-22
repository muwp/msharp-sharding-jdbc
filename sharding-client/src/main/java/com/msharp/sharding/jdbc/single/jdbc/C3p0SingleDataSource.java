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
package com.msharp.sharding.jdbc.single.jdbc;

import com.msharp.sharding.jdbc.log.Logger;
import com.msharp.sharding.jdbc.log.LoggerFactory;

/**
 * C3p0SingleDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class C3p0SingleDataSource extends SingleDataSource {

    protected static final Logger LOGGER = LoggerFactory.getLogger(C3p0SingleDataSource.class);

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
