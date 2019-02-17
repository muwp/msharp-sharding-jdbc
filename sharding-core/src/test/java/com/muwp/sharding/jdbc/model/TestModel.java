package com.muwp.sharding.jdbc.model;

import java.io.Serializable;
import java.util.Date;

public class TestModel implements Serializable {

    private Long id;

    private String appkey;

    private String name;

    private Integer age;

    private Date updateTime;

    private String appKeyValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAppKeyValue() {
        return appKeyValue;
    }

    public void setAppKeyValue(String appKeyValue) {
        this.appKeyValue = appKeyValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestModel{");
        sb.append("id=").append(id);
        sb.append(", appkey='").append(appkey).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", updateTime=").append(updateTime);
        sb.append('}');
        return sb.toString();
    }
}
