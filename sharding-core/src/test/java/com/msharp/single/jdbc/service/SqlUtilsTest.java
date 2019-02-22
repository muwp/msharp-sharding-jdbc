package com.msharp.single.jdbc.service;

import com.msharp.sharding.jdbc.bean.SqlBean;
import com.msharp.single.jdbc.model.TestModel;
import com.msharp.sharding.jdbc.manager.SqlParserManager;

public class SqlUtilsTest {

    public static void main(String[] args) {
        final TestModel model = new TestModel();
        model.setId(2L);
//      model.setAge(2);
        model.setAppkey("dd");
//        model.setUpdateTime(new Date());
        //model.setValueList(Arrays.asList("1", "2", "4"));
        SqlBean sqlBean = SqlParserManager.getInstance().generateSelectSql("id", 1l, TestModel.class);
        System.out.println(sqlBean);
    }
}