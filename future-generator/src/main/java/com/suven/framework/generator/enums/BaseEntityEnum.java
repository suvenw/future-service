package com.suven.framework.generator.enums;

public enum BaseEntityEnum {

    BASE_ENTITY(1, "BaseEntity"), BASE_STATUS_ENTITY(2, "BaseStatusEntity");

    private int id;
    private String value;

    BaseEntityEnum(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }



    public String getValue() {
        return value;
    }


}
