package com.suven.framework.http.client;

import com.suven.framework.http.util.HttpParamsUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2023-02-03
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  提供给业务方便使用的api
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.66os.com
 **/


public class HttpParseRequestParams {

    /** 对象请求实现 **/
    public static HttpRequestParams getHttpRequestParams(Object body,String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestApiParam(null,false,body,false,md5Key);
        return httpRequestParams;
    }

    public static HttpRequestParams getHttpRequestParams(Object body,boolean decode,String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestApiParam(null,false,body,decode,md5Key);
        return httpRequestParams;
    }
    public static HttpRequestParams getHttpRequestParams(Object headers,Object body,boolean decode,String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestApiParam(headers,false,body,decode,md5Key);
        return httpRequestParams;
    }


    //json or url
    public static HttpRequestParams getHttpRequestString(String  body,String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(null,false,body,true,true,md5Key);
        return httpRequestParams;
    }
    public static HttpRequestParams getHttpRequestString(String  body,boolean isUrlNotJson, String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(null,false,body,isUrlNotJson,true,md5Key);
        return httpRequestParams;
    }
    public static HttpRequestParams getHttpRequestString(String  body,boolean isUrlNotJson, boolean decode,String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(null,false,body,isUrlNotJson,decode,md5Key);
        return httpRequestParams;
    }
    public static HttpRequestParams getHttpRequestString(Object headers,String  body,boolean decode,String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(headers,false,body,true,decode,md5Key);
        return httpRequestParams;
    }
    public static HttpRequestParams getHttpRequestString(Object headers,boolean headersIsSignParam,String  body,boolean decode,String md5Key)  {
        HttpRequestParams httpRequestParams  = getHttpRequestStringParam(headers,false,body,true,decode,md5Key);
        return httpRequestParams;
    }

    private static HttpRequestParams getHttpRequestApiParam(Object headers,boolean headersIsSignParam,Object body,boolean decode,String md5Key)  {
        //1.请求参数对象转换排序的map树
        Map<String, String> dataMap = new TreeMap<>();
        Map<String, String> headMap = null;
        Map<String, String> bodyMap = null;
        if(null != headers){
            headMap = HttpParamsUtil.toMap(headers,decode);
            if(headersIsSignParam && headMap != null && !headMap.isEmpty() ){
                dataMap.putAll(headMap);
            }
        }
        if(null != body){
            bodyMap = HttpParamsUtil.toMap(body,decode);
            if(bodyMap != null && !bodyMap.isEmpty()){
                dataMap.putAll(bodyMap);
            }
        }
        //2.请求的map 树转换url get请求规范的字符串
        String urlParam = HttpParamsUtil.getSortedMapSign(dataMap, Arrays.asList("cliSign"));
        String signParam = HttpParamsUtil.paramMd5length(urlParam,md5Key,8,24);

        HttpRequestParams httpRequestParams = HttpRequestParams.build(bodyMap,headMap,decode,urlParam,signParam);
        return httpRequestParams;
    }

    private static HttpRequestParams getHttpRequestStringParam(Object head,boolean headersIsSignParam,String body, boolean isUrlNotJson, boolean decode,String md5Key)  {
        //1.请求参数对象转换排序的map树
        Map<String, String> dataMap = new TreeMap<>();
        Map<String, String> headMap = null;
        Map<String, String> bodyMap = null;

        if(null != head){
            headMap = HttpParamsUtil.toMap(head,decode);
            if(headersIsSignParam && headMap != null && !headMap.isEmpty()){
                dataMap.putAll(headMap);
            }
        }
        if(null != body){
            bodyMap = HttpParamsUtil.toMapByString(body,decode,isUrlNotJson);
            if(bodyMap != null && !bodyMap.isEmpty()){
                dataMap.putAll(bodyMap);
            }
        }
        //2.请求的map 树转换url get请求规范的字符串
        String urlParam = HttpParamsUtil.getSortedMapSign(dataMap, Arrays.asList("cliSign"));
        String signParam = HttpParamsUtil.paramMd5length(urlParam,md5Key,8,24);
        HttpRequestParams httpRequestParams = HttpRequestParams.build(bodyMap,headMap,decode,urlParam,signParam);
        return httpRequestParams;
    }

}
