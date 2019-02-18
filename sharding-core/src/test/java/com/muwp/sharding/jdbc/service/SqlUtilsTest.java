package com.muwp.sharding.jdbc.service;

import com.muwp.sharding.jdbc.bean.SqlBean;
import com.muwp.sharding.jdbc.model.TestModel;
import com.muwp.sharding.jdbc.manager.SqlParserManager;

import java.util.Arrays;
import java.util.Date;

public class SqlUtilsTest {

    public static void main(String[] args) {
        TestModel model = new TestModel();
//        model.setId(2L);
//        model.setAge(2);
//        model.setAppkey("dd");
//        model.setUpdateTime(new Date());
        model.setValueList(Arrays.asList("1", "2", "4"));
        SqlBean sqlBean = SqlParserManager.generateSearchSql(model, 1, 39);
        System.out.println(sqlBean);
    }
}