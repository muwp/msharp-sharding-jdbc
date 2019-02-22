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
package com.msharp.single.jdbc.single.manager;

import com.msharp.single.jdbc.exception.MSharpException;
import com.msharp.single.jdbc.single.config.DataSourceConfig;
import com.msharp.single.jdbc.single.jdbc.SingleDataSource;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * DefaultSingleDataSourceManager
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class DefaultSingleDataSourceManager implements SingleDataSourceManager {

    private Thread dataSourceMonitor;

    private BlockingQueue<SingleDataSource> toBeClosedDataSource = new LinkedBlockingQueue<SingleDataSource>();

    @Override
    public synchronized SingleDataSource createDataSource(DataSourceConfig config) {
        return new SingleDataSource(config);
    }

    @Override
    public synchronized void destoryDataSource(SingleDataSource dataSource) {
        if (dataSource != null) {
            this.toBeClosedDataSource.offer(dataSource);
        }
    }

    @Override
    public synchronized void init() {
        if (dataSourceMonitor == null) {
            dataSourceMonitor = new Thread(new CloseDataSourceTask());

            dataSourceMonitor.setDaemon(true);
            dataSourceMonitor.setName("Dal-" + CloseDataSourceTask.class.getSimpleName());
            dataSourceMonitor.start();
        }
    }

    @Override
    public synchronized void stop() {
        if (dataSourceMonitor != null) {
            dataSourceMonitor.interrupt();
        }
    }

    class CloseDataSourceTask implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                SingleDataSource dataSource = null;
                try {
                    dataSource = toBeClosedDataSource.take();
                    dataSource.close();
                } catch (MSharpException e) {
                    if (dataSource != null) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                        } catch (InterruptedException e1) {
                        }

                        toBeClosedDataSource.offer(dataSource);
                    }
                } catch (Exception ignore) {
                }
            }
        }
    }
}
