package com.suven.framework.generator.entity;

import java.io.Serializable;

/**
 *
 */
public class PageFieldEntity extends JavaClassFieldEntity implements Serializable  {

    private static final long serialVersionUID = 1L;

    /**
     * .是否为主键
     */
    private Integer isKey;
    /**
     * .数据库字段名称
     */
    private String columnName;
    /**
     * 1.数据库字段注释
     */
    private String comments;
    /**
     * 1.数据库字段类型
     */
    private String dataType;


    /**
     * 查询类型
     */
    private Integer queryMode;
//            = QueryTypeEnum.EQ.getCode();

    /**
     * .显示类型,1:文本;2:日期时间;3:下拉框4:单选框;5:多选框;6:多行文本;7:文件
     */
    private int showType;

    /**
     * 前端校验
     */
    private Integer viewVerification;

    /**
     * 后台校验
     */
    private Integer serverVerification;

    /**
     * 允许空
     */
    private Integer notNull;

    /**
     * 最小
     */
    private String minNum;

    /**
     * 最大
     */
    private String maxNum;

    /**
     * 正则
     */
    private String regex;


    /**
     * 字段默认值
     */
    private String fieldDefault;

    public static PageFieldEntity build(){
        return new PageFieldEntity();
    }

    public Integer getIsKey() {
        return isKey;
    }

    public PageFieldEntity setIsKey(Integer isKey) {
        this.isKey = isKey;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public PageFieldEntity setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public PageFieldEntity setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public PageFieldEntity setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }


    public Integer getQueryMode() {
        return queryMode;
    }

    public PageFieldEntity setQueryMode(Integer queryMode) {
        this.queryMode = queryMode;
        return this;
    }

    public int getShowType() {
        return showType;
    }

    public PageFieldEntity setShowType(int showType) {
        this.showType = showType;
        return this;
    }

    public Integer getViewVerification() {
        return viewVerification;
    }

    public PageFieldEntity setViewVerification(Integer viewVerification) {
        this.viewVerification = viewVerification;
        return this;
    }

    public Integer getServerVerification() {
        return serverVerification;
    }

    public PageFieldEntity setServerVerification(Integer serverVerification) {
        this.serverVerification = serverVerification;
        return this;
    }

    public Integer getNotNull() {
        return notNull;
    }

    public PageFieldEntity setNotNull(Integer notNull) {
        this.notNull = notNull;
        return this;
    }

    public String getMinNum() {
        return minNum;
    }

    public PageFieldEntity setMinNum(String minNum) {
        this.minNum = minNum;
        return this;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public PageFieldEntity setMaxNum(String maxNum) {
        this.maxNum = maxNum;
        return this;
    }

    public String getRegex() {
        return regex;
    }

    public PageFieldEntity setRegex(String regex) {
        this.regex = regex;
        return this;
    }

    public String getFieldDefault() {
        return fieldDefault;
    }

    public PageFieldEntity setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
        return this;
    }
}
