package com.suven.framework.generator.entity;

import com.suven.framework.generator.config.SysConfig;

/**
 * @author Created by 陈淦彬
 * @date 2022/9/14
 */
public class CodeCreateInfo extends ClassConfigEntity {

    private String baseProjectPath;
    private String rpcProjectPath = "";
    private String httpProjectPath = "";
    private String sysProjectPath = "";
    private String apiProjectPath = "";
    private String templatesPath = "";
    private String htmlPath = "";

    private SysConfig sysConfig;

    public String getBaseProjectPath() {
        return baseProjectPath;
    }

    public void setBaseProjectPath(String baseProjectPath) {
        this.baseProjectPath = baseProjectPath;
    }

    public String getRpcProjectPath() {
        return rpcProjectPath;
    }

    public void setRpcProjectPath(String rpcProjectPath) {
        this.rpcProjectPath = rpcProjectPath;
    }

    public String getHttpProjectPath() {
        return httpProjectPath;
    }

    public void setHttpProjectPath(String httpProjectPath) {
        this.httpProjectPath = httpProjectPath;
    }

    public String getSysProjectPath() {
        return sysProjectPath;
    }

    public void setSysProjectPath(String sysProjectPath) {
        this.sysProjectPath = sysProjectPath;
    }

    public String getApiProjectPath() {
        return apiProjectPath;
    }

    public void setApiProjectPath(String apiProjectPath) {
        this.apiProjectPath = apiProjectPath;
    }

    public String getTemplatesPath() {
        return templatesPath;
    }

    public void setTemplatesPath(String templatesPath) {
        this.templatesPath = templatesPath;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public SysConfig getSysConfig() {
        return sysConfig;
    }

    public void setSysConfig(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
    }
}