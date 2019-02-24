package com.msharp.single.jdbc.datasource.jdbc;

import com.msharp.single.jdbc.log.Logger;
import com.msharp.single.jdbc.log.LoggerFactory;

/**
 * C3p0SingleDataSource
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class C3p0SingleDataSource extends SingleDataSource {

    protected static final Logger LOGGER = LoggerFactory.getLogger(C3p0SingleDataSource.class);

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
