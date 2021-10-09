package com.suven.framework.generator.entity;

import org.apache.commons.lang.StringUtils;

public class ClassConfigEntity {

 /** 表名称,以逗号隔开,eg:tb_user,tb_ouath **/
    private String tables;
    /**引用构架包目录,eg: top.suven.modules **/
    private String mainPath;
    /** package, 项目包目录 **/
    private String packageName;
    /** moduleName,模块名称,eg:user **/
    private String moduleName;
    /** author,作者名,eg:suven **/
    private String author;
    /** 邮箱,eg:suvenw@gmail.com **/
    private String email;
    /** 删除表前缀,eg:tb_ **/
    private String tablePrefix;

    /** spring mvc Service 引用标签包路径 或 dubbo Service 路径  */
    private String importServicePackage = "import org.springframework.stereotype.Service;";
    /** spring mvc Autowired 引用标签包路径 或 dubbo Reference 路径  */
    private String importAutowiredPackage = "import org.springframework.beans.factory.annotation.Autowired;";
    /** spring mvc @Autowired 引用标签 或对应 dubbo 的 @Reference*/
    private String importAutowired = "@Autowired";




    public static ClassConfigEntity build(){
        return new ClassConfigEntity();
    }

    public static ClassConfigEntity init(){
      return   build()
              .setMainPath("top.suven.future")
              .setPackageName("top.suven.future.test")
              .setModuleName("user")
              .setAuthor("suven")
              .setEmail("suvenw@gmail.com")
              .setTablePrefix("");

    }

    public void setDubboInfo(){
        importServicePackage = "import org.apache.dubbo.config.annotation.Service;";
        importAutowiredPackage ="import org.apache.dubbo.config.annotation.Reference;";
        importAutowired = "@Reference";
    }


    public String getTables() {
        if(null == tables){
            return "";
        }
        return tables;
    }

    public ClassConfigEntity setTables(Object tables) {
        if(tables == null){return this;}
        this.tables = tables.toString();
        return this;
    }
    public String getMainPath() {
        mainPath = StringUtils.isBlank(mainPath) ? "top.suven" : mainPath;
        return mainPath;
    }

    public ClassConfigEntity setMainPath(Object mainPath) {
        if(mainPath == null){
            return this;}
        this.mainPath = mainPath.toString();
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public ClassConfigEntity setPackageName(Object packageName) {
        if(packageName == null){return this;}
        this.packageName = packageName.toString();
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public ClassConfigEntity setModuleName(Object moduleName) {
        if(moduleName == null){return this;}
        this.moduleName = moduleName.toString();
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ClassConfigEntity setAuthor(Object author) {
        if(author == null){return this;}
        this.author = author.toString();
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClassConfigEntity setEmail(Object email) {
        if(email == null){return this;}
        this.email = email.toString();
        return this;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public ClassConfigEntity setTablePrefix(Object tablePrefix) {
        if(tablePrefix == null){return this;}
        this.tablePrefix = tablePrefix.toString();
        return this;
    }

    public String getImportServicePackage() {
        return importServicePackage;
    }

    public ClassConfigEntity setImportServicePackage(String importServicePackage) {
        this.importServicePackage = importServicePackage;
        return this;
    }

    public String getImportAutowiredPackage() {
        return importAutowiredPackage;
    }

    public ClassConfigEntity setImportAutowiredPackage(String importAutowiredPackage) {
        this.importAutowiredPackage = importAutowiredPackage;
        return this;
    }

    public String getImportAutowired() {
        return importAutowired;
    }

    public ClassConfigEntity setImportAutowired(String importAutowired) {
        this.importAutowired = importAutowired;
        return this;
    }
}
