package com.msharp.sharding.jdbc.sphere.namespace.parser;

import com.google.common.base.Strings;
import com.msharp.sharding.jdbc.sphere.namespace.constants.ShardingStrategyBeanDefinitionParserTag;
import io.shardingsphere.api.config.strategy.*;
import io.shardingsphere.core.exception.ShardingException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.w3c.dom.Element;

/**
 * ShardingStrategyBeanDefinition
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public final class ShardingStrategyBeanDefinition {

    public static AbstractBeanDefinition getBeanDefinitionByElement(final Element element) {
        String type = element.getLocalName();
        switch (type) {
            case ShardingStrategyBeanDefinitionParserTag.STANDARD_STRATEGY_ROOT_TAG:
                return getStandardShardingStrategyConfigBeanDefinition(element);
            case ShardingStrategyBeanDefinitionParserTag.COMPLEX_STRATEGY_ROOT_TAG:
                return getComplexShardingStrategyConfigBeanDefinition(element);
            case ShardingStrategyBeanDefinitionParserTag.INLINE_STRATEGY_ROOT_TAG:
                return getInlineShardingStrategyConfigBeanDefinition(element);
            case ShardingStrategyBeanDefinitionParserTag.HINT_STRATEGY_ROOT_TAG:
                return getHintShardingStrategyConfigBeanDefinition(element);
            case ShardingStrategyBeanDefinitionParserTag.NONE_STRATEGY_ROOT_TAG:
                return getNoneShardingStrategyConfigBeanDefinition();
            default:
                throw new ShardingException("Cannot support type: %s", type);
        }
    }

    private static AbstractBeanDefinition getStandardShardingStrategyConfigBeanDefinition(final Element element) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(StandardShardingStrategyConfiguration.class);
        factory.addConstructorArgValue(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.SHARDING_COLUMN_ATTRIBUTE));
        factory.addConstructorArgReference(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.PRECISE_ALGORITHM_REF_ATTRIBUTE));
        if (!Strings.isNullOrEmpty(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.RANGE_ALGORITHM_REF_ATTRIBUTE))) {
            factory.addConstructorArgReference(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.RANGE_ALGORITHM_REF_ATTRIBUTE));
        }
        return factory.getBeanDefinition();
    }

    private static AbstractBeanDefinition getComplexShardingStrategyConfigBeanDefinition(final Element element) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ComplexShardingStrategyConfiguration.class);
        factory.addConstructorArgValue(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.SHARDING_COLUMNS_ATTRIBUTE));
        factory.addConstructorArgReference(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.ALGORITHM_REF_ATTRIBUTE));
        return factory.getBeanDefinition();
    }

    private static AbstractBeanDefinition getInlineShardingStrategyConfigBeanDefinition(final Element element) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(InlineShardingStrategyConfiguration.class);
        factory.addConstructorArgValue(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.SHARDING_COLUMN_ATTRIBUTE));
        factory.addConstructorArgValue(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.ALGORITHM_EXPRESSION_ATTRIBUTE));
        return factory.getBeanDefinition();
    }

    private static AbstractBeanDefinition getHintShardingStrategyConfigBeanDefinition(final Element element) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(HintShardingStrategyConfiguration.class);
        factory.addConstructorArgReference(element.getAttribute(ShardingStrategyBeanDefinitionParserTag.ALGORITHM_REF_ATTRIBUTE));
        return factory.getBeanDefinition();
    }

    private static AbstractBeanDefinition getNoneShardingStrategyConfigBeanDefinition() {
        return BeanDefinitionBuilder.rootBeanDefinition(NoneShardingStrategyConfiguration.class).getBeanDefinition();
    }
}