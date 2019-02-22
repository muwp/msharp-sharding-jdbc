package com.msharp.sharding.jdbc.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "test")
public class TestModel implements Serializable {

    private Long id;

    @Column(name = "app_ap")
    private String appkey;

    private String name;

    private Integer age;

    private Date updateTime;

    private String appKeyValue;

    private List<String> valueList;

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

    public List<String> getValueList() {
        return valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
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
