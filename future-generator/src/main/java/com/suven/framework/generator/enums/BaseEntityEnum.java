package com.suven.framework.generator.enums;

import java.util.HashMap;
import java.util.Map;

public enum BaseEntityEnum {



    BASE_ENTITY(1, "BaseEntity","id,createDate,modifyDate","id,create_date,modify_date"),
    BASE_STATUS_ENTITY(2, "BaseStatusEntity","id,createDate,modifyDate,sort,status","id,create_date,modify_date,sort,status"),
    BASE_TIME_ENTITY(3, "BaseTimeEntity","id,createTime,updateTime","create_time,update_time"),
    BASE_BY_TIME_ENTITY(4, "BaseByTimeEntity","id,createTime,updateTime,createBy,updateBy","create_time,update_time,create_by,update_by"),
    BASE_ID_ENTITY(5, "BaseIdEntity","id","id"),
    ;

    private int id;
    private String value;
    private String excValue;
    private String excSql;

    BaseEntityEnum(int id, String value,String excValue, String excSql) {
        this.id = id;
        this.value = value;
        this.excValue = excValue;
        this.excSql = excSql;
    }

    private static Map<String ,BaseEntityEnum> enumMap = new HashMap<>();
    static {
        for(BaseEntityEnum enums : values()) {
            enumMap.put(enums.value, enums);
        }

    }
    public  static BaseEntityEnum getEnum(String value){
        return enumMap.get(value);
    }

    public int getId() {
        return id;
    }



    public String getValue() {
        return value;
    }

    public String getExcValue() {
        return excValue;
    }

    public String getExcSql() {
        return excSql;
    }
}
