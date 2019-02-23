package com.msharp.single.jdbc.jtemplate.core.impl;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * BatchUpdateSetter
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public class BatchUpdateSetter implements BatchPreparedStatementSetter {

    private List<Object[]> paramsList;

    public BatchUpdateSetter(List<Object[]> paramsList) {
        this.paramsList = paramsList;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        Object[] params = paramsList.get(i);
        for (int j = 0; j < params.length; j++) {
            ps.setObject(j + 1, params[j]);
        }
    }

    @Override
    public int getBatchSize() {
        return paramsList.size();
    }
}
