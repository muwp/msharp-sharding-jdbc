package com.msharp.single.jdbc.monitor.statement;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 * Monitor CallableStatement
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
public class MonitorCallableStatement extends MonitorPreparedStatement implements CallableStatement {

    private final CallableStatement prepareCall;

    public MonitorCallableStatement(CallableStatement prepareCall, String sql, Connection connection) {
        super(prepareCall, sql, connection);
        this.prepareCall = prepareCall;
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        this.prepareCall.registerOutParameter(parameterIndex, sqlType);
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        this.prepareCall.registerOutParameter(parameterIndex, sqlType, scale);
    }

    @Override
    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        this.prepareCall.registerOutParameter(parameterIndex, sqlType, typeName);
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        this.prepareCall.registerOutParameter(parameterName, sqlType);
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        this.prepareCall.registerOutParameter(parameterName, sqlType, scale);
    }

    @Override
    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        this.prepareCall.registerOutParameter(parameterName, sqlType, typeName);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return this.prepareCall.wasNull();
    }

    @Override
    public String getString(int parameterIndex) throws SQLException {
        return this.prepareCall.getString(parameterIndex);
    }

    @Override
    public boolean getBoolean(int parameterIndex) throws SQLException {
        return this.prepareCall.getBoolean(parameterIndex);
    }

    @Override
    public byte getByte(int parameterIndex) throws SQLException {
        return this.prepareCall.getByte(parameterIndex);
    }

    @Override
    public short getShort(int parameterIndex) throws SQLException {
        return this.prepareCall.getShort(parameterIndex);
    }

    @Override

    public int getInt(int parameterIndex) throws SQLException {
        return this.prepareCall.getInt(parameterIndex);
    }

    @Override
    public long getLong(int parameterIndex) throws SQLException {
        return this.prepareCall.getLong(parameterIndex);
    }

    @Override
    public float getFloat(int parameterIndex) throws SQLException {
        return this.prepareCall.getFloat(parameterIndex);
    }

    @Override
    public double getDouble(int parameterIndex) throws SQLException {
        return this.prepareCall.getDouble(parameterIndex);
    }

    @Override
    /** @deprecated */
    @Deprecated
    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return this.prepareCall.getBigDecimal(parameterIndex, scale);
    }

    @Override
    public byte[] getBytes(int parameterIndex) throws SQLException {
        return this.prepareCall.getBytes(parameterIndex);
    }

    @Override
    public Date getDate(int parameterIndex) throws SQLException {
        return this.prepareCall.getDate(parameterIndex);
    }

    @Override

    public Time getTime(int parameterIndex) throws SQLException {
        return this.prepareCall.getTime(parameterIndex);
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return this.prepareCall.getTimestamp(parameterIndex);
    }

    @Override
    public Object getObject(int parameterIndex) throws SQLException {
        return this.prepareCall.getObject(parameterIndex);
    }

    @Override
    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return this.prepareCall.getBigDecimal(parameterIndex);
    }

    @Override

    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        return this.prepareCall.getObject(parameterIndex, map);
    }

    @Override
    public Ref getRef(int parameterIndex) throws SQLException {
        return this.prepareCall.getRef(parameterIndex);
    }

    @Override
    public Blob getBlob(int parameterIndex) throws SQLException {
        return this.prepareCall.getBlob(parameterIndex);
    }

    @Override
    public Clob getClob(int parameterIndex) throws SQLException {
        return this.prepareCall.getClob(parameterIndex);
    }

    @Override
    public Array getArray(int parameterIndex) throws SQLException {
        return this.prepareCall.getArray(parameterIndex);
    }

    @Override
    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        return this.prepareCall.getDate(parameterIndex, cal);
    }

    @Override
    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        return this.prepareCall.getTime(parameterIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        return this.prepareCall.getTimestamp(parameterIndex, cal);
    }

    @Override
    public URL getURL(int parameterIndex) throws SQLException {
        return this.prepareCall.getURL(parameterIndex);
    }

    @Override
    public void setURL(String parameterName, URL val) throws SQLException {
        this.prepareCall.setURL(parameterName, val);
    }

    @Override
    public void setNull(String parameterName, int sqlType) throws SQLException {
        this.prepareCall.setNull(parameterName, sqlType);
    }

    @Override
    public void setBoolean(String parameterName, boolean x) throws SQLException {
        this.prepareCall.setBoolean(parameterName, x);
    }

    @Override
    public void setByte(String parameterName, byte x) throws SQLException {
        this.prepareCall.setByte(parameterName, x);
    }

    @Override
    public void setShort(String parameterName, short x) throws SQLException {
        this.prepareCall.setShort(parameterName, x);
    }

    @Override
    public void setInt(String parameterName, int x) throws SQLException {
        this.prepareCall.setInt(parameterName, x);
    }

    @Override
    public void setLong(String parameterName, long x) throws SQLException {
        this.prepareCall.setLong(parameterName, x);
    }

    @Override
    public void setFloat(String parameterName, float x) throws SQLException {
        this.prepareCall.setFloat(parameterName, x);
    }

    @Override

    public void setDouble(String parameterName, double x) throws SQLException {
        this.prepareCall.setDouble(parameterName, x);
    }

    @Override
    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        this.prepareCall.setBigDecimal(parameterName, x);
    }

    @Override
    public void setString(String parameterName, String x) throws SQLException {
        this.prepareCall.setString(parameterName, x);
    }

    @Override
    public void setBytes(String parameterName, byte[] x) throws SQLException {
        this.prepareCall.setBytes(parameterName, x);
    }

    @Override
    public void setDate(String parameterName, Date x) throws SQLException {
        this.prepareCall.setDate(parameterName, x);
    }

    @Override
    public void setTime(String parameterName, Time x) throws SQLException {
        this.prepareCall.setTime(parameterName, x);
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        this.prepareCall.setTimestamp(parameterName, x);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        this.prepareCall.setAsciiStream(parameterName, x, length);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        this.prepareCall.setBinaryStream(parameterName, x, length);
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        this.prepareCall.setObject(parameterName, x, targetSqlType, scale);
    }

    @Override
    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        this.prepareCall.setObject(parameterName, x, targetSqlType);
    }

    @Override
    public void setObject(String parameterName, Object x) throws SQLException {
        this.prepareCall.setObject(parameterName, x);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        this.prepareCall.setCharacterStream(parameterName, reader, length);
    }

    @Override
    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        this.prepareCall.setDate(parameterName, x, cal);
    }

    @Override
    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        this.prepareCall.setTime(parameterName, x, cal);
    }

    @Override
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        this.prepareCall.setTimestamp(parameterName, x, cal);
    }

    @Override
    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        this.prepareCall.setNull(parameterName, sqlType, typeName);
    }

    @Override
    public String getString(String parameterName) throws SQLException {
        return this.prepareCall.getString(parameterName);
    }

    @Override
    public boolean getBoolean(String parameterName) throws SQLException {
        return this.prepareCall.getBoolean(parameterName);
    }

    @Override
    public byte getByte(String parameterName) throws SQLException {
        return this.prepareCall.getByte(parameterName);
    }

    @Override
    public short getShort(String parameterName) throws SQLException {
        return this.prepareCall.getShort(parameterName);
    }

    @Override
    public int getInt(String parameterName) throws SQLException {
        return this.prepareCall.getInt(parameterName);
    }

    @Override
    public long getLong(String parameterName) throws SQLException {
        return this.prepareCall.getLong(parameterName);
    }

    @Override
    public float getFloat(String parameterName) throws SQLException {
        return this.prepareCall.getFloat(parameterName);
    }

    @Override
    public double getDouble(String parameterName) throws SQLException {
        return this.prepareCall.getDouble(parameterName);
    }

    @Override
    public byte[] getBytes(String parameterName) throws SQLException {
        return this.prepareCall.getBytes(parameterName);
    }

    @Override
    public Date getDate(String parameterName) throws SQLException {
        return this.prepareCall.getDate(parameterName);
    }

    @Override
    public Time getTime(String parameterName) throws SQLException {
        return this.prepareCall.getTime(parameterName);
    }

    @Override
    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return this.prepareCall.getTimestamp(parameterName);
    }

    @Override
    public Object getObject(String parameterName) throws SQLException {
        return this.prepareCall.getObject(parameterName);
    }

    @Override
    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return this.prepareCall.getBigDecimal(parameterName);
    }

    @Override
    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return this.prepareCall.getObject(parameterName, map);
    }

    @Override
    public Ref getRef(String parameterName) throws SQLException {
        return this.prepareCall.getRef(parameterName);
    }

    @Override
    public Blob getBlob(String parameterName) throws SQLException {
        return this.prepareCall.getBlob(parameterName);
    }

    @Override
    public Clob getClob(String parameterName) throws SQLException {
        return this.prepareCall.getClob(parameterName);
    }

    @Override
    public Array getArray(String parameterName) throws SQLException {
        return this.prepareCall.getArray(parameterName);
    }

    @Override
    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return this.prepareCall.getDate(parameterName, cal);
    }

    @Override
    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return this.prepareCall.getTime(parameterName, cal);
    }

    @Override
    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return this.prepareCall.getTimestamp(parameterName, cal);
    }

    @Override
    public URL getURL(String parameterName) throws SQLException {
        return this.prepareCall.getURL(parameterName);
    }

    @Override
    public RowId getRowId(int parameterIndex) throws SQLException {
        return this.prepareCall.getRowId(parameterIndex);
    }

    @Override
    public RowId getRowId(String parameterName) throws SQLException {
        return this.prepareCall.getRowId(parameterName);
    }

    @Override
    public void setRowId(String parameterName, RowId x) throws SQLException {
        this.prepareCall.setRowId(parameterName, x);
    }

    @Override
    public void setNString(String parameterName, String value) throws SQLException {
        this.prepareCall.setNString(parameterName, value);
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        this.prepareCall.setNCharacterStream(parameterName, value, length);
    }

    @Override
    public void setNClob(String parameterName, NClob value) throws SQLException {
        this.prepareCall.setNClob(parameterName, value);
    }

    @Override

    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        this.prepareCall.setClob(parameterName, reader, length);
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        this.prepareCall.setBlob(parameterName, inputStream, length);
    }

    @Override
    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        this.prepareCall.setNClob(parameterName, reader, length);
    }

    @Override

    public NClob getNClob(int parameterIndex) throws SQLException {
        return this.prepareCall.getNClob(parameterIndex);
    }

    @Override
    public NClob getNClob(String parameterName) throws SQLException {
        return this.prepareCall.getNClob(parameterName);
    }

    @Override

    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        this.prepareCall.setSQLXML(parameterName, xmlObject);
    }

    @Override

    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return this.prepareCall.getSQLXML(parameterIndex);
    }

    @Override

    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return this.prepareCall.getSQLXML(parameterName);
    }

    @Override
    public String getNString(int parameterIndex) throws SQLException {
        return this.prepareCall.getNString(parameterIndex);
    }

    @Override

    public String getNString(String parameterName) throws SQLException {
        return this.prepareCall.getNString(parameterName);
    }

    @Override
    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return this.prepareCall.getNCharacterStream(parameterIndex);
    }

    @Override
    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return this.prepareCall.getNCharacterStream(parameterName);
    }

    @Override
    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return this.prepareCall.getCharacterStream(parameterIndex);
    }

    @Override
    public Reader getCharacterStream(String parameterName) throws SQLException {
        return this.prepareCall.getCharacterStream(parameterName);
    }

    @Override
    public void setBlob(String parameterName, Blob x) throws SQLException {
        this.prepareCall.setBlob(parameterName, x);
    }

    @Override
    public void setClob(String parameterName, Clob x) throws SQLException {
        this.prepareCall.setClob(parameterName, x);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        this.prepareCall.setAsciiStream(parameterName, x, length);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        this.prepareCall.setBinaryStream(parameterName, x, length);
    }

    @Override
    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        this.prepareCall.setCharacterStream(parameterName, reader, length);
    }

    @Override
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        this.prepareCall.setAsciiStream(parameterName, x);
    }

    @Override
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        this.prepareCall.setBinaryStream(parameterName, x);
    }

    @Override

    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        this.prepareCall.setCharacterStream(parameterName, reader);
    }

    @Override
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        this.prepareCall.setNCharacterStream(parameterName, value);
    }

    @Override
    public void setClob(String parameterName, Reader reader) throws SQLException {
        this.prepareCall.setClob(parameterName, reader);
    }

    @Override
    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        this.prepareCall.setBlob(parameterName, inputStream);
    }

    @Override
    public void setNClob(String parameterName, Reader reader) throws SQLException {
        this.prepareCall.setNClob(parameterName, reader);
    }

    @Override
    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("getObject");
    }

    @Override
    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        throw new UnsupportedOperationException("getObject");
    }
}