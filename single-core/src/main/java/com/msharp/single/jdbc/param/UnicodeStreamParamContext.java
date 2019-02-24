package com.msharp.single.jdbc.param;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of UnicodeStreamParamContext
 *
 * @author Leo Liang
 */
public class UnicodeStreamParamContext extends ParamContext {

	private static final long serialVersionUID = 6354070943960944242L;

	/**
	 * @param index
	 * @param values
	 */
	public UnicodeStreamParamContext(int index, Object[] values) {
		super(index, values);
	}

	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		stmt.setUnicodeStream(index, (InputStream) values[0], (Integer) values[1]);
	}

}
