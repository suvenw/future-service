package com.suven.framework.generator.config;



public class ProjectPathConfig implements IProjectPathConfig{

    private int id;
    private String userNme;
    private String baseProjectPath;
    private String rpcProjectPath;
    private String httpProjectPath;
    private String sysProjectPath;
    private String apiProjectPath;

    private String templatesPath;
    private String htmlPath;
    private int isUse;

    public static String srcMainJava = "src/main/java/";
    public static String srcMainResourcesViews = "src/main/resources/views/";
    public static String templates = "template/";
    public static String modules = "modules";

    public static ProjectPathConfig build(){
        return new ProjectPathConfig();
    }


    public String getProjectPath(int createCodeEnumIndex){
        switch (createCodeEnumIndex){
            case 1:
                return getRpcProjectPath().trim();
            case 2:
               return getApiProjectPath().trim();
            case 3:
               return getHttpProjectPath().trim();
            case 4:
                return getSysProjectPath().trim();
            default:
                throw new RuntimeException("项目的ProjectRoot根目录无法找到,请检查模板CreateCodeEnum的实现类 ");
        }
    }

    public String getSysProjectPath() {
        if(sysProjectPath == null){
            this.sysProjectPath = getHttpProjectPath();
        }
        return sysProjectPath;
    }

    public ProjectPathConfig setSysProjectPath(String sysProjectPath) {
        this.sysProjectPath = sysProjectPath;
        return this;
    }

    public String getRpcProjectPath() {
        return rpcProjectPath;
    }

    public ProjectPathConfig setRpcProjectPath(String rpcProjectPath) {
        this.rpcProjectPath = rpcProjectPath;
        return this;
    }

    public String getHttpProjectPath() {
        return httpProjectPath;
    }

    public ProjectPathConfig setHttpProjectPath(String httpProjectPath) {
        this.httpProjectPath = httpProjectPath;
        return this;
    }

    public String getApiProjectPath() {
        return apiProjectPath;
    }

    public ProjectPathConfig setApiProjectPath(String apiProjectPath) {
        this.apiProjectPath = apiProjectPath;
        return this;
    }

    public String getTemplatesPath() {
        return templatesPath;
    }

    public ProjectPathConfig setTemplatesPath(String templatesPath) {
        this.templatesPath = templatesPath;
        return this;
    }

    public int getId() {
        return id;
    }

    public ProjectPathConfig setId(int id) {
        this.id = id;
        return this;
    }

    public String getUserNme() {
        return userNme;
    }

    public ProjectPathConfig setUserNme(String userNme) {
        this.userNme = userNme;
        return this;
    }

    public int getIsUse() {
        return isUse;
    }

    public ProjectPathConfig setIsUse(int isUse) {
        this.isUse = isUse;
        return this;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public ProjectPathConfig setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
        return this;
    }

    public String getBaseProjectPath() {
        return baseProjectPath;
    }

    public ProjectPathConfig setBaseProjectPath(String baseProjectPath) {
        this.baseProjectPath = baseProjectPath;
        return this;
    }



    public static ProjectPathConfig init2(){
        return build().setId(2).setUserNme("xxx").setIsUse(0)
                .setBaseProjectPath("/Users/suven/project/workspace/ds-video-service/")
                .setApiProjectPath("/rpc-api/src/main/java/")
                .setHttpProjectPath("/http-resource-service/src/main/java/")
                .setRpcProjectPath("/rpc-resource-service/src/main/java/")
                .setHtmlPath("/http-vue/src/")
                .setTemplatesPath("/Users/suven/project/workspace/st-video-service/templates/templates/");
                }
    // /Users/sixeco/suven/workspace/suvenw/weixue-service
    public static ProjectPathConfig init3(){
        return build().setId(3).setUserNme("Suven").setIsUse(1)
                .setBaseProjectPath("/Users/sixeco/suven/workspace/suvenw/weixue-service")
                .setApiProjectPath("api-poems-service")
                .setHttpProjectPath("http-poems-service")
                .setRpcProjectPath("rpc-poems-service")
                .setHtmlPath("api-core-service/src/views/poems")
                .setTemplatesPath("/Users/suven/project/workspace/openSource/future-service/future-http-system/src/main/resources");

    }
    public static ProjectPathConfig init4(){
        return build().setId(4).setUserNme("Suven").setIsUse(0)
                .setBaseProjectPath("/Users/suven/project/workspace/openSource/future-service")
                .setApiProjectPath("future-http-system")
                .setHttpProjectPath("future-http-system")
                .setRpcProjectPath("future-http-system")
                .setHtmlPath("future-vue/src/views/test")
                .setTemplatesPath("/Users/suven/project/workspace/openSource/future-service/future-http-system/src/main/resources");

    }

}
