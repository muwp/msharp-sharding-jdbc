package com.msharp.single.jdbc.monitor.util;

/**
 * MonitorUtils
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class MonitorUtils {

    public static final int BIG_SQL = 102400;

    public static final int BIG_RESPONSE = 100000;

    public MonitorUtils() {
    }

    public static String getSqlLengthName(int length) {
        if (length <= 1024) {
            return "<= 1K";
        } else if (length <= 10240) {
            return "<= 10K";
        } else if (length <= 102400) {
            return "<= 100K";
        } else if (length <= 1048576) {
            return "<= 1M";
        } else if (length <= 10485760) {
            return "<= 10M";
        } else {
            return length <= 104857600 ? "<= 100M" : "> 100M";
        }
    }

    public static String getSqlRowsName(int rows) {
        if (rows <= 10) {
            return "<= 10";
        } else if (rows <= 100) {
            return "<= 100";
        } else if (rows <= 1000) {
            return "<= 1000";
        } else if (rows <= 10000) {
            return "<= 10k";
        } else {
            return rows <= 100000 ? "<= 100k" : "> 100k";
        }
    }
}
