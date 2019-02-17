package com.muwp.sharding.jdbc.service;

import com.muwp.sharding.jdbc.bean.SqlBean;
import com.muwp.sharding.jdbc.model.TestModel;
import com.muwp.sharding.jdbc.util.SqlUtil;

import java.util.Date;

public class SqlUtilsTest {

    public static void main(String[] args) {
        TestModel model = new TestModel();
        model.setId(2L);
        model.setAge(2);
        model.setAppKeyValue("dd");
        model.setUpdateTime(new Date());
        SqlBean sqlBean = SqlUtil.generateUpdateSql(model);
        System.out.println(sqlBean);
    }
}
