
package com.msharp.single.jdbc.callback;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * JdbcOperationCallback
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public interface JdbcOperationCallback<T> {

    /**
     * 根据connection处理结果
     *
     * @param conn 数据库连接
     * @return
     * @throws SQLException
     */
    T doAction(Connection conn) throws SQLException;
}