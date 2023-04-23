package com.suven.framework.generator.enums;
public enum DBEnum {
    MYSQL("mysql", "com.mysql.jdbc.Driver"),
    ORACLE("oracle", "oracle.jdbc.driver.OracleDriver"),
    SQLSERVER("sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),
    POSTGRESQL("postgresql", "org.postgresql.Driver"),
    DB2("bd2", "org.postgresql.Driver");


    private String type;
    private String driver;

    private DBEnum(String type, String driver) {
        this.type = type;
        this.driver = driver;
    }

    public static DBEnum getDbTypeByType(String type) {
        DBEnum[] types = values();
        for (int i = 0; i < types.length; i++) {
            if (types[i].getType().equals(type)) {
                return types[i];
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}