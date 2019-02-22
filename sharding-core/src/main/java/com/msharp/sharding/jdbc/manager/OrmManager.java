package com.msharp.sharding.jdbc.manager;

import com.mysql.jdbc.ResultSetMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Table;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OrmManager
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class OrmManager {

    private static final Logger log = LoggerFactory.getLogger(OrmManager.class);

    private static Map<Class, String> className2DbTableCache = new ConcurrentHashMap<>();

    private static final OrmManager INSTANCE = new OrmManager();

    public static OrmManager getInstance() {
        return INSTANCE;
    }

    public static String getTableName(final Class clazz) {
        String tableName = className2DbTableCache.get(clazz);
        if (null != tableName) {
            return tableName;
        }
        tableName = loadDbTableName(clazz);
        return tableName;
    }

    private static synchronized String loadDbTableName(Class clazz) {
        String tableName = className2DbTableCache.get(clazz);
        if (null != tableName) {
            return tableName;
        }
        final Table table = (Table) clazz.getAnnotation(Table.class);
        if (table != null) {
            tableName = table.name();
            className2DbTableCache.put(clazz, tableName);
        } else {
            tableName = getTableName(clazz.getSimpleName());
            className2DbTableCache.put(clazz, tableName);
        }
        return tableName;
    }

    private synchronized static String getTableName(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char val = name.charAt(i);
            if (Character.isUpperCase(val) && i != 0) {
                sb.append("_");
                val = Character.toLowerCase(val);
            }
            sb.append(val);
        }
        return sb.toString();
    }

    public static String javaFieldName2DbFieldName(String name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char val = name.charAt(i);
            if (Character.isUpperCase(val)) {
                sb.append("_");
                val = Character.toLowerCase(val);
            }
            sb.append(val);
        }
        return sb.toString();
    }

    public static String dbFieldName2JavaFieldName(String name) {
        StringBuilder sb = new StringBuilder();
        boolean lower = true;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '_') {
                lower = false;
                continue;
            }
            if (lower) {
                sb.append(Character.toLowerCase(name.charAt(i)));
            } else {
                sb.append(Character.toUpperCase(name.charAt(i)));
                lower = true;
            }
        }
        return sb.toString();
    }

    public static String generateParamPlaceholders(int count) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append("?");
        }

        return sb.toString();
    }

    public static <T> T convertRow2Bean(ResultSet rs, Class<T> clazz) {
        try {
            T bean = clazz.newInstance();
            final ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                int columnType = rsmd.getColumnType(i);
                String columnName = rsmd.getColumnName(i);
                String fieldName = OrmManager.dbFieldName2JavaFieldName(columnName);
                String setterName = ReflectionManager.getInstance().fieldName2SetterName(fieldName);

                if (columnType == Types.SMALLINT) {
                    Method setter = ReflectionManager.getInstance().searchEnumSetter(clazz, setterName);
                    Class<?> enumParamClazz = setter.getParameterTypes()[0];
                    Method enumParseFactoryMethod = enumParamClazz.getMethod("parse", int.class);
                    Object value = enumParseFactoryMethod.invoke(enumParamClazz, rs.getInt(i));
                    setter.invoke(bean, value);
                } else {
                    Class<? extends Object> param;
                    Object value;
                    switch (columnType) {
                        case Types.VARCHAR:
                            param = String.class;
                            value = rs.getString(i);
                            break;
                        case Types.BIGINT:
                            param = long.class;
                            value = rs.getLong(i);
                            break;
                        case Types.INTEGER:
                            param = int.class;
                            value = rs.getInt(i);
                            break;
                        case Types.DATE:
                            param = Date.class;
                            value = rs.getTimestamp(i);
                            break;
                        case Types.TIMESTAMP:
                            param = Date.class;
                            value = rs.getTimestamp(i);
                            break;
                        default:
                            log.error("sharding-jdbc doesn't support column {} type {}.", columnName, columnType);
                            throw new Exception("Db column not supported.");
                    }

                    final Method setter = clazz.getMethod(setterName, param);
                    setter.invoke(bean, value);
                }
            }

            return bean;
        } catch (Exception e) {
            log.error("Fail to operator on ResultSet metadata for clazz {}.", clazz);
            log.error("Exception--->", e);
            throw new IllegalStateException("Fail to operator on ResultSet metadata.", e);
        }
    }
}
