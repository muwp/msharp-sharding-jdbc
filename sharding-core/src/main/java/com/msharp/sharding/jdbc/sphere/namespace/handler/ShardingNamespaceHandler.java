package com.msharp.sharding.jdbc.sphere.namespace.handler;

 import com.msharp.sharding.jdbc.sphere.namespace.constants.ShardingDataSourceBeanDefinitionParserTag;
 import com.msharp.sharding.jdbc.sphere.namespace.constants.ShardingStrategyBeanDefinitionParserTag;
 import com.msharp.sharding.jdbc.sphere.namespace.parser.ShardingDataSourceBeanDefinitionParser;
 import com.msharp.sharding.jdbc.sphere.namespace.parser.ShardingStrategyBeanDefinitionParser;
 import org.springframework.beans.factory.xml.NamespaceHandlerSupport;


/**
 * ShardingNamespaceHandler
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public final class ShardingNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(ShardingStrategyBeanDefinitionParserTag.STANDARD_STRATEGY_ROOT_TAG, new ShardingStrategyBeanDefinitionParser());
        registerBeanDefinitionParser(ShardingStrategyBeanDefinitionParserTag.COMPLEX_STRATEGY_ROOT_TAG, new ShardingStrategyBeanDefinitionParser());
        registerBeanDefinitionParser(ShardingStrategyBeanDefinitionParserTag.INLINE_STRATEGY_ROOT_TAG, new ShardingStrategyBeanDefinitionParser());
        registerBeanDefinitionParser(ShardingStrategyBeanDefinitionParserTag.HINT_STRATEGY_ROOT_TAG, new ShardingStrategyBeanDefinitionParser());
        registerBeanDefinitionParser(ShardingStrategyBeanDefinitionParserTag.NONE_STRATEGY_ROOT_TAG, new ShardingStrategyBeanDefinitionParser());
        registerBeanDefinitionParser(ShardingDataSourceBeanDefinitionParserTag.ROOT_TAG, new ShardingDataSourceBeanDefinitionParser());
    }
}