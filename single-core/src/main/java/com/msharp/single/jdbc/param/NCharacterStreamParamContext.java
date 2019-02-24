package com.msharp.single.jdbc.param;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO Comment of NCharacterStreamParamContext
 *
 * @author Leo Liang
 */
public class NCharacterStreamParamContext extends ParamContext {

	private static final long serialVersionUID = -6956092326424615289L;

	/**
	 * @param index
	 * @param values
	 */
	public NCharacterStreamParamContext(int index, Object[] values) {
		super(index, values);
	}

	@Override
	public void setParam(PreparedStatement stmt) throws SQLException {
		if (values.length == 1) {
			stmt.setNCharacterStream(index, (Reader) values[0]);
		} else if (values.length == 2) {
			stmt.setNCharacterStream(index, (Reader) values[0], (Long) values[1]);
		}
	}

}
