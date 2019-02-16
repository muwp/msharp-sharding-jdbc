package com.muwp.sharding.jdbc.single;

import com.muwp.sharding.jdbc.bean.SqlBean;
import com.muwp.sharding.jdbc.rowmapper.DefaultRowMapper;
import com.muwp.sharding.jdbc.util.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * SimpleJdbcTemplate
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class SimpleJdbcTemplate extends JdbcTemplate implements SimpleJdbcOperations {

    private static final Logger log = LoggerFactory.getLogger(SimpleJdbcTemplate.class);

    public SimpleJdbcTemplate() {
    }

    public SimpleJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public SimpleJdbcTemplate(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }

    @Override
    public <T> void insert(final T bean) {
        SqlBean srb = SqlUtil.generateInsertSql(bean);
        log.debug("Insert, the bean: {} ---> the SQL: {} ---> the params: {}", bean, srb.getSql(), srb.getParams());
        this.update(srb.getSql(), srb.getParams());
    }

    @Override
    public <T> void update(T bean) {
        SqlBean srb = SqlUtil.generateUpdateSql(bean);
        log.debug("Update, the bean: {} ---> the SQL: {} ---> the params: {}", bean, srb.getSql(), srb.getParams());
        this.update(srb.getSql(), srb.getParams());
    }

    @Override
    public <T> void delete(long id, Class<T> clazz) {
        SqlBean srb = SqlUtil.generateDeleteSql(id, clazz);
        log.debug("Delete, the bean: {} ---> the SQL: {} ---> the params: {}", id, srb.getSql(), srb.getParams());
        this.update(srb.getSql(), srb.getParams());
    }

    @Override
    public <T> T get(long id, final Class<T> clazz) {
        final SqlBean srb = SqlUtil.generateSelectSql("id", id, clazz);
        final T bean = this.queryForObject(srb.getSql(), srb.getParams(), new DefaultRowMapper<>(clazz));
        return bean;
    }

    @Override
    public <T> T get(String name, Object value, final Class<T> clazz) {
        SqlBean srb = SqlUtil.generateSelectSql(name, value, clazz);
        T bean = this.queryForObject(srb.getSql(), srb.getParams(), new DefaultRowMapper<>(clazz));
        return bean;
    }

    @Override
    public <T> List<T> query(final T bean) {
        SqlBean srb = SqlUtil.generateSearchSql(bean);
        Class<T> clazz = (Class<T>) bean.getClass();
        List<T> beans = this.query(srb.getSql(), srb.getParams(), new DefaultRowMapper<>(clazz));
        return beans;
    }

    @Override
    public <T> List<T> query(String sql, Object[] params, final Class<T> clazz) {
        final List<T> beans = this.query(sql, params, new DefaultRowMapper<>(clazz));
        return beans;
    }
}