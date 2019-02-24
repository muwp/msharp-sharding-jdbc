package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of IntParamContext
 *
 * @author Leo Liang
 */
public class IntParamContext extends ParamContext {

	private static final long serialVersionUID = -4094567388337032659L;

	/**
	 * @param index
	 * @param values
	 */
	public IntParamContext(int index, Object[] values) {
		super(index, values);
	}

	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		stmt.setInt(index, (Integer) values[0]);
	}

}
