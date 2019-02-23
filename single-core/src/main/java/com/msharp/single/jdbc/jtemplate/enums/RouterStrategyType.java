package com.msharp.single.jdbc.jtemplate.enums;

/**
 * RouterStrategyType
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/15 13:51
 **/
public enum RouterStrategyType {

    VERTICAL("vertical"),

    HORIZONTAL("horizontal");

    private String value;

    RouterStrategyType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
