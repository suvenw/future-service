package com.suven.framework.http.data.vo;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ClassUtils;

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
 * @Copyright: (c) 2021 gc by https://www.sixeco.com
 **/

public interface IResponseResult<T> {


    /*** 返回指定规范对象 **/
    int code();
    String message();
    boolean success();


    /**
     *  失败时返回对象
     * @param code 失败错误码
     * @param message 失败错误提示内容信息
     * @return
     */
    IResponseResult buildResultVo(int code, String message);

    /**
     * 返回自定义对象,
     * 默认成功返回的结果数据
     * @param success
     * @param code
     * @param message
     * @param result
     * @return
     */
    IResponseResult buildResultVo(boolean success, int code, String message, T result);


    /**
     * 默认成功返回的结果数据
     * @param result 返回的数据对象
     * @return
     */
    default IResponseResult buildResultVo( T result){
        return buildResultVo(true,0,"成功",result);
    }

    /**
     * 成功返回的结果数据,success默认值为true
     * @param code 返回的编码
     * @param message 返回的提示内容信息
     * @param result 返回的结果对象信息;
     * @return
     */
    default IResponseResult buildResultVo(int code, String message,T result){
        return buildResultVo(true,code,message,result);
    }
    /**
     * 成功返回的结果数据,ResponseResultVo对象 success默认值为true
     * @return
     */
    default  ResponseResultVo buildResultVo(){
        return ResponseResultVo.build();
    }


    default Object initData(Object data) {
        if(data == null){
            return null;
        }
        if( data instanceof String ){
            return (T)data;
        }
        if(data != null && !ClassUtils.isPrimitiveOrWrapper(data.getClass())){
            return data;
        }
        if(data instanceof  Boolean){
            JSONObject object = new JSONObject();
            int value = (Boolean)data ? 1 : 0;
            object.put("result",value);
            return object;
        }else {
            JSONObject object = new JSONObject();
            object.put("pkId",data);
            return object;
        }
    }

}
