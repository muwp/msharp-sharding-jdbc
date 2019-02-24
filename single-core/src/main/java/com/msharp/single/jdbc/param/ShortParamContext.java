package com.msharp.single.jdbc.param;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of ShortParamContext
 *
 * @author Leo Liang
 */
public class ShortParamContext extends ParamContext {

	private static final long serialVersionUID = 99912191693491650L;

	/**
	 * @param index
	 * @param values
	 */
	public ShortParamContext(int index, Object[] values) {
		super(index, values);
	}

	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		stmt.setShort(index, (Short) values[0]);
	}

}
