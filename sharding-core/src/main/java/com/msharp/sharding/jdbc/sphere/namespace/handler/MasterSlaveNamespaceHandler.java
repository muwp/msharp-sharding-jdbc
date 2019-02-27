package com.msharp.sharding.jdbc.sphere.namespace.handler;

import com.msharp.sharding.jdbc.sphere.namespace.constants.MasterSlaveDataSourceBeanDefinitionParserTag;
import com.msharp.sharding.jdbc.sphere.namespace.parser.MasterSlaveDataSourceBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * MasterSlaveNamespaceHandler
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class MasterSlaveNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(MasterSlaveDataSourceBeanDefinitionParserTag.ROOT_TAG, new MasterSlaveDataSourceBeanDefinitionParser());
    }
}
