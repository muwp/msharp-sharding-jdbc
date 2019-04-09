package com.msharp.single.jdbc;


import com.msharp.single.jdbc.datasource.jdbc.DruidSingleDataSource;
import com.msharp.single.jdbc.datasource.jdbc.SingleDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Test
 *
 * @author mwup
 * @version 1.0
 * @created 2018/10/29 13:51
 **/
public class Test {

    public static void main(String[] args) throws Throwable {
        final DruidSingleDataSource singleDataSource = new DruidSingleDataSource();
        singleDataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/sharding_jdbc_0?characterEncoding=UTF8&amp;socketTimeout=60000&amp;useSSL=false");
        singleDataSource.setUser("root");
        singleDataSource.setPassword("122816");
        singleDataSource.init();
        final Connection connection = singleDataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from product");
        ResultSet resultSet= preparedStatement.executeQuery();

        while (resultSet.next()){
          String value=  resultSet.getString(1);
          System.out.println(value);
        }
        System.out.println(connection);

        Thread.sleep(399999);

    }
}
