package com.msharp.sharding.jdbc.reflect;

import com.msharp.sharding.jdbc.manager.ReflectionManager;
import com.msharp.sharding.jdbc.bean.ResultBean;
import com.msharp.sharding.jdbc.handler.SqlHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * FieldVisitor
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class FieldVisitor {

    private static final Logger log = LoggerFactory.getLogger(FieldVisitor.class);

    private static final FieldVisitor INSTANCE = new FieldVisitor();

    public static FieldVisitor getInstance() {
        return INSTANCE;
    }

    public <T> void visit(final T bean, SqlHandler sqlHandler) {
        List<ResultBean> resultBeans = ReflectionManager.getInstance().loadClassEffectiveFields(bean.getClass());
        int count = 0;
        for (int i = 0, size = resultBeans.size(); i < size; i++) {
            final ResultBean resultBean = resultBeans.get(i);
            final Field field = resultBean.getField();
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
                sqlHandler.handle(count++, resultBean.getColumnName(), value);
                field.setAccessible(access);
            } catch (Throwable e) {
                log.error("Fail to obtain bean {} property {}", bean, field, e);
            }
        }
    }
}
