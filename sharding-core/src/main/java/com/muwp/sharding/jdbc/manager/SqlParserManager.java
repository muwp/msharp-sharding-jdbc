package com.muwp.sharding.jdbc.manager;

import com.muwp.sharding.jdbc.bean.SqlBean;
import com.muwp.sharding.jdbc.reflect.FieldVisitor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * ShardSqlParser
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SqlParserManager {

    public static SqlParserManager INSTANCE = new SqlParserManager();

    public static SqlParserManager getInstance() {
        return INSTANCE;
    }

    public <T> SqlBean generateInsertSql(final T bean, final String databasePrefix, String tablePrefix, int databseIndex, int tableIndex) {
        final StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        if (null == tablePrefix) {
            tablePrefix = OrmManager.getTableName(bean.getClass());
        }

        sb.append(getQualifiedTableName(databasePrefix, tablePrefix, databseIndex, tableIndex))
                .append("(");

        final List<Object> params = new ArrayList<>();
        FieldVisitor.getInstance().visit(bean, (index, columnName, value) -> {
            if (index != 0) {
                sb.append(",");
            }
            sb.append(columnName);
            if (value instanceof Enum) {
                value = ((Enum<?>) value).ordinal();
            }

            params.add(value);
        });

        sb.append(") values (")
                .append(OrmManager.generateParamPlaceholders(params.size()))
                .append(")");
        return new SqlBean(sb.toString(), params.toArray());
    }

    public <T> SqlBean generateInsertSql(T bean) {
        return generateInsertSql(bean, null, null, -1, -1);
    }

    public <T> SqlBean generateInsertSql(T bean, String databasePrefix) {
        return generateInsertSql(bean, databasePrefix, null, -1, -1);
    }

    public <T> SqlBean generateInsertSql(T bean, String databasePrefix, String tablePrefix) {
        return generateInsertSql(bean, databasePrefix, tablePrefix, -1, -1);
    }

    public <T> SqlBean generateUpdateSql(T bean, String databasePrefix, String tablePrefix, int databaseIndex, int tableIndex) {
        final StringBuilder sb = new StringBuilder();
        sb.append(" update ");

        if (null == tablePrefix) {
            tablePrefix = OrmManager.getTableName(bean.getClass());
        }

        sb.append(getQualifiedTableName(databasePrefix, tablePrefix, databaseIndex, tableIndex));

        sb.append(" set ");

        final List<Object> params = new ArrayList<>();
        FieldVisitor.getInstance().visit(bean, (index, columnName, value) -> {
            if (index != 0) {
                sb.append(", ");
            }
            sb.append(columnName).append("=? ");

            if (value instanceof Enum) {
                value = ((Enum<?>) value).ordinal();
            }

            params.add(value);
        });

        sb.append(" where ID = ?");

        params.add(ReflectionManager.getInstance().getFieldValue(bean, "id"));

        return new SqlBean(sb.toString(), params.toArray());
    }

    public <T> SqlBean generateUpdateSql(T bean) {
        return generateUpdateSql(bean, null, null, -1, -1);
    }

    public <T> SqlBean generateUpdateSql(T bean, String databasePrefix) {
        return generateUpdateSql(bean, databasePrefix, null, -1, -1);
    }

    public <T> SqlBean generateUpdateSql(T bean, String databasePrefix, String tablePrefix) {
        return generateUpdateSql(bean, databasePrefix, tablePrefix, -1, -1);
    }

    public <T> SqlBean generateDeleteSql(long id, Class<T> clazz, String databasePrefix, String tablePrefix, int databaseIndex, int tableIndex) {
        final StringBuilder sb = new StringBuilder();
        sb.append("delete from ");

        if (null == tablePrefix) {
            tablePrefix = OrmManager.getTableName(clazz);
        }

        sb.append(getQualifiedTableName(databasePrefix, tablePrefix, databaseIndex, tableIndex))
                .append(" where ID = ?");

        List<Object> params = new LinkedList<Object>();
        params.add(id);

        return new SqlBean(sb.toString(), params.toArray());
    }

    public <T> SqlBean generateDeleteSql(long id, Class<T> clazz) {
        return generateDeleteSql(id, clazz, null, null, -1, -1);
    }

    public <T> SqlBean generateDeleteSql(long id, Class<T> clazz, String databasePrefix) {
        return generateDeleteSql(id, clazz, databasePrefix, null, -1, -1);
    }

    public <T> SqlBean generateDeleteSql(long id, Class<T> clazz, String databasePrefix, String tablePrefix) {
        return generateDeleteSql(id, clazz, databasePrefix, tablePrefix, -1, -1);
    }

    public <T> SqlBean generateSelectSql(String name, Object value, Class<T> clazz, String databasePrefix, String tablePrefix, int databaseIndex, int tableIndex) {
        final StringBuilder sb = new StringBuilder();
        sb.append("select * from ");

        if (StringUtils.isEmpty(tablePrefix)) {
            tablePrefix = OrmManager.getTableName(clazz);
        }

        sb.append(getQualifiedTableName(databasePrefix, tablePrefix, databaseIndex, tableIndex));

        sb.append(" where ")
                .append(name)
                .append("=?");

        return new SqlBean(sb.toString(), new Object[]{value});
    }

    public <T> SqlBean generateSelectSql(String name, Object value, Class<T> clazz) {
        return generateSelectSql(name, value, clazz, null, null, -1, -1);
    }

    public <T> SqlBean generateSelectSql(String name, Object value, Class<T> clazz, String databasePrefix) {
        return generateSelectSql(name, value, clazz, databasePrefix, null, -1, -1);
    }

    public <T> SqlBean generateSelectSql(String name, Object value, Class<T> clazz, String databasePrefix, String tablePrefix) {
        return generateSelectSql(name, value, clazz, databasePrefix, tablePrefix, -1, -1);
    }

    public <T> SqlBean generateSearchSql(T bean, String name, Object valueFrom, Object valueTo, String databasePrefix, String tablePrefix, int databaseIndex, int tableIndex, final int offset, int pageSize) {
        final StringBuilder sb = new StringBuilder();
        sb.append("select * from ");

        if (StringUtils.isEmpty(tablePrefix)) {
            tablePrefix = OrmManager.getTableName(bean.getClass());
        }

        sb.append(getQualifiedTableName(databasePrefix, tablePrefix, databaseIndex, tableIndex));

        sb.append(" where ");

        final List<Object> params = new ArrayList<>();

        FieldVisitor.getInstance().visit(bean, (index, columnName, value) -> {
            if (index != 0) {
                sb.append(" and ");
            }
            sb.append(columnName).append("=? ");

            if (value instanceof Enum) {
                value = ((Enum<?>) value).ordinal();
            }
            params.add(value);
        });

        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(valueFrom) && !StringUtils.isEmpty(valueTo)) {
            sb.append(" and ").append(name).append(">=? and ");
            sb.append(name).append("<=? ");
            params.add(valueFrom);
            params.add(valueTo);
        } else if (!StringUtils.isEmpty(name)
                && !StringUtils.isEmpty(valueFrom)
                && StringUtils.isEmpty(valueTo)) {
            sb.append(" and ").append(name).append("=? ");
            params.add(valueFrom);
        }

        if (offset >= 0 && pageSize > 0) {
            sb.append(" limit ?,?");
            params.add(offset);
            params.add(pageSize);
        }

        return new SqlBean(sb.toString(), params.toArray());
    }

    public <T> SqlBean generateSearchSql(T bean, String databasePrefix, String tablePrefix, int databaseIndex, int tableIndex) {
        return generateSearchSql(bean, null, null, null, databasePrefix, tablePrefix, databaseIndex, tableIndex, -1, -1);
    }

    public <T> SqlBean generateSearchSql(T bean, String name, Object value, String databasePrefix, String tablePrefix, int databaseIndex, int tableIndex) {
        return generateSearchSql(bean, name, value, null, databasePrefix, tablePrefix, databaseIndex, tableIndex, -1, -1);
    }

    public <T> SqlBean generateSearchSql(T bean) {
        return generateSearchSql(bean, -1, -1);
    }

    public <T> SqlBean generateSearchSql(final T bean, final int offset, final int pageSize) {
        final StringBuilder sb = new StringBuilder();
        sb.append("select * from ")
                .append(OrmManager.getTableName(bean.getClass()));

        final List<Object> paramList = new ArrayList<>();
        final StringBuilder condition = new StringBuilder();
        FieldVisitor.getInstance().visit(bean, (index, columnName, value) -> {
                    if (index != 0) {
                        condition.append(" and ");
                    }
                    if (value instanceof Enum) {
                        value = ((Enum<?>) value).ordinal();
                        condition.append(columnName).append("=? ");
                        paramList.add(value);
                    } else if (value instanceof List) {
                        final List<Object> valList = (List) value;
                        if (CollectionUtils.isEmpty(valList)) {
                            return;
                        }
                        condition
                                .append(columnName)
                                .append(" in (");
                        for (int i = 0, size = valList.size(); i < size; i++) {
                            if (i != 0) {
                                condition.append(",");
                            }
                            final Object val = valList.get(i);
                            condition.append(" ?");
                            paramList.add(val);
                        }
                        condition.append(")");
                    } else {
                        condition
                                .append(columnName)
                                .append("=? ");
                        paramList.add(value);
                    }
                }
        );

        if (paramList.size() > 0) {
            sb
                    .append(" where ")
                    .append(condition.toString());
        }

        if (offset >= 0 && pageSize > 0) {
            paramList.add(offset);
            paramList.add(pageSize);
            sb.append(" limit ?,?");
        }
        return new SqlBean(sb.toString(), paramList.toArray());
    }

    private String getQualifiedTableName(String databasePrefix, String tablePrefix, int dbIndex, int tableIndex) {
        StringBuffer sb = new StringBuffer();

        if (!StringUtils.isEmpty(databasePrefix)) {
            sb.append(databasePrefix);
        }

        if (dbIndex != -1) {
            sb.append("_")
                    .append(dbIndex)
                    .append(".");
        }

        if (!StringUtils.isEmpty(tablePrefix)) {
            sb.append(tablePrefix);
        }

        if (tableIndex != -1) {
            sb.append("_")
                    .append(tableIndex)
                    .append(" ");
        }

        return sb.toString();
    }
}
