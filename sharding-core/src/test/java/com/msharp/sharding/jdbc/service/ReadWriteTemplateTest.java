package com.msharp.sharding.jdbc.service;

import com.msharp.single.jdbc.jtemplate.single.SimpleJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ContextConfiguration(locations = "/spring/sharding-readwrite-test.xml")
public class ReadWriteTemplateTest extends AbstractTestNGSpringContextTests {

    @Test(groups = {"jdbcTemplate"})
    public void tesJdbcTemplate() {
        //
        final SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) applicationContext.getBean("jdbcTemplate");

        //
        final com.msharp.sharding.jdbc.model.Test test = new com.msharp.sharding.jdbc.model.Test();
        test.setAppkey("appkey");


        final List<com.msharp.sharding.jdbc.model.Test> update = jdbcTemplate.query("select id from test where appkey in( ? ,?) ", new Object[]{"appkey", "rpc"},
                new RowMapper<com.msharp.sharding.jdbc.model.Test>() {

                    @Override
                    public com.msharp.sharding.jdbc.model.Test mapRow(ResultSet rs, int rowNum) throws SQLException {
                        final com.msharp.sharding.jdbc.model.Test model = new com.msharp.sharding.jdbc.model.Test();
                        model.setId(rs.getLong(1));
                        model.setAppkey(rs.getString(2));
                        model.setName(rs.getString(3));
                        model.setAge(rs.getInt(4));
                        model.setUpdateTime(rs.getTimestamp(5));
                        return model;
                    }
                });
        System.out.println(update);
        System.out.println(update.size());
    }
}
