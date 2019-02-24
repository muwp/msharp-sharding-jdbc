package com.msharp.single.jdbc.util;

import com.msharp.single.jdbc.exception.MSharpConfigException;

/**
 * JdbcDriverClassHelper
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class JdbcDriverClassHelper {

	public static String getDriverClassNameByJdbcUrl(String url) {
		if (url.startsWith("jdbc:mysql:")) {
			return "com.mysql.jdbc.Driver";
		} else if (url.startsWith("jdbc:postgresql:")) {
			return "org.postgresql.Driver";
		} else if (url.startsWith("jdbc:sqlserver:")) {
			return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		} else if (url.startsWith("jdbc:jtds:sqlserver:")) {
			return "net.sourceforge.jtds.jdbc.Driver";
		} else if (url.startsWith("jdbc:h2:")) {
			return "org.h2.Driver";
		} else if(url.startsWith("jdbc:kylin:")) {
			return "org.apache.kylin.jdbc.Driver";
		} else {
			return "";
		}
	}

	public static void loadDriverClass(String driverClass, String url) throws MSharpConfigException {
		try {
			Class.forName(StringUtils.isEmpty(driverClass) ? getDriverClassNameByJdbcUrl(url) : driverClass);
		} catch (ClassNotFoundException e) {
			throw new MSharpConfigException("Cannot find driver class : " + url, e);
		}
	}
}
