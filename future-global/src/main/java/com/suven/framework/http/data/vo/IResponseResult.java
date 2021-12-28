package com.suven.framework.http.data.vo;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-06-29
 * @WeeK 星期: 星期二
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  返回前端结构实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.com
 **/

public interface IResponseResult {


//    /**获取错误码 **/
//    int getCode();
//    /**获取错误码对应描述 **/
//    String getMsg();
//    /**接口返回数据对象 **/
//    Object getData();


    /** 返回自定义对象**/
    ResponseResultVo buildResponseResultVo();

}
