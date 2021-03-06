package com.msharp.sharding.jdbc.service;

import com.msharp.single.jdbc.jtemplate.single.SimpleJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = "/spring/sharding-test.xml")
public class JdbcTemplateTest extends AbstractTestNGSpringContextTests {

    @Test(groups = {"jdbcTemplate"})
    public void testJdbcTemplate() {

        //quite
        final SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) applicationContext.getBean("jdbcTemplate");
        final com.msharp.sharding.jdbc.model.Test test = new com.msharp.sharding.jdbc.model.Test();

        //
        test.setAppkey("appkey");

        //
        List<com.msharp.sharding.jdbc.model.Test> update = jdbcTemplate.query("select count(*) from product where appkey=?", new Object[]{"supp"}, new RowMapper<com.msharp.sharding.jdbc.model.Test>() {

                    @Override
                    public com.msharp.sharding.jdbc.model.Test mapRow(ResultSet rs, int rowNum) throws SQLException {
                        final com.msharp.sharding.jdbc.model.Test model = new com.msharp.sharding.jdbc.model.Test();
                        //model.setId(rs.getLong(1));
                        //model.setAppkey(rs.getString(2));
                        //model.setName(rs.getString(3));
                        //model.setAge(rs.getInt(4));
                        //model.setUpdateTime(rs.getTimestamp(5));
                        return model;
                    }
                }
        );

        update = jdbcTemplate.query("select count(*) from product where appkey=?", new Object[]{"supp"}, new RowMapper<com.msharp.sharding.jdbc.model.Test>() {

            @Override
            public com.msharp.sharding.jdbc.model.Test mapRow(ResultSet rs, int rowNum) throws SQLException {
                final com.msharp.sharding.jdbc.model.Test model = new com.msharp.sharding.jdbc.model.Test();
                model.setId(rs.getLong(1));
                return model;
            }
        });

        System.out.println(update);
        System.out.println(update.size());
    }


    //@Test(groups = {"jdbcTemplate"})
    public void tesJdbcTemplate() {
        final SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) applicationContext.getBean("jdbcTemplate");
        com.msharp.sharding.jdbc.model.Test test = new com.msharp.sharding.jdbc.model.Test();
        test.setAppkey("appkey");
        final List<com.msharp.sharding.jdbc.model.Test> update = jdbcTemplate.query("select id from test where appkey in( ? ,?)  ", new Object[]{"appkey", "rpc"},
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

    @Test(groups = {"jdbcTemplate"})
    public void testInsertJdbcTemplate() {
        final SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) applicationContext.getBean("jdbcTemplate");
        com.msharp.sharding.jdbc.model.Test test = new com.msharp.sharding.jdbc.model.Test();
        test.setAppkey("supp");
        test.setName("supp");
        test.setUpdateTime(new Date());
        test.setAge(1);
        jdbcTemplate.update("update test set id=3 where id=3");
        // System.out.println(update);
    }

    //@Test(groups = {"dataSource"})
    public void testDataSource() throws Throwable {
        final DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        final Connection connection = dataSource.getConnection();
        connection.close();
    }
}
