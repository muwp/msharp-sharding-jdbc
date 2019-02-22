package com.msharp.sharding.jdbc.core.manager;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ShardJdbcTemplateManager
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class ShardJdbcTemplateManager {

    /**
     * 主表
     */
    private JdbcTemplate master;

    /**
     * 从表
     */
    private List<JdbcTemplate> slaves;

    private AtomicLong iter = new AtomicLong(0);

    public JdbcTemplate getMaster() {
        return master;
    }

    public void setMaster(JdbcTemplate master) {
        this.master = master;
    }

    public List<JdbcTemplate> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<JdbcTemplate> slaves) {
        this.slaves = slaves;
    }

    public void addSalveTemplate(JdbcTemplate jdbcTemplate) {
        this.slaves.add(jdbcTemplate);
    }

    public void removeSalveTemplate(JdbcTemplate jdbcTemplate) {
        this.slaves.remove(jdbcTemplate);
    }

    public JdbcTemplate getRoundRobinSlaveTemplate() {
        long iterValue = iter.incrementAndGet();
        // Still race condition, but it doesn't matter
        if (iterValue == Long.MAX_VALUE || iterValue < 0) {
            iter.set(0);
        }

        return slaves.get((int) iterValue % slaves.size());
    }
}
