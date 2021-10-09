package com.suven.framework.generator.entity;


import java.util.List;

public class GeneratorInfo {

    private ClassConfigEntity classEntity;
    private TableEntity tableEntity;
    private List<ColumnBean> pageColumnBeanList;



    public GeneratorInfo(){

    }

    public static GeneratorInfo build(){
        return new GeneratorInfo();
    }

    public static GeneratorInfo build(ClassConfigEntity classEntity, TableEntity tableEntity) {
       return build().setClassEntity(classEntity).setTableEntity(tableEntity);
    }

    public ClassConfigEntity getClassEntity() {
        return classEntity;
    }

    public GeneratorInfo setClassEntity(ClassConfigEntity classEntity) {
        this.classEntity = classEntity;
        return this;
    }

    public TableEntity getTableEntity() {
        return tableEntity;
    }

    public GeneratorInfo setTableEntity(TableEntity tableEntity) {
        this.tableEntity = tableEntity;
        return this;
    }

    public List<ColumnBean> getPageColumnBeanList() {
        return pageColumnBeanList;
    }

    public void setPageColumnBeanList(List<ColumnBean> pageColumnBeanList) {
        this.pageColumnBeanList = pageColumnBeanList;
    }
}
