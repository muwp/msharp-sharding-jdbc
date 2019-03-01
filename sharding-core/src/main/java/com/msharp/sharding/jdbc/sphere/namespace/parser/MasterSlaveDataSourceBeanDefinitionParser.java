package com.msharp.sharding.jdbc.sphere.namespace.parser;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.msharp.sharding.jdbc.sphere.namespace.constants.MasterSlaveDataSourceBeanDefinitionParserTag;
import com.msharp.sharding.jdbc.sphere.namespace.constants.ShardingDataSourceBeanDefinitionParserTag;
import com.msharp.sharding.jdbc.sphere.spring.SpringMasterSlaveDataSource;
import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.*;

/**
 * MasterSlaveDataSourceBeanDefinitionParser
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class MasterSlaveDataSourceBeanDefinitionParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(final Element element, final ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringMasterSlaveDataSource.class);
        factory.addConstructorArgValue(parseDataSources(element));
        factory.addConstructorArgValue(parseId(element));
        factory.addConstructorArgValue(parseMasterDataSourceRef(element));
        factory.addConstructorArgValue(parseSlaveDataSourcesRef(element));
        String strategyRef = parseStrategyRef(element);
        if (!Strings.isNullOrEmpty(strategyRef)) {
            factory.addConstructorArgReference(strategyRef);
        } else {
            factory.addConstructorArgValue(parseStrategyType(element));
        }
        factory.addConstructorArgValue(parseConfigMap(element, parserContext, factory.getBeanDefinition()));
        factory.addConstructorArgValue(parseProperties(element, parserContext));
        return factory.getBeanDefinition();
    }

    private Map<String, RuntimeBeanReference> parseDataSources(final Element element) {
        List<String> slaveDataSources = Splitter.on(",").trimResults().splitToList(element.getAttribute(MasterSlaveDataSourceBeanDefinitionParserTag.SLAVE_DATA_SOURCE_NAMES_ATTRIBUTE));
        Map<String, RuntimeBeanReference> result = new ManagedMap<>(slaveDataSources.size());
        for (String each : slaveDataSources) {
            result.put(each, new RuntimeBeanReference(each));
        }
        String masterDataSourceName = parseMasterDataSourceRef(element);
        result.put(masterDataSourceName, new RuntimeBeanReference(masterDataSourceName));
        return result;
    }

    private String parseId(final Element element) {
        return element.getAttribute(ID_ATTRIBUTE);
    }

    private String parseMasterDataSourceRef(final Element element) {
        return element.getAttribute(MasterSlaveDataSourceBeanDefinitionParserTag.MASTER_DATA_SOURCE_NAME_ATTRIBUTE);
    }

    private Collection<String> parseSlaveDataSourcesRef(final Element element) {
        List<String> slaveDataSources = Splitter.on(",").trimResults().splitToList(element.getAttribute(MasterSlaveDataSourceBeanDefinitionParserTag.SLAVE_DATA_SOURCE_NAMES_ATTRIBUTE));
        Collection<String> result = new ManagedList<>(slaveDataSources.size());
        result.addAll(slaveDataSources);
        return result;
    }

    private String parseStrategyRef(final Element element) {
        return element.getAttribute(MasterSlaveDataSourceBeanDefinitionParserTag.STRATEGY_REF_ATTRIBUTE);
    }

    private MasterSlaveLoadBalanceAlgorithmType parseStrategyType(final Element element) {
        String result = element.getAttribute(MasterSlaveDataSourceBeanDefinitionParserTag.STRATEGY_TYPE_ATTRIBUTE);
        return Strings.isNullOrEmpty(result) ? MasterSlaveLoadBalanceAlgorithmType.getDefaultAlgorithmType() : MasterSlaveLoadBalanceAlgorithmType
                .valueOf(result);
    }

    private Map parseConfigMap(final Element element, final ParserContext parserContext, final BeanDefinition beanDefinition) {
        Element dataElement = DomUtils.getChildElementByTagName(element, ShardingDataSourceBeanDefinitionParserTag.CONFIG_MAP_TAG);
        return null == dataElement ? Collections.<String, Class<?>>emptyMap() : parserContext.getDelegate().parseMapElement(dataElement, beanDefinition);
    }

    private Properties parseProperties(final Element element, final ParserContext parserContext) {
        Element propsElement = DomUtils.getChildElementByTagName(element, ShardingDataSourceBeanDefinitionParserTag.PROPS_TAG);
        return null == propsElement ? new Properties() : parserContext.getDelegate().parsePropsElement(propsElement);
    }
}
