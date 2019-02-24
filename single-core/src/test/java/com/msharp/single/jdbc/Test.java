package com.msharp.single.jdbc;


import com.msharp.single.jdbc.datasource.jdbc.SingleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Test
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class Test {

    public static void main(String[] args) throws Throwable {
        final SingleDataSource singleDataSource = new SingleDataSource();
        singleDataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/sharding_jdbc_0?characterEncoding=UTF8&amp;socketTimeout=60000&amp;useSSL=false");
        singleDataSource.setUser("root");
        singleDataSource.setPassword("122816");
        singleDataSource.setPoolType("druid");
        singleDataSource.init();
        final Connection connection = singleDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from product");
        preparedStatement.executeQuery();

        System.out.println(connection);

        Thread.sleep(399999);

    }
}
