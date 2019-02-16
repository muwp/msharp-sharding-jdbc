package com.muwp.sharding.jdbc;

import com.muwp.sharding.jdbc.core.SimpleSplitJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;


@ContextConfiguration(locations = "/spring/jdbc-test.xml")
public class SimpleSplitJdbcTemplateTest extends AbstractTestNGSpringContextTests {

    @Test(groups = {"simpleSplitJdbcTemplate"})
    public void testSimpleSplitJdbcTemplate() {
        SimpleSplitJdbcTemplate jdbcTemplate = (SimpleSplitJdbcTemplate) applicationContext.getBean("simpleSplitJdbcTemplate");
        final int update = jdbcTemplate.update("test", "insert into sharding_jdbc.test(id,appkey,name,age,update_time) values(?,?,?,?,?)", new Object[]{3, "test", "three", 19, new Date()});
        System.out.println("result:" + update);
    }

    @Test(groups = {"simpleSplitJdbcTemplate"})
    public void testQuery() {
        SimpleSplitJdbcTemplate jdbcTemplate = (SimpleSplitJdbcTemplate) applicationContext.getBean("simpleSplitJdbcTemplate");
        final List<RowMapper> result = jdbcTemplate.query("test", "select id,appkey,name,age,update_time from name=?", new Object[]{"one"}, (RowMapper) null);
        System.out.println("result:" + result);
    }

    //@Test(groups = {"splitJdbcTemplate"})
    public void testSplitJdbcTemplate() {
        SimpleSplitJdbcTemplate simpleSplitJdbcTemplate = (SimpleSplitJdbcTemplate) applicationContext;

        long id = 1;
        System.out.println("id:" + id);

        int r = simpleSplitJdbcTemplate.update(id, "insert into test_db.TEST_TABLE(ID, NAME, GENDER, LST_UPD_USER, LST_UPD_TIME) values (?, ?, ?, ?, ?)", new Object[]{id, "test" + id, 0, "test", new Date()});
        AssertJUnit.assertEquals(1, r);

        r = simpleSplitJdbcTemplate.update(id, "update test_db.TEST_TABLE set name = ? where id = ?", new Object[]{"test1" + id, id});
        AssertJUnit.assertEquals(1, r);

        r = simpleSplitJdbcTemplate.update(id, "delete from test_db.TEST_TABLE where id = ?", new Object[]{id});
        AssertJUnit.assertEquals(1, r);
    }
}