package com.msharp.sharding.jdbc.sphere.namespace.parser;


import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.msharp.sharding.jdbc.sphere.namespace.constants.ShardingDataSourceBeanDefinitionParserTag;
import com.msharp.sharding.jdbc.sphere.spring.SpringShardingDataSource;
import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingsphere.api.config.rule.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.shardingjdbc.spring.namespace.constants.MasterSlaveDataSourceBeanDefinitionParserTag;
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
 * ShardingDataSourceBeanDefinitionParser
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public final class ShardingDataSourceBeanDefinitionParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(final Element element, final ParserContext parserContext) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SpringShardingDataSource.class);
        factory.addConstructorArgValue(parseDataSources(element));
        factory.addConstructorArgValue(parseShardingRuleConfig(element));
        factory.addConstructorArgValue(parseConfigMap(element, parserContext, factory.getBeanDefinition()));
        factory.addConstructorArgValue(parseProperties(element, parserContext));
        factory.setDestroyMethodName("close");
        return factory.getBeanDefinition();
    }

    private Map<String, RuntimeBeanReference> parseDataSources(final Element element) {
        Element shardingRuleElement = DomUtils.getChildElementByTagName(element, ShardingDataSourceBeanDefinitionParserTag.SHARDING_RULE_CONFIG_TAG);
        List<String> dataSources = Splitter.on(",").trimResults().splitToList(shardingRuleElement.getAttribute(ShardingDataSourceBeanDefinitionParserTag.DATA_SOURCE_NAMES_TAG));
        Map<String, RuntimeBeanReference> result = new ManagedMap<>(dataSources.size());
        for (String each : dataSources) {
            result.put(each, new RuntimeBeanReference(each));
        }
        return result;
    }

    private BeanDefinition parseShardingRuleConfig(final Element element) {
        Element shardingRuleElement = DomUtils.getChildElementByTagName(element, ShardingDataSourceBeanDefinitionParserTag.SHARDING_RULE_CONFIG_TAG);
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ShardingRuleConfiguration.class);
        parseDefaultDataSource(factory, shardingRuleElement);
        parseDefaultDatabaseShardingStrategy(factory, shardingRuleElement);
        parseDefaultTableShardingStrategy(factory, shardingRuleElement);
        factory.addPropertyValue("tableRuleConfigs", parseTableRulesConfig(shardingRuleElement));
        factory.addPropertyValue("masterSlaveRuleConfigs", parseMasterSlaveRulesConfig(shardingRuleElement));
        factory.addPropertyValue("bindingTableGroups", parseBindingTablesConfig(shardingRuleElement));
        factory.addPropertyValue("broadcastTables", parseBroadcastTables(shardingRuleElement));
        parseKeyGenerator(factory, shardingRuleElement);
        return factory.getBeanDefinition();
    }

    private void parseKeyGenerator(final BeanDefinitionBuilder factory, final Element element) {
        String defaultKeyGenerator = element.getAttribute(ShardingDataSourceBeanDefinitionParserTag.DEFAULT_KEY_GENERATOR_REF_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(defaultKeyGenerator)) {
            factory.addPropertyReference("defaultKeyGenerator", defaultKeyGenerator);
        }
    }

    private void parseDefaultDataSource(final BeanDefinitionBuilder factory, final Element element) {
        String defaultDataSource = element.getAttribute(ShardingDataSourceBeanDefinitionParserTag.DEFAULT_DATA_SOURCE_NAME_TAG);
        if (!Strings.isNullOrEmpty(defaultDataSource)) {
            factory.addPropertyValue("defaultDataSourceName", defaultDataSource);
        }
    }

    private void parseDefaultDatabaseShardingStrategy(final BeanDefinitionBuilder factory, final Element element) {
        String defaultDatabaseShardingStrategy = element.getAttribute(ShardingDataSourceBeanDefinitionParserTag.DEFAULT_DATABASE_STRATEGY_REF_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(defaultDatabaseShardingStrategy)) {
            factory.addPropertyReference("defaultDatabaseShardingStrategyConfig", defaultDatabaseShardingStrategy);
        }
    }

    private void parseDefaultTableShardingStrategy(final BeanDefinitionBuilder factory, final Element element) {
        String defaultTableShardingStrategy = element.getAttribute(ShardingDataSourceBeanDefinitionParserTag.DEFAULT_TABLE_STRATEGY_REF_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(defaultTableShardingStrategy)) {
            factory.addPropertyReference("defaultTableShardingStrategyConfig", defaultTableShardingStrategy);
        }
    }

    private List<BeanDefinition> parseMasterSlaveRulesConfig(final Element element) {
        Element masterSlaveRulesElement = DomUtils.getChildElementByTagName(element, ShardingDataSourceBeanDefinitionParserTag.MASTER_SLAVE_RULES_TAG);
        if (null == masterSlaveRulesElement) {
            return new LinkedList<>();
        }
        List<Element> masterSlaveRuleElements = DomUtils.getChildElementsByTagName(masterSlaveRulesElement, ShardingDataSourceBeanDefinitionParserTag.MASTER_SLAVE_RULE_TAG);
        List<BeanDefinition> result = new ManagedList<>(masterSlaveRuleElements.size());
        for (Element each : masterSlaveRuleElements) {
            result.add(parseMasterSlaveRuleConfig(each));
        }
        return result;
    }

    private BeanDefinition parseMasterSlaveRuleConfig(final Element masterSlaveElement) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(MasterSlaveRuleConfiguration.class);
        factory.addPropertyValue("name", masterSlaveElement.getAttribute(ID_ATTRIBUTE));
        factory.addPropertyValue("masterDataSourceName", masterSlaveElement.getAttribute(
                MasterSlaveDataSourceBeanDefinitionParserTag.MASTER_DATA_SOURCE_NAME_ATTRIBUTE));
        factory.addPropertyValue("slaveDataSourceNames", parseSlaveDataSourcesRef(masterSlaveElement));
        String strategyRef = masterSlaveElement.getAttribute(MasterSlaveDataSourceBeanDefinitionParserTag.STRATEGY_REF_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(strategyRef)) {
            factory.addPropertyReference("loadBalanceAlgorithm", strategyRef);
        } else {
            factory.addPropertyValue("loadBalanceAlgorithm", parseStrategyType(masterSlaveElement).getAlgorithm());
        }
        return factory.getBeanDefinition();
    }

    private MasterSlaveLoadBalanceAlgorithmType parseStrategyType(final Element element) {
        String result = element.getAttribute(MasterSlaveDataSourceBeanDefinitionParserTag.STRATEGY_TYPE_ATTRIBUTE);
        return Strings.isNullOrEmpty(result) ? MasterSlaveLoadBalanceAlgorithmType.getDefaultAlgorithmType() : MasterSlaveLoadBalanceAlgorithmType
                .valueOf(result);
    }

    private Collection<String> parseSlaveDataSourcesRef(final Element element) {
        List<String> slaveDataSources = Splitter.on(",").trimResults().splitToList(element.getAttribute(
                MasterSlaveDataSourceBeanDefinitionParserTag.SLAVE_DATA_SOURCE_NAMES_ATTRIBUTE));
        Collection<String> result = new ManagedList<>(slaveDataSources.size());
        result.addAll(slaveDataSources);
        return result;
    }

    private List<BeanDefinition> parseTableRulesConfig(final Element element) {
        Element tableRulesElement = DomUtils.getChildElementByTagName(element, ShardingDataSourceBeanDefinitionParserTag.TABLE_RULES_TAG);
        List<Element> tableRuleElements = DomUtils.getChildElementsByTagName(tableRulesElement, ShardingDataSourceBeanDefinitionParserTag.TABLE_RULE_TAG);
        List<BeanDefinition> result = new ManagedList<>(tableRuleElements.size());
        for (Element each : tableRuleElements) {
            result.add(parseTableRuleConfig(each));
        }
        return result;
    }

    private BeanDefinition parseTableRuleConfig(final Element tableElement) {
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(TableRuleConfiguration.class);
        factory.addPropertyValue("logicTable", tableElement.getAttribute(ShardingDataSourceBeanDefinitionParserTag.LOGIC_TABLE_ATTRIBUTE));
        String actualDataNodes = tableElement.getAttribute(ShardingDataSourceBeanDefinitionParserTag.ACTUAL_DATA_NODES_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(actualDataNodes)) {
            factory.addPropertyValue("actualDataNodes", actualDataNodes);
        }
        String databaseStrategy = tableElement.getAttribute(ShardingDataSourceBeanDefinitionParserTag.DATABASE_STRATEGY_REF_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(databaseStrategy)) {
            factory.addPropertyReference("databaseShardingStrategyConfig", databaseStrategy);
        }
        String tableStrategy = tableElement.getAttribute(ShardingDataSourceBeanDefinitionParserTag.TABLE_STRATEGY_REF_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(tableStrategy)) {
            factory.addPropertyReference("tableShardingStrategyConfig", tableStrategy);
        }
        String keyGeneratorColumnName = tableElement.getAttribute(ShardingDataSourceBeanDefinitionParserTag.GENERATE_KEY_COLUMN_NAME_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(keyGeneratorColumnName)) {
            factory.addPropertyValue("keyGeneratorColumnName", keyGeneratorColumnName);
        }
        String keyGenerator = tableElement.getAttribute(ShardingDataSourceBeanDefinitionParserTag.KEY_GENERATOR_REF_ATTRIBUTE);
        if (!Strings.isNullOrEmpty(keyGenerator)) {
            factory.addPropertyReference("keyGenerator", keyGenerator);
        }
        String logicIndex = tableElement.getAttribute(ShardingDataSourceBeanDefinitionParserTag.LOGIC_INDEX);
        if (!Strings.isNullOrEmpty(logicIndex)) {
            factory.addPropertyValue("logicIndex", logicIndex);
        }
        return factory.getBeanDefinition();
    }

    private List<String> parseBindingTablesConfig(final Element element) {
        Element bindingTableRulesElement = DomUtils.getChildElementByTagName(element, ShardingDataSourceBeanDefinitionParserTag.BINDING_TABLE_RULES_TAG);
        if (null == bindingTableRulesElement) {
            return Collections.emptyList();
        }
        List<Element> bindingTableRuleElements = DomUtils.getChildElementsByTagName(bindingTableRulesElement, ShardingDataSourceBeanDefinitionParserTag.BINDING_TABLE_RULE_TAG);
        List<String> result = new LinkedList<>();
        for (Element each : bindingTableRuleElements) {
            result.add(each.getAttribute(ShardingDataSourceBeanDefinitionParserTag.LOGIC_TABLES_ATTRIBUTE));
        }
        return result;
    }

    private List<String> parseBroadcastTables(final Element element) {
        Element broadcastTableRulesElement = DomUtils.getChildElementByTagName(element, ShardingDataSourceBeanDefinitionParserTag.BROADCAST_TABLE_RULES_TAG);
        if (null == broadcastTableRulesElement) {
            return Collections.emptyList();
        }
        List<Element> broadcastTableRuleElements = DomUtils.getChildElementsByTagName(broadcastTableRulesElement, ShardingDataSourceBeanDefinitionParserTag.BROADCAST_TABLE_RULE_TAG);
        List<String> result = new LinkedList<>();
        for (Element each : broadcastTableRuleElements) {
            result.add(each.getAttribute(ShardingDataSourceBeanDefinitionParserTag.TABLE_ATTRIBUTE));
        }
        return result;
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