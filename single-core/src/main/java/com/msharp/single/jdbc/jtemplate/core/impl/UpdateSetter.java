package com.msharp.single.jdbc.jtemplate.core.impl;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * UpdateSetter
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class UpdateSetter implements PreparedStatementSetter {

    private Object[] params;

    public UpdateSetter(final Object[] params) {
        this.params = params;
    }

    @Override
    public void setValues(PreparedStatement ps) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }
}
