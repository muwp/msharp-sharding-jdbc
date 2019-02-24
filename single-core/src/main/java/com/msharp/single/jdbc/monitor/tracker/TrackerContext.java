package com.msharp.single.jdbc.monitor.tracker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TrackerContext
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/23 13:51
 **/
public class TrackerContext implements Serializable {

    private static final long serialVersionUID = -2035566050630732806L;

    /**
     * this is optional now
     */
    private String token;

    /**
     * current (app node/remote-service)'s name,e.g. [web], [10.1.1.73-ShopPicService.loadShopPic]
     */
    private String location;

    /**
     * whether track required
     */
    private boolean trackRequired;

    /**
     * the context's created timestamp, in nanoseconds
     * relative to current app
     */
    private long createdTime;

    /**
     * remote-service's consumed time, in nanoseconds
     */
    private long timeConsumed;

    /**
     * the rpc context's arrive timestamp, in nanoseconds
     * relative to remote service's app
     */
    private long arriveTime;

    /**
     * whether authenticated user
     */
    private boolean authenticated;

    private boolean succeed = true;

    private SqlExecutionTrace sqlExecutionTrace = new SqlExecutionTrace();

    private CacheExecutionTrace cacheExecutionTrace = new CacheExecutionTrace();

    private Map<String, Object> extension;

    private List<TrackerContext> remoteContexts = new ArrayList<TrackerContext>();

    /**
     *
     */
    public TrackerContext() {
        this.createdTime = System.nanoTime();
    }

    /**
     * create specified location's rpc tracker context
     *
     * @param location
     * @return
     */
    public TrackerContext createRemoteContext(String location) {
        TrackerContext remoteTrackerContext = new TrackerContext();
        remoteTrackerContext.token = token;
        remoteTrackerContext.location = location;
        remoteTrackerContext.authenticated = authenticated;
        remoteTrackerContext.extension = extension;
        remoteTrackerContext.trackRequired = trackRequired;
        return remoteTrackerContext;
    }

    public long getTotalRemoteTimeConsumed() {
        long totalRemoteTimes = 0;
        if (remoteContexts != null && !remoteContexts.isEmpty()) {
            for (TrackerContext remoteContext : remoteContexts) {
                totalRemoteTimes += remoteContext.getTimeConsumed();
            }
        }
        return totalRemoteTimes;
    }

    public long getTotalSqlTimes() {
        long totalTimes = sqlExecutionTrace.getTimes();
        if (remoteContexts != null && !remoteContexts.isEmpty()) {
            for (TrackerContext remoteContext : remoteContexts) {
                totalTimes += remoteContext.getTotalSqlTimes();
            }
        }
        return totalTimes;
    }

    public long getTotalSqlTimeConsumed() {
        long totalTimeConsumed = sqlExecutionTrace.getTimeConsumed();
        if (remoteContexts != null && !remoteContexts.isEmpty()) {
            for (TrackerContext remoteContext : remoteContexts) {
                totalTimeConsumed += remoteContext.getTotalSqlTimeConsumed();
            }
        }
        return totalTimeConsumed;
    }

    public long getTotalCacheTimeConsumed() {
        long totalTimeConsumed = cacheExecutionTrace.getTimeConsumed();
        if (remoteContexts != null && !remoteContexts.isEmpty()) {
            for (TrackerContext remoteContext : remoteContexts) {
                totalTimeConsumed += remoteContext.getTotalCacheTimeConsumed();
            }
        }
        return totalTimeConsumed;
    }

    /**
     * @param context
     */
    public void addRemoteContext(TrackerContext context) {
        remoteContexts.add(context);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public SqlExecutionTrace getSqlExecutionTrace() {
        return sqlExecutionTrace;
    }

    public void setSqlExecutionTrace(SqlExecutionTrace sqlTrace) {
        this.sqlExecutionTrace = sqlTrace;
    }

    public CacheExecutionTrace getCacheExecutionTrace() {
        return cacheExecutionTrace;
    }

    public void setCacheExecutionTrace(CacheExecutionTrace cacheTrace) {
        this.cacheExecutionTrace = cacheTrace;
    }

    public Map<String, Object> getExtension() {
        return extension;
    }

    public void setExtension(Map<String, Object> extension) {
        this.extension = extension;
    }

    public void addExtension(String name, Object value) {
        if (this.extension == null) {
            this.extension = new HashMap<String, Object>();
        }
        this.extension.put(name, value);
    }

    public Object getExtension(String name) {
        return extension != null ? extension.get(name) : null;
    }

    public List<TrackerContext> getRemoteContexts() {
        return remoteContexts;
    }

    public void setRemoteContexts(List<TrackerContext> rpcContexts) {
        this.remoteContexts = rpcContexts;
    }

    public boolean isTrackRequired() {
        return trackRequired;
    }

    public void setTrackRequired(boolean trackRequired) {
        this.trackRequired = trackRequired;
    }

    public long getTimeConsumed() {
        return timeConsumed;
    }

    public void setTimeConsumed(long timeConsumed) {
        this.timeConsumed = timeConsumed;
    }

    /**
     * set remote tracker returned time
     *
     * @param returnTime in nanoseconds
     */
    public void setReturnTime(long returnTime) {
        this.timeConsumed = returnTime - createdTime;
    }

    public void setReturnTimeIfRequired(long returnTime) {
        if (timeConsumed <= 0) {
            setReturnTime(returnTime);
        }
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

}