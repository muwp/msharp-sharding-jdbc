package com.msharp.single.jdbc.jtemplate.rowmapper;

import com.msharp.single.jdbc.jtemplate.manager.OrmManager;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DefaultRowMapper
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class DefaultRowMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public DefaultRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        return OrmManager.convertRow2Bean(rs, clazz);
    }
}
