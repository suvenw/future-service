package com.suven.framework.generator.entity;

import java.util.HashMap;
import java.util.Map;

public enum DatabaseEnum {
    MYSQL("mysql"),
    ORACLE("oracle"),
    SQLSERVER("sqlserver"),
    POSTGRESQL("postgresql")
        ;

    private static Map<String, DatabaseEnum> tbTypeMap = new HashMap<>();
    static {

        for(DatabaseEnum type : values()) {
            tbTypeMap.put(type.value, type);
        }
    }

    private String value;

    DatabaseEnum(String value) {
        this.value = value;
    }

    public static String getValue(String database) {
        if(database == null )return null;
        DatabaseEnum databaseEnum =  tbTypeMap.get(database.toLowerCase());
        if(databaseEnum == null )return null;
        return databaseEnum.value;
    }

}
