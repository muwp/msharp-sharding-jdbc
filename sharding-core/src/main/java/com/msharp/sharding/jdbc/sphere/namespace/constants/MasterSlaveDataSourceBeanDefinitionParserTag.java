package com.msharp.sharding.jdbc.sphere.namespace.constants;

/**
 * MasterSlaveDataSourceBeanDefinitionParserTag
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class MasterSlaveDataSourceBeanDefinitionParserTag {

    public static final String ROOT_TAG = "data-source";

    public static final String MASTER_DATA_SOURCE_NAME_ATTRIBUTE = "master-data-source-name";

    public static final String SLAVE_DATA_SOURCE_NAMES_ATTRIBUTE = "slave-data-source-names";

    public static final String STRATEGY_REF_ATTRIBUTE = "strategy-ref";

    public static final String STRATEGY_TYPE_ATTRIBUTE = "strategy-type";
}
