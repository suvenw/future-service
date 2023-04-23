package com.suven.framework.generator.entity;

import java.io.Serializable;

/**
 * 生成java类属性信息
 *
 * @author suven
 * @email suvenw@gmail.com
 * @date 2016年12月20日 上午12:01:45
 */
public class JavaClassFieldEntity implements Serializable {

    //属性名称(第一个字母大写)，如：user_name => UserName
    private String attrName;
    //属性名称(第一个字母小写)，如：user_name => userName
    private String attrname;
    //属性类型
    private String attrType;



    //java 定义注解
    private String explainAttrName;
    //java 定义文档
    private String docAttrName;
    //java 定义导出
    private String excelAttrName;
    //java 定义属性
    private String fieldAttrName;
    //java 定义属性set方法
    private String setAttrName;
    //java 定义属性get方法
    private String getAttrName;
    //java 定义属性build方法
    private String buildAttrName;

    //java 定义属性build方法
    private String buildRequestVoName;
    //java 定义属性build方法
    private String buildRequestAddVoName;
    //java 定义属性build方法
    private String buildRequestQueryVoName;

    //java 定义属性build方法
    private String buildResponseVoName;
    //java 定义属性build方法
    private String buildResponseShowVoName;

    //java 定义属性build方法
    private String buildRequestDtoName;
    //java 定义属性build方法
    private String buildResponseDtoName;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getExplainAttrName() {
        return explainAttrName;
    }

    public void setExplainAttrName(String explainAttrName) {
        this.explainAttrName = explainAttrName;
    }

    public String getDocAttrName() {
        return docAttrName;
    }

    public void setDocAttrName(String docAttrName) {
        this.docAttrName = docAttrName;
    }

    public String getExcelAttrName() {
        return excelAttrName;
    }

    public void setExcelAttrName(String excelAttrName) {
        this.excelAttrName = excelAttrName;
    }

    public String getFieldAttrName() {
        return fieldAttrName;
    }

    public void setFieldAttrName(String fieldAttrName) {
        this.fieldAttrName = fieldAttrName;
    }

    public String getSetAttrName() {
        return setAttrName;
    }

    public void setSetAttrName(String setAttrName) {
        this.setAttrName = setAttrName;
    }

    public String getGetAttrName() {
        return getAttrName;
    }

    public void setGetAttrName(String getAttrName) {
        this.getAttrName = getAttrName;
    }

    public String getBuildAttrName() {
        return buildAttrName;
    }

    public void setBuildAttrName(String buildAttrName) {
        this.buildAttrName = buildAttrName;
    }

    public String getBuildRequestVoName() {
        return buildRequestVoName;
    }

    public void setBuildRequestVoName(String buildRequestVoName) {
        this.buildRequestVoName = buildRequestVoName;
    }

    public String getBuildRequestAddVoName() {
        return buildRequestAddVoName;
    }

    public void setBuildRequestAddVoName(String buildRequestAddVoName) {
        this.buildRequestAddVoName = buildRequestAddVoName;
    }

    public String getBuildRequestQueryVoName() {
        return buildRequestQueryVoName;
    }

    public void setBuildRequestQueryVoName(String buildRequestQueryVoName) {
        this.buildRequestQueryVoName = buildRequestQueryVoName;
    }

    public String getBuildResponseVoName() {
        return buildResponseVoName;
    }

    public void setBuildResponseVoName(String buildResponseVoName) {
        this.buildResponseVoName = buildResponseVoName;
    }

    public String getBuildResponseShowVoName() {
        return buildResponseShowVoName;
    }

    public void setBuildResponseShowVoName(String buildResponseShowVoName) {
        this.buildResponseShowVoName = buildResponseShowVoName;
    }

    public String getBuildRequestDtoName() {
        return buildRequestDtoName;
    }

    public void setBuildRequestDtoName(String buildRequestDtoName) {
        this.buildRequestDtoName = buildRequestDtoName;
    }

    public String getBuildResponseDtoName() {
        return buildResponseDtoName;
    }

    public void setBuildResponseDtoName(String buildResponseDtoName) {
        this.buildResponseDtoName = buildResponseDtoName;
    }
}
