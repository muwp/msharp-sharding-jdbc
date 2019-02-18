package com.muwp.sharding.jdbc.reflect;

import com.muwp.sharding.jdbc.bean.FieldWrapper;
import com.muwp.sharding.jdbc.manager.OrmManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * ReflectionUtil
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public abstract class ReflectionUtil {

    private static final Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

    public static List<FieldWrapper> getClassEffectiveFields(Class<? extends Object> clazz) {
        final List<FieldWrapper> effectiveFields = new ArrayList<>();
        String columnName;
        while (clazz != null) {
            final Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Transient.class)) {
                    continue;
                }
                final Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    columnName = column.name();
                } else {
                    columnName = OrmManager.javaFieldName2DbFieldName(field.getName());
                }
                if (!field.isAccessible()) {
                    try {
                        final Method method = clazz.getMethod(fieldName2GetterName(field.getName()));
                        if (method.getReturnType() != field.getType()) {
                            log.error("The getter for field {} may not be correct.", field);
                            continue;
                        }
                    } catch (NoSuchMethodException e) {
                        log.error("Fail to obtain getter method for non-accessible field {}.", field);
                        log.error("Exception--->", e);
                        continue;
                    } catch (SecurityException e) {
                        log.error("Fail to obtain getter method for non-accessible field {}.", field);
                        log.error("Exception--->", e);
                        continue;
                    }
                }
                field.setAccessible(true);
                final FieldWrapper fieldWrapper = new FieldWrapper();
                fieldWrapper.setField(field);
                fieldWrapper.setFieldName(field.getName());
                fieldWrapper.setColumnName(columnName);
                effectiveFields.add(fieldWrapper);
            }
            clazz = clazz.getSuperclass();
        }
        return effectiveFields;
    }

    public static String fieldName2GetterName(String fieldName) {
        return "get" + StringUtils.capitalize(fieldName);
    }

    public static String fieldName2SetterName(String fieldName) {
        return "set" + StringUtils.capitalize(fieldName);
    }

    public static <T> Object getFieldValue(T bean, String fieldName) {
        Field field = null;
        try {
            field = bean.getClass().getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            log.error("Fail to obtain field {} from bean {}.", fieldName, bean);
            log.error("Exception--->", e);
            throw new IllegalStateException("Refelction error: ", e);
        } catch (SecurityException e) {
            log.error("Fail to obtain field {} from bean {}.", fieldName, bean);
            log.error("Exception--->", e);
            throw new IllegalStateException("Refelction error: ", e);
        }

        boolean access = field.isAccessible();
        field.setAccessible(true);

        Object result = null;
        try {
            result = field.get(bean);
        } catch (IllegalArgumentException e) {
            log.error("Fail to obtain field {}'s value from bean {}.", fieldName, bean);
            log.error("Exception--->", e);
            throw new IllegalStateException("Refelction error: ", e);
        } catch (IllegalAccessException e) {
            log.error("Fail to obtain field {}'s value from bean {}.", fieldName, bean);
            log.error("Exception--->", e);
            throw new IllegalStateException("Refelction error: ", e);
        }
        field.setAccessible(access);

        return result;
    }

    public static Method searchEnumSetter(Class<?> clazz, String methodName) {
        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                if (method.getParameterCount() > 0) {
                    Class<?> paramType = method.getParameterTypes()[0];
                    if (Enum.class.isAssignableFrom(paramType)) {
                        return method;
                    }
                }
            }
        }

        return null;
    }
}
