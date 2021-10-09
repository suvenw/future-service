package com.suven.framework.generator.enums;

/**
 * 数据类型转换
 *
 * @author by jueyue on 18-6-12.
 */
public enum TypeConvertEnum {


    ORACLE_BLOB(DBEnum.ORACLE, "BLOB", GenFieldEnum.BYTE_ARR),
    ORACLE_CHAR(DBEnum.ORACLE, "CHAR", GenFieldEnum.STRING),
    ORACLE_CLOB(DBEnum.ORACLE, "CLOB", GenFieldEnum.STRING),
    ORACLE_DATE(DBEnum.ORACLE, "DATE", GenFieldEnum.DATE),
    ORACLE_NUMBER(DBEnum.ORACLE, "NUMBER", GenFieldEnum.BIG_DECIMAL),
    ORACLE_LONG(DBEnum.ORACLE, "LONG", GenFieldEnum.STRING),
    ORACLE_SMALLINT(DBEnum.ORACLE, "SMALLINT", GenFieldEnum.INTEGER),
    ORACLE_TIMESTAMP(DBEnum.ORACLE, "TIMESTAMP", GenFieldEnum.DATE),
    ORACLE_RAW(DBEnum.ORACLE, "RAW", GenFieldEnum.BYTE_ARR),
    ORACLE_VARCHAR2(DBEnum.ORACLE, "VARCHAR2", GenFieldEnum.STRING),


    //----------------------------------------------------------------------
    SQLSERVER_TIMESTAMP(DBEnum.SQLSERVER, "TIMESTAMP", GenFieldEnum.DATE),
    SQLSERVER_BIT(DBEnum.SQLSERVER, "BIT", GenFieldEnum.BOOLEAN),
    SQLSERVER_CHAR(DBEnum.SQLSERVER, "CHAR", GenFieldEnum.STRING),
    SQLSERVER_NCHAR(DBEnum.SQLSERVER, "NCHAR", GenFieldEnum.STRING),
    SQLSERVER_DATETIME(DBEnum.SQLSERVER, "DATETIME", GenFieldEnum.DATE),
    SQLSERVER_MONEY(DBEnum.SQLSERVER, "MONEY", GenFieldEnum.BIG_DECIMAL),
    SQLSERVER_SMALLMONEY(DBEnum.SQLSERVER, "SMALLMONEY", GenFieldEnum.BIG_DECIMAL),
    SQLSERVER_DECIMAL(DBEnum.SQLSERVER, "DECIMAL", GenFieldEnum.BIG_DECIMAL),
    SQLSERVER_FLOAT(DBEnum.SQLSERVER, "FLOAT", GenFieldEnum.DOUBLE),
    SQLSERVER_INT(DBEnum.SQLSERVER, "INT", GenFieldEnum.INTEGER),
    SQLSERVER_IMAGE(DBEnum.SQLSERVER, "IMAGE", GenFieldEnum.BYTE_ARR),
    SQLSERVER_TEXT(DBEnum.SQLSERVER, "TEXT", GenFieldEnum.BYTE_ARR),
    SQLSERVER_BTEXT(DBEnum.SQLSERVER, "NTEXT", GenFieldEnum.BYTE_ARR),
    SQLSERVER_XML(DBEnum.SQLSERVER, "XML", GenFieldEnum.BYTE_ARR),
    SQLSERVER_NUMERIC(DBEnum.SQLSERVER, "NUMERIC", GenFieldEnum.BIG_DECIMAL),
    SQLSERVER_REAL(DBEnum.SQLSERVER, "REAL", GenFieldEnum.FLOAT),
    SQLSERVER_SMALLINT(DBEnum.SQLSERVER, "SMALLINT", GenFieldEnum.SHORT),
    SQLSERVER_SMALL_DATETIME(DBEnum.SQLSERVER, "SMALLDATETIME", GenFieldEnum.DATE),
    SQLSERVER_TINYINT(DBEnum.SQLSERVER, "TINYINT", GenFieldEnum.BYTE),
    SQLSERVER_NVARCHAR(DBEnum.SQLSERVER, "NVARCHAR", GenFieldEnum.STRING),
    SQLSERVER_VARBINARY(DBEnum.SQLSERVER, "VARBINARY", GenFieldEnum.BYTE_ARR),
    SQLSERVER_VARCHAR(DBEnum.SQLSERVER, "VARCHAR", GenFieldEnum.STRING),

    //---------------------------------------------------------------------
    DB2_BIGINT(DBEnum.DB2, "BIGINT", GenFieldEnum.LONG),
    DB2_BLOB(DBEnum.DB2, "BLOB", GenFieldEnum.BYTE_ARR),
    DB2_CHARACTER(DBEnum.DB2, "CHARACTER", GenFieldEnum.STRING),
    DB2_GRAPHIC(DBEnum.DB2, "GRAPHIC", GenFieldEnum.STRING),
    DB2_CLOB(DBEnum.DB2, "CLOB", GenFieldEnum.STRING),
    DB2_DATE(DBEnum.DB2, "DATE", GenFieldEnum.DATE),
    DB2_DECIMAL(DBEnum.DB2, "DECIMAL", GenFieldEnum.BIG_DECIMAL),
    DB2_DOUBLE(DBEnum.DB2, "DOUBLE", GenFieldEnum.DOUBLE),
    DB2_INTEGER(DBEnum.DB2, "INTEGER", GenFieldEnum.INTEGER),
    DB2_LONGVARGRAPHIC(DBEnum.DB2, "LONGVARGRAPHIC", GenFieldEnum.BYTE_ARR),
    DB2_LONGVARCHAR(DBEnum.DB2, "LONGVARCHAR", GenFieldEnum.BYTE_ARR),
    DB2_REAL(DBEnum.DB2, "REAL", GenFieldEnum.LONG),
    DB2_SMALLINT(DBEnum.DB2, "SMALLINT", GenFieldEnum.SHORT),
    DB2_TIME(DBEnum.DB2, "TIME", GenFieldEnum.DATE),
    DB2_TIMESTAMP(DBEnum.DB2, "TIMESTAMP", GenFieldEnum.DATE),
    DB2_VARGRAPHIC(DBEnum.DB2, "VARGRAPHIC", GenFieldEnum.STRING),
    DB2_VARCHAR(DBEnum.DB2, "VARCHAR", GenFieldEnum.STRING),

    //------------------------------------------------------------
    MYSQL_BIGINT(DBEnum.MYSQL, "BIGINT", GenFieldEnum.LONG),
    MYSQL_TINYBLOB(DBEnum.MYSQL, "TINYBLOB", GenFieldEnum.BYTE_ARR),
    MYSQL_BIT(DBEnum.MYSQL, "BIT", GenFieldEnum.BOOLEAN),
    MYSQL_ENUM(DBEnum.MYSQL, "ENUM", GenFieldEnum.STRING),
    MYSQL_SET(DBEnum.MYSQL, "SET", GenFieldEnum.STRING),
    MYSQL_CHAR(DBEnum.MYSQL, "CHAR", GenFieldEnum.STRING),
    MYSQL_DATE(DBEnum.MYSQL, "DATE", GenFieldEnum.DATE),
    MYSQL_YEAR(DBEnum.MYSQL, "YEAR", GenFieldEnum.DATE),
    MYSQL_DECIMAL(DBEnum.MYSQL, "DECIMAL", GenFieldEnum.BIG_DECIMAL),
    MYSQL_NUMERIC(DBEnum.MYSQL, "NUMERIC", GenFieldEnum.BIG_DECIMAL),
    MYSQL_DOUBLE(DBEnum.MYSQL, "DOUBLE", GenFieldEnum.DOUBLE),
    MYSQL_REAL(DBEnum.MYSQL, "REAL", GenFieldEnum.DOUBLE),
    MYSQL_MEDIUMINT(DBEnum.MYSQL, "MEDIUMINT", GenFieldEnum.INTEGER),
    MYSQL_BLOB(DBEnum.MYSQL, "BLOB", GenFieldEnum.BYTE_ARR),
    MYSQL_MEDIUMBLOB(DBEnum.MYSQL, "MEDIUMBLOB", GenFieldEnum.BYTE_ARR),
    MYSQL_LONGBLOB(DBEnum.MYSQL, "LONGBLOB", GenFieldEnum.BYTE_ARR),
    MYSQL_FLOAT(DBEnum.MYSQL, "FLOAT", GenFieldEnum.FLOAT),
    MYSQL_INT(DBEnum.MYSQL, "INT", GenFieldEnum.INTEGER),
    MYSQL_SMALLINT(DBEnum.MYSQL, "SMALLINT", GenFieldEnum.SHORT),
    MYSQL_TIME(DBEnum.MYSQL, "TIME", GenFieldEnum.DATE),
    MYSQL_TIMESTAMP(DBEnum.MYSQL, "TIMESTAMP", GenFieldEnum.DATE),
    MYSQL_DATETIME(DBEnum.MYSQL, "DATETIME", GenFieldEnum.DATE),
    MYSQL_TINYINT(DBEnum.MYSQL, "TINYINT", GenFieldEnum.BOOLEAN),
    MYSQL_VARBINARY(DBEnum.MYSQL, "VARBINARY", GenFieldEnum.BYTE_ARR),
    MYSQL_BINARY(DBEnum.MYSQL, "BINARY", GenFieldEnum.BYTE_ARR),
    MYSQL_VARCHAR(DBEnum.MYSQL, "VARCHAR", GenFieldEnum.STRING),
    MYSQL_TINYTEXT(DBEnum.MYSQL, "TINYTEXT", GenFieldEnum.STRING),
    MYSQL_TEXT(DBEnum.MYSQL, "TEXT", GenFieldEnum.STRING);


    private DBEnum db;
    private String dbType;
    private GenFieldEnum type;

    TypeConvertEnum(DBEnum db, String dbType, GenFieldEnum type) {
        this.db = db;
        this.dbType = dbType;
        this.type = type;
    }

    public static String getTypeByDb(DBEnum db, String dbType) {
        TypeConvertEnum[] types = TypeConvertEnum.values();
        for (int i = 0; i < types.length; i++) {
            if (types[i].db.equals(db) && types[i].dbType.equalsIgnoreCase(dbType)) {
                return types[i].type.getType();
            }
        }
        return null;
    }

    public DBEnum getDb() {
        return db;
    }

    public String getDbType() {
        return dbType;
    }

    public GenFieldEnum getType() {
        return type;
    }

}