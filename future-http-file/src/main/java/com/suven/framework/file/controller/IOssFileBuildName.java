package com.suven.framework.file.controller;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-05-11
 * @WeeK 星期: 星期二
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  生成阿里oss文件名称的实现方法接口
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.66os.com
 **/

public interface IOssFileBuildName {

    /** 生成阿里oss文件名称的实现方法接口 **/
    String getOssFileName();

    /** 生成阿里oss文件名称的实现方法接口 **/
    String getOssFileName(String prefixKey);
}
