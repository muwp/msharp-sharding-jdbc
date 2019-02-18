package com.muwp.sharding.jdbc.single;

import com.muwp.sharding.jdbc.bean.SqlBean;
import com.muwp.sharding.jdbc.manager.SqlParserManager;
import com.muwp.sharding.jdbc.rowmapper.DefaultRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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
    public <T> int insert(final T bean) {
        final SqlBean srb = SqlParserManager.generateInsertSql(bean);
        if (logger.isDebugEnabled()) {
            log.debug("Insert, the bean: {} ---> the SQL: {} ---> the params: {}", bean, srb.getSql(), srb.getParams());
        }
        return this.update(srb.getSql(), srb.getParams());
    }

    @Override
    public <T> int update(T bean) {
        final SqlBean srb = SqlParserManager.generateUpdateSql(bean);
        if (logger.isDebugEnabled()) {
            log.debug("Update, the bean: {} ---> the SQL: {} ---> the params: {}", bean, srb.getSql(), srb.getParams());
        }
        return this.update(srb.getSql(), srb.getParams());
    }

    @Override
    public <T> int delete(long id, Class<T> clazz) {
        final SqlBean srb = SqlParserManager.generateDeleteSql(id, clazz);
        if (logger.isDebugEnabled()) {
            log.debug("Delete, the bean: {} ---> the SQL: {} ---> the params: {}", id, srb.getSql(), srb.getParams());
        }
        return this.update(srb.getSql(), srb.getParams());
    }

    @Override
    public <T> T load(long id, final Class<T> clazz) {
        final SqlBean srb = SqlParserManager.generateSelectSql("id", id, clazz);
        final T bean = this.queryForObject(srb.getSql(), srb.getParams(), new DefaultRowMapper<>(clazz));
        return bean;
    }

    @Override
    public <T> T get(String name, Object value, final Class<T> clazz) {
        final SqlBean srb = SqlParserManager.generateSelectSql(name, value, clazz);
        T bean = this.queryForObject(srb.getSql(), srb.getParams(), new DefaultRowMapper<>(clazz));
        return bean;
    }

    @Override
    public <T> List<T> query(final T request) {
        final SqlBean srb = SqlParserManager.generateSearchSql(request);
        final Class<T> clazz = (Class<T>) request.getClass();
        final List<T> beans = this.query(srb.getSql(), srb.getParams(), new DefaultRowMapper<>(clazz));
        return beans;
    }

    @Override
    public <T> List<T> query(final T request, final int offset, final int pageSize) {
        final SqlBean srb = SqlParserManager.generateSearchSql(request, offset, pageSize);
        final Class<T> clazz = (Class<T>) request.getClass();
        final List<T> beans = this.query(srb.getSql(), srb.getParams(), new DefaultRowMapper<>(clazz));
        return beans;
    }

    @Override
    public <Request, Result> List<Result> query(Request request, RowMapper<Result> rowMapper) {
        final SqlBean srb = SqlParserManager.generateSearchSql(request);
        final List<Result> results = this.query(srb.getSql(), srb.getParams(), rowMapper);
        return results;
    }

    @Override
    public <Request, Result> List<Result> query(Request request, RowMapper<Result> rowMapper, final int offset, final int pageSize) {
        final SqlBean srb = SqlParserManager.generateSearchSql(request, offset, pageSize);
        final List<Result> results = this.query(srb.getSql(), srb.getParams(), rowMapper);
        return results;
    }

    @Override
    public <Result> List<Result> query(String sql, Object[] params, final Class<Result> clazz) {
        final List<Result> beans = this.query(sql, params, new DefaultRowMapper<>(clazz));
        return beans;
    }

    @Override
    public <Result> List<Result> query(String sql, Object[] params, final RowMapper<Result> rowMapper) {
        final List<Result> beans = this.query(sql, params, rowMapper);
        return beans;
    }
}