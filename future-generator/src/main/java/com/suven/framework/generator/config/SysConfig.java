package com.suven.framework.generator.config;



public class SysConfig {

    private String databaseType = "mysql";
    private String dataSourceName;//数据库类实现名
    private String entity = "BaseEntity"; //1.BaseEntity, 2.BaseStatusEntity,3.BaseTimeEntity,4.BaseByTimeEntity
    private String entityDao = "MyBatisBaseCacheDao"; //1.JdbcBaseEntityDao, 2.JdbcBaseCacheDao,3.MyBatisBaseEntityDao,4.MyBatisBaseCacheDao
    private String tempEnum = "MybatisCodeEnum"; //1.JdbcCodeCacheEnum, 2.JdbcCodeStatusEnum, 3.MybatisCodeEnum,4.MvcCodeEnum,5.SimpMvcCodeEnum
    private int pageVal = 1;
    private int dubbo = 0;
    private int mvc = 1;
    private int isOverrideWrite = 0;
    private int isLombok= 1;
    /**
     * 模仿的对象名
     */
    private String imitationBean;



    public String getDatabaseType() {
        return databaseType;
    }

    public SysConfig setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
        return this;
    }

    public int getPageVal() {
        return pageVal;
    }

    public SysConfig setPageVal(int pageVal) {
        this.pageVal = pageVal;
        return this;
    }

    public int getDubbo() {
        return dubbo;
    }

    public void setDubbo(int dubbo) {
        this.dubbo = dubbo;
    }

    public int getMvc() {
        return mvc;
    }

    public void setMvc(int mvc) {
        this.mvc = mvc;
    }

    public String getDataSourceName() {
        if(null == dataSourceName || "".equals(dataSourceName)){
          return  "AbstractDbService";
        }
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public int getIsOverrideWrite() {
        return isOverrideWrite;
    }

    public void setIsOverrideWrite(int isOverrideWrite) {
        this.isOverrideWrite = isOverrideWrite;
    }

    public String getTempEnum() {
        return tempEnum;
    }

    public void setTempEnum(String tempEnum) {
        this.tempEnum = tempEnum;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityDao() {
        return entityDao;
    }

    public void setEntityDao(String entityDao) {
        this.entityDao = entityDao;
    }

    public int getIsLombok() {
        return isLombok;
    }

    public void setIsLombok(int isLombok) {
        this.isLombok = isLombok;
    }

    public String getImitationBean() {
        return imitationBean;
    }

    public void setImitationBean(String imitationBean) {
        this.imitationBean = imitationBean;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
                "databaseType='" + databaseType + '\'' +
                ", dataSourceName='" + dataSourceName + '\'' +
                ", entity=" + entity +
                ", entityDao=" + entityDao +
                ", tempEnum='" + tempEnum + '\'' +
                ", pageVal=" + pageVal +
                ", dubbo=" + dubbo +
                ", mvc=" + mvc +
                ", isLombok=" + isLombok +
                ", imitationBean=" + imitationBean +
                '}';
    }
}
