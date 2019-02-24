package com.msharp.single.jdbc.config;

import java.util.HashMap;
import java.util.Map;

/**
 * ServiceConfigBuilder
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class ServiceConfigBuilder {

    private Map<String, Object> configMap = new HashMap<>();

    public static ServiceConfigBuilder newInstance() {
        return new ServiceConfigBuilder();
    }

    public ServiceConfigBuilder putValue(String key, Object value) {
        this.configMap.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return configMap;
    }
}
