package com.muwp.sharding.jdbc.bean;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * FieldWrapper
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class FieldWrapper implements Serializable {

    /**
     * 字段
     */
    private Field field;

    /**
     * java 字段名称
     */
    private String fieldName;

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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FieldWrapper{");
        sb.append("field=").append(field);
        sb.append(", fieldName='").append(fieldName).append('\'');
        sb.append(", columnName='").append(columnName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
