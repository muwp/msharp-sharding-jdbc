package com.msharp.single.jdbc.monitor.statement;

import com.msharp.single.jdbc.callback.JdbcOperationCallback;
import com.msharp.single.jdbc.monitor.util.MonitorUtils;
import com.msharp.single.jdbc.util.StringUtils;
import com.ruijing.fundamental.cat.Cat;
import com.ruijing.fundamental.cat.common.collections.New;
import com.ruijing.fundamental.cat.message.Message;
import com.ruijing.fundamental.cat.message.MessageProducer;
import com.ruijing.fundamental.cat.message.Transaction;
import com.ruijing.fundamental.cat.site.helper.Stringizers;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Monitor PreparedStatement
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class MonitorPreparedStatement extends MonitorStatement implements PreparedStatement {

    private final PreparedStatement prepareStatement;

    protected String sql;

    private Object[] params;

    private int maxParamIndex;

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private int notPlainBatchSize;

    public static MonitorPreparedStatement from(PreparedStatement prepareStatement, String sql, Connection connection) {
        return new MonitorPreparedStatement(prepareStatement, sql, connection);
    }

    private void logSqlLengthEvent(String sql) {
        int length = (sql == null ? 0 : sql.length());
        if (length <= 102400) {
            Cat.logEvent("SQL.Length", MonitorUtils.getSqlLengthName(length), "0", "");
        } else {
            Cat.logEvent("SQL.Length", MonitorUtils.getSqlLengthName(length), "long-sql warning", "");
        }
    }

    public MonitorPreparedStatement(PreparedStatement prepareStatement, String sql, Connection connection) {
        super(prepareStatement, connection);
        this.prepareStatement = prepareStatement;
        this.sql = sql;
        this.initParams();
    }

    private String buildSqlType(String sql) {
        try {
            char c = sql.trim().charAt(0);
            if (c != 's' && c != 'S') {
                if (c != 'u' && c != 'U') {
                    if (c != 'i' && c != 'I') {
                        if (c != 'd' && c != 'D') {
                            if (c == 'c' || c == 'C') {
                                return "Call";
                            } else {
                                return "Execute";
                            }
                        } else {
                            return "Delete";
                        }
                    } else {
                        return "Insert";
                    }
                } else {
                    return "Update";
                }
            } else {
                return "Select";
            }
        } catch (Exception ex) {
            return "Execute";
        }
    }

    private <T> T executeWithCat(JdbcOperationCallback<T> callback, String type) throws SQLException {
        final MessageProducer cat = Cat.getProducer();
        final String sqlType = this.buildSqlType(this.sql);
        final Transaction t = cat.newTransaction("SQL", sqlType);
        t.addData(this.sql);
        T result;
        try {
            cat.logEvent("SQL.Database", this.connection.getMetaData().getURL());
            final List<Object> params = this.getParamList();
            if (null != params) {
                cat.logEvent("SQL.Params", "Params", Message.SUCCESS, Stringizers.forJson().compact().from(params, 1000, 50));
            }
            result = callback.doAction(null);
            t.setSuccess();
        } catch (SQLException ex) {
            cat.logError(ex);
            t.setStatus(ex);
            throw ex;
        } finally {
            t.complete();
        }
        return result;

    }

    @Override
    public ResultSet executeQuery() throws SQLException {
        return this.executeWithCat(conn -> {
            MonitorPreparedStatement.this.checkClosed();
            ResultSet resultSet = MonitorPreparedStatement.this.prepareStatement.executeQuery();
            return resultSet;
        }, "Query");
    }

    @Override
    public int executeUpdate() throws SQLException {
        return this.executeWithCat(conn -> {
            MonitorPreparedStatement.this.checkClosed();
            int updatedCount = MonitorPreparedStatement.this.prepareStatement.executeUpdate();
            return updatedCount;
        }, "Update");
    }

    @Override
    public boolean execute() throws SQLException {
        return this.executeWithCat(conn -> {
            MonitorPreparedStatement.this.checkClosed();
            boolean hasResultSet = MonitorPreparedStatement.this.prepareStatement.execute();
            return hasResultSet;
        }, "Execute");
    }

    @Override
    public void clearBatch() throws SQLException {
        this.prepareStatement.clearBatch();
        this.notPlainBatchSize = 0;
    }

    @Override
    public void addBatch() throws SQLException {
        this.prepareStatement.addBatch();
        ++this.notPlainBatchSize;
    }

    @Override
    public int[] executeBatch() throws SQLException {
        return this.executeWithCat(conn -> {
            MonitorPreparedStatement.this.checkClosed();
            boolean batchHasNotplainStatements = MonitorPreparedStatement.this.notPlainBatchSize > 0;
            if (batchHasNotplainStatements) {
                int[] var3;
                try {
                    int[] batchResult = MonitorPreparedStatement.this.prepareStatement.executeBatch();
                    var3 = batchResult;
                } finally {
                    MonitorPreparedStatement.this.notPlainBatchSize = 0;
                }
                return var3;
            } else {
                return MonitorPreparedStatement.this.prepareStatement.executeBatch();
            }
        }, "Batch");
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.setParameter(parameterIndex, null);
        this.prepareStatement.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setByte(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setShort(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setLong(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setFloat(parameterIndex, x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setDouble(parameterIndex, x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        this.setParameter(parameterIndex, x.scale() > 4 ? x.setScale(4, 4) : x);
        this.prepareStatement.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setString(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        this.setParameter(parameterIndex, "byte[]");
        this.prepareStatement.setBytes(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        this.setParameter(parameterIndex, this.dateFormat.format(x));
        this.prepareStatement.setDate(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        this.setParameter(parameterIndex, this.timeFormat.format(x));
        this.prepareStatement.setTime(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        this.setParameter(parameterIndex, this.dateTimeFormat.format(x));
        this.prepareStatement.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        this.setParameter(parameterIndex, this.dateFormat.format(x));
        this.prepareStatement.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        this.setParameter(parameterIndex, this.timeFormat.format(x));
        this.prepareStatement.setTime(parameterIndex, x, cal);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        this.setParameter(parameterIndex, this.dateTimeFormat.format(x));
        this.prepareStatement.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.setParameter(parameterIndex, "[ascii-stream]");
        this.prepareStatement.setAsciiStream(parameterIndex, x, length);
    }

    /**
     * @deprecated
     */
    @Deprecated
    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.setParameter(parameterIndex, "[unicode-stream]");
        this.prepareStatement.setUnicodeStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.setParameter(parameterIndex, "[binary-stream]");
        this.prepareStatement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        this.setParameter(parameterIndex, "[object]");
        this.prepareStatement.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        this.setParameter(parameterIndex, "[object]");
        this.prepareStatement.setObject(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        this.setParameter(parameterIndex, "[char-stream]");
        this.prepareStatement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        this.setParameter(parameterIndex, "[ref]");
        this.prepareStatement.setRef(parameterIndex, x);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        this.setParameter(parameterIndex, "[blob]");
        this.prepareStatement.setBlob(parameterIndex, x);
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        this.setParameter(parameterIndex, "[clob]");
        this.prepareStatement.setClob(parameterIndex, x);
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        this.setParameter(parameterIndex, "[array]");
        this.prepareStatement.setArray(parameterIndex, x);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        this.setParameter(parameterIndex, (Object) null);
        this.prepareStatement.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.setParameter(parameterIndex, x);
        this.prepareStatement.setURL(parameterIndex, x);
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        this.setParameter(parameterIndex, "[rowid]");
        this.prepareStatement.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        this.setParameter(parameterIndex, value);
        this.prepareStatement.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        this.setParameter(parameterIndex, "[nchar-stream]");
        this.prepareStatement.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        this.setParameter(parameterIndex, "[nclob]");
        this.prepareStatement.setNClob(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.setParameter(parameterIndex, "[clob]");
        this.prepareStatement.setClob(parameterIndex, reader, length);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        this.setParameter(parameterIndex, "[blob]");
        this.prepareStatement.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.setParameter(parameterIndex, "[nclob]");
        this.prepareStatement.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        this.setParameter(parameterIndex, "[xml]");
        this.prepareStatement.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        this.setParameter(parameterIndex, "[object]");
        this.prepareStatement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.setParameter(parameterIndex, "[ascii-stream]");
        this.prepareStatement.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.setParameter(parameterIndex, "[binary-stream]");
        this.prepareStatement.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        this.setParameter(parameterIndex, "[char-stream]");
        this.prepareStatement.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        this.setParameter(parameterIndex, "[ascii-stream]");
        this.prepareStatement.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        this.setParameter(parameterIndex, "[binary-stream]");
        this.prepareStatement.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        this.setParameter(parameterIndex, "[char-stream]");
        this.prepareStatement.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        this.setParameter(parameterIndex, "[nchar-stream]");
        this.prepareStatement.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        this.setParameter(parameterIndex, "[clob]");
        this.prepareStatement.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        this.setParameter(parameterIndex, "[blob]");
        this.prepareStatement.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        this.setParameter(parameterIndex, "[nclob]");
        this.prepareStatement.setNClob(parameterIndex, reader);
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return this.prepareStatement.getParameterMetaData();
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.prepareStatement.getMetaData();
    }

    @Override
    public void clearParameters() throws SQLException {
        this.initParams();
        this.prepareStatement.clearParameters();
    }

    @Override
    public void close() throws SQLException {
        super.close();
        this.params = null;
        this.sql = null;
        this.maxParamIndex = 0;
    }

    private void setParameter(int parameterIndex, Object parameterValue) {
        try {
            int paramIndex = parameterIndex - 1;
            int paramLen = this.params.length;
            if (paramIndex > paramLen - 1) {
                int newParamLen = (int) ((double) paramLen * 1.5D);
                final Object[] newParams = new Object[newParamLen > parameterIndex ? newParamLen : parameterIndex];
                System.arraycopy(this.params, 0, newParams, 0, paramLen);
                this.params = newParams;
            }

            this.params[paramIndex] = parameterValue;
            if (parameterIndex > this.maxParamIndex) {
                this.maxParamIndex = parameterIndex;
            }
        } catch (Throwable ex) {
            //quite
        }

    }

    private void initParams() {
        if (StringUtils.isBlank(this.sql)) {
            return;
        }
        final String lowerSql = this.sql.toLowerCase().trim();
        this.params = null;
        if (lowerSql.startsWith("insert")) {
            this.params = new Object[25];
        } else {
            this.params = new Object[10];
        }
    }

    private List<Object> getParamList() {
        if (this.maxParamIndex <= 0) {
            return null;
        } else {
            final List<Object> paramList = New.listWithCapacity(maxParamIndex);
            for (int i = 0; i < this.maxParamIndex; ++i) {
                paramList.add(this.params[i]);
            }
            return paramList;
        }
    }
}