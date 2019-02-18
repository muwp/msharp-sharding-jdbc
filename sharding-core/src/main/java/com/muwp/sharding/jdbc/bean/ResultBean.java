package com.muwp.sharding.jdbc.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ResultBean
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ResultBean implements Serializable {

    /**
     * 字段
     */
    private Field field;

    /**
     * 方法
     */
    private Method method;

    /**
     * java 字段名称
     */
    private String fieldName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * mysql 数据库字段名称
     */
    private String columnName;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResultBean{");
        sb.append("field=").append(field);
        sb.append(", method=").append(method);
        sb.append(", fieldName='").append(fieldName).append('\'');
        sb.append(", methodName='").append(methodName).append('\'');
        sb.append(", columnName='").append(columnName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
