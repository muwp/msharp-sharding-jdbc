package com.msharp.sharding.jdbc.sphere.namespace.parser;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * ShardingStrategyBeanDefinitionParser
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public final class ShardingStrategyBeanDefinitionParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(final Element element, final ParserContext parserContext) {
        return ShardingStrategyBeanDefinition.getBeanDefinitionByElement(element);
    }
}