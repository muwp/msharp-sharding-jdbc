package com.muwp.sharding.jdbc.reflect;

import com.muwp.sharding.jdbc.bean.FieldWrapper;
import com.muwp.sharding.jdbc.handler.SqlHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FieldVisitor
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class FieldVisitor {

    private static final Logger log = LoggerFactory.getLogger(FieldVisitor.class);

    private static final Map<Class, List<FieldWrapper>> cache = new ConcurrentHashMap<>();

    private static final FieldVisitor INSTANCE = new FieldVisitor();

    public static FieldVisitor getInstance() {
        return INSTANCE;
    }

    public <T> void visit(T bean, SqlHandler sqlHandler) {
        final Class clazz = bean.getClass();
        List<FieldWrapper> fieldWrappers = cache.get(clazz);
        if (null == fieldWrappers) {
            fieldWrappers = ReflectionUtil.getClassEffectiveFields(bean.getClass());
        }
        int count = 0;
        for (int i = 0, size = fieldWrappers.size(); i < size; i++) {
            final FieldWrapper fieldWrapper = fieldWrappers.get(i);
            final Field field = fieldWrapper.getField();
            Object value;
            try {
                final boolean access = field.isAccessible();
                field.setAccessible(true);
                value = field.get(bean);
                if (null == value) {
                    continue;
                }
                if (value instanceof Number && ((Number) value).doubleValue() == -1d) {
                    continue;
                }
                sqlHandler.handle(count++, fieldWrapper.getColumnName(), value);
                field.setAccessible(access);
            } catch (IllegalArgumentException e) {
                log.error("Fail to obtain bean {} property {}", bean, field);
                log.error("Exception--->", e);
            } catch (IllegalAccessException e) {
                log.error("Fail to obtain bean {} property {}", bean, field);
                log.error("Exception--->", e);
            }
        }
    }
}
