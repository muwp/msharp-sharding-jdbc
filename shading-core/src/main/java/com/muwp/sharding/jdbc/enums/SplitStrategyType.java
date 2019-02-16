package com.muwp.sharding.jdbc.enums;

/**
 * SplitStrategyType
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public enum SplitStrategyType {

    VERTICAL("vertical"),

    HORIZONTAL("horizontal");

    private String value;

    SplitStrategyType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
