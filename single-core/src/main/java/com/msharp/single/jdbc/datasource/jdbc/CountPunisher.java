package com.msharp.single.jdbc.datasource.jdbc;

import com.msharp.single.jdbc.util.JDBCUtils;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountPunisher
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class CountPunisher {

    private final DataSourceLifeCycle dataSource;

    /**
     * ms; 值为0表示关闭这个功能，无任何限制
     */
    private final long timeWindow;

    private final long limit;

    private final long resetTime;

    /**
     * 0表示没有超时，没有计时
     */
    private volatile long timeWindowBegin = 0;

    private volatile long punishBeginTime = Long.MAX_VALUE;

    private final AtomicInteger count = new AtomicInteger();

    /**
     * timeWindow之内，超时（或其他意义）次数超过limit，则标识为punish, 这个punish标志resetTime过后自动复位
     *
     * @param dataSource
     * @param timeWindow
     * @param limit
     */
    public CountPunisher(DataSourceLifeCycle dataSource, long timeWindow, long limit, long resetTime) {
        this.dataSource = dataSource;
        this.timeWindow = timeWindow;
        this.limit = limit;
        this.resetTime = resetTime;
    }

    public CountPunisher(DataSourceLifeCycle dataSource, long timeWindow, long limit) {
        // 默认5分钟复位
        this(dataSource, timeWindow, limit, 5 * 60 * 100);
    }

    private void count() {
        if (timeWindow == 0) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - timeWindowBegin > timeWindow) {
            resetTimeWindow();
        }

        if (timeWindowBegin == 0) {
            timeWindowBegin = now;
        }

        if (count.incrementAndGet() >= limit) {
            punishBeginTime = now;
            dataSource.markDown();
        }
    }

    private void resetTimeWindow() {
        timeWindowBegin = 0;
        count.set(0);
    }

    /**
     * @return true表示惩罚；false表示通过
     */
    public boolean punish() {
        if (punishBeginTime == Long.MAX_VALUE || timeWindow == 0) {
            // 这里不需要复位
            return false;
        }
        if (System.currentTimeMillis() - punishBeginTime > resetTime) {
            // 超过复位时间，不再惩罚，状态复位
            punishBeginTime = Long.MAX_VALUE;
            resetTimeWindow();
            dataSource.markUp();
            return false;
        }

        return true;
    }

    public void countAndPunish(SQLException e) {
        if (JDBCUtils.isReadOnlyException(e)) {
            count();
        }
        // TODO getConnnetion loss exception
    }
}