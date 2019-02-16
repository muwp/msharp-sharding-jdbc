package com.muwp.sharding.jdbc.core;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * SplitNode
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SplitNode {

    /**
     * 主表
     */
    private JdbcTemplate masterTemplate;

    /**
     * 从表
     */
    private List<JdbcTemplate> slaveTemplates;

    private AtomicLong iter = new AtomicLong(0);

    public SplitNode() {
    }

    public SplitNode(final JdbcTemplate masterTemplate, final List<JdbcTemplate> slaveTemplates) {
        this.masterTemplate = masterTemplate;
        this.slaveTemplates = slaveTemplates;
    }

    public SplitNode(final JdbcTemplate masterTemplate, final JdbcTemplate... slaveTemplates) {
        this.masterTemplate = masterTemplate;
        this.slaveTemplates = Arrays.asList(slaveTemplates);
    }

    public JdbcTemplate getMasterTemplate() {
        return masterTemplate;
    }

    public void setMasterTemplate(JdbcTemplate masterTemplate) {
        this.masterTemplate = masterTemplate;
    }

    public List<JdbcTemplate> getSlaveTemplates() {
        return slaveTemplates;
    }

    public void setSlaveTemplates(List<JdbcTemplate> slaveTemplates) {
        this.slaveTemplates = slaveTemplates;
    }

    public void addSalveTemplate(JdbcTemplate jdbcTemplate) {
        this.slaveTemplates.add(jdbcTemplate);
    }

    public void removeSalveTemplate(JdbcTemplate jdbcTemplate) {
        this.slaveTemplates.remove(jdbcTemplate);
    }

    public JdbcTemplate getRoundRobinSlaveTemplate() {
        long iterValue = iter.incrementAndGet();
        // Still race condition, but it doesn't matter
        if (iterValue == Long.MAX_VALUE) {
            iter.set(0);
        }

        return slaveTemplates.get((int) iterValue % slaveTemplates.size());
    }
}
