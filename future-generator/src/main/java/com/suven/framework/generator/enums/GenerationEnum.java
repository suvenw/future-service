package com.suven.framework.generator.enums;

/**
 * 主键类型
 *
 * @author suven
 * @date 2014年12月25日
 */
public enum GenerationEnum {

    TABLE("table"), SEQUENCE("sequence"), IDENTITY("identity"), AUTO("auto");

    private String value;

    private GenerationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}