package com.suven.framework.generator.enums;

public enum GenFieldEnum {

    OBJECT("Object"),
    STRING("String"),
    CHAR("char"),
    BOOLEAN("Boolean"),
    SHORT("Short"),
    LONG("Long"),
    BYTE_ARR("byte[]"),
    BYTE("byte"),
    DATE("Date"),
    BIG_DECIMAL("BigDecimal"),
    DOUBLE("Double"),
    FLOAT("Float"),
    INTEGER("Integer");


    private String type;

    GenFieldEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}