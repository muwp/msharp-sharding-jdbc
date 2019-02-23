package com.msharp.single.jdbc.jtemplate.manager;

import com.msharp.single.jdbc.jtemplate.bean.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ReflectionManager
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ReflectionManager {

    private static final Logger log = LoggerFactory.getLogger(ReflectionManager.class);

    private static final ReflectionManager INSTANCE = new ReflectionManager();

    private static final Map<Class, List<ResultBean>> cache = new ConcurrentHashMap<>();

    public static ReflectionManager getInstance() {
        return INSTANCE;
    }

    public List<ResultBean> loadClassEffectiveFields(Class<? extends Object> clazz) {
        List<ResultBean> resultBeans = cache.get(clazz);
        if (null == resultBeans) {
            resultBeans = getClassEffectiveFields(clazz);
        }
        return resultBeans;
    }

    private List<ResultBean> getClassEffectiveFields(Class<? extends Object> clazz) {
        final List<ResultBean> resultBeans = new ArrayList<>();
        String columnName;
        while (clazz != null) {
            final Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                final ResultBean resultBean = new ResultBean();

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
                    field.setAccessible(true);
                }
                try {
                    final Method method = clazz.getMethod(fieldName2GetterName(field.getName()));
                    resultBean.setMethod(method);
                    resultBean.setMethodName(method.getName());
                    if (method.getReturnType() != field.getType()) {
                        log.error("The getter for field {} may not be correct.", field);
                        continue;
                    }
                } catch (Throwable e) {
                    log.error("Fail to obtain getter method for non-accessible field {}.", field, e);
                    continue;
                }
                field.setAccessible(true);
                resultBean.setField(field);
                resultBean.setFieldName(field.getName());
                resultBean.setColumnName(columnName);
                resultBeans.add(resultBean);
            }
            clazz = clazz.getSuperclass();
        }
        return resultBeans;
    }

    public String fieldName2GetterName(String fieldName) {
        return "get" + StringUtils.capitalize(fieldName);
    }

    public String fieldName2SetterName(String fieldName) {
        return "set" + StringUtils.capitalize(fieldName);
    }

    public <T> Object getFieldValue(T bean, String fieldName) {
        Field field = null;
        final List<ResultBean> resultBeans = cache.get(bean.getClass());
        for (int i = 0; i < resultBeans.size(); i++) {
            if (fieldName.equals(resultBeans.get(i).getFieldName())) {
                field = resultBeans.get(i).getField();
                break;
            }
        }
        if (null == field) {
            throw new IllegalStateException("fieldName:" + fieldName + " not found");
        }

        Object result;
        try {
            result = field.get(bean);
        } catch (Throwable e) {
            log.error("Fail to obtain field {}'s value from bean {}.", fieldName, bean);
            throw new IllegalStateException("Refelction error: ", e);
        }
        return result;
    }

    public Method searchEnumSetter(Class<?> clazz, String methodName) {
        Method method = null;
        final List<ResultBean> resultBeans = cache.get(clazz);
        for (int i = 0; i < resultBeans.size(); i++) {
            if (methodName.equals(resultBeans.get(i).getMethodName())) {
                method = resultBeans.get(i).getMethod();
                break;
            }
        }
        return method;
    }
}
