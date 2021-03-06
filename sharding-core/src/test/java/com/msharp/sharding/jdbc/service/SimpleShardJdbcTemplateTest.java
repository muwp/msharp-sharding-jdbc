package com.msharp.sharding.jdbc.service;

import com.msharp.sharding.jdbc.jtemplate.jdbc.HighLevelShardJdbcTemplate;
import com.msharp.sharding.jdbc.model.TestModel;
import com.msharp.single.jdbc.jtemplate.single.SimpleJdbcTemplate;
import com.msharp.sharding.jdbc.model.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = "/spring/jdbc-test.xml")
public class SimpleShardJdbcTemplateTest extends AbstractTestNGSpringContextTests {

   // @Test(groups = {"jdbcTemplate"})
    public void testJdbcTemplate() {
        final SimpleJdbcTemplate jdbcTemplate = (SimpleJdbcTemplate) applicationContext.getBean("jdbcTemplate");
        Test test = new Test();
        test.setAge(20);
        test.setAppkey("appkey");
        test.setName("name_1");
        test.setUpdateTime(new Date());
        final int update = jdbcTemplate.insert(test);
        System.out.println(update);
        System.out.println(test);
    }

//    @Test(groups = {"highLevelShardJdbcTemplate"})
    public void testSimpleSplitJdbcTemplate() {
        HighLevelShardJdbcTemplate jdbcTemplate = (HighLevelShardJdbcTemplate) applicationContext.getBean("highLevelShardJdbcTemplate");
        //final int update = jdbcTemplate.update("test", "insert into sharding_jdbc.test(id,appkey,name,age,update_time) values(?,?,?,?,?)", new Object[]{3, "test", "three", 19, new Date()});
        //System.out.println("result:" + update);
    }

  //  @Test(groups = {"highLevelShardJdbcTemplate"})
    public void testQuery() {
        HighLevelShardJdbcTemplate jdbcTemplate = (HighLevelShardJdbcTemplate) applicationContext.getBean("highLevelShardJdbcTemplate");
        final List<TestModel> result = jdbcTemplate.query("test", "select id,appkey,name,age,update_time from sharding_jdbc.test ", new RowMapper<TestModel>() {
            @Override
            public TestModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                final TestModel model = new TestModel();
                model.setId(rs.getLong(1));
                model.setAppkey(rs.getString(2));
                model.setName(rs.getString(3));
                model.setAge(rs.getInt(4));
                model.setUpdateTime(rs.getTimestamp(5));
                return model;
            }
        });
        System.out.println("result:" + result);
    }

  //  @Test(groups = {"splitJdbcTemplate"})
    public void testSplitJdbcTemplate() {

        HighLevelShardJdbcTemplate simpleSplitJdbcTemplate = (HighLevelShardJdbcTemplate) applicationContext;

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
