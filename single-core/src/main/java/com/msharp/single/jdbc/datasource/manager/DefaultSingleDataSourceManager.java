package com.msharp.single.jdbc.datasource.manager;

import com.msharp.single.jdbc.exception.MSharpException;
import com.msharp.single.jdbc.datasource.config.DataSourceConfig;
import com.msharp.single.jdbc.datasource.jdbc.SingleDataSource;

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
