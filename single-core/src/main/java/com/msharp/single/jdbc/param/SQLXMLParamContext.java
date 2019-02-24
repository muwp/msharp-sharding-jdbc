package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLXML;

/**
 * TODO Comment of SQLXMLParamContext
 *
 * @author Leo Liang
 */
public class SQLXMLParamContext extends ParamContext {

	private static final long serialVersionUID = -3225485567882941489L;

	/**
	 * @param index
	 * @param values
	 */
	public SQLXMLParamContext(int index, Object[] values) {
		super(index, values);
	}

	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		stmt.setSQLXML(index, (SQLXML) values[0]);
	}

}
