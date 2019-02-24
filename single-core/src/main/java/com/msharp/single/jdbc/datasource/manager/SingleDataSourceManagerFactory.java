package com.msharp.single.jdbc.datasource.manager;

/**
 * SingleDataSourceManagerFactory
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public final class SingleDataSourceManagerFactory {

	private volatile static SingleDataSourceManager dataSourceManager;

	private SingleDataSourceManagerFactory() {
	}

	public static SingleDataSourceManager getDataSourceManager() {
		if (dataSourceManager == null) {
			synchronized (SingleDataSourceManagerFactory.class) {
				if (dataSourceManager == null) {
					dataSourceManager = new DefaultSingleDataSourceManager();
					dataSourceManager.init();
				}
			}
		}

		return dataSourceManager;
	}
}
