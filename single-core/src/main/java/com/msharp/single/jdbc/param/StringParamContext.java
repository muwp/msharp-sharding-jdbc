package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of StringParamContext
 *
 * @author Leo Liang
 */
public class StringParamContext extends ParamContext {

	private static final long serialVersionUID = -4522806074499008890L;

	/**
	 * @param index
	 * @param values
	 */
	public StringParamContext(int index, Object[] values) {
		super(index, values);
	}

	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		stmt.setString(index, (String) values[0]);
	}
}
