package com.suven.framework.http.client;


import java.util.Map;
import java.util.TreeMap;

/**
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2023-02-03
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  api请求参数转换后的请求参数对象
 *  String url, Map<String, String> params, HttpProxyHeader header, boolean encode
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.66os.com
 **/


public class HttpRequestParams {

    private Map<String, String> params;
    private Map<String, String> headers;
    private boolean encode;
    private  String urlParam;
    private String signParam;

    public HttpRequestParams() {
    }
    public static HttpRequestParams build(Map<String, String> params,String urlParam) {
        return build(params,null,true,urlParam,null);
    }
    public static HttpRequestParams build( Map<String, String> params,Map<String, String> headers) {
        return build(params,headers,true,null,null);
    }
    public static HttpRequestParams build( Map<String, String> params, Map<String, String> headers,boolean encode,
                                           String urlParam,String signParam ) {
        HttpRequestParams request = new HttpRequestParams();
        request.urlParam = urlParam;
        request.params = params;
        request.headers = headers;
        request.encode = encode;
        request.signParam = signParam;
        return request;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public Map<String, String> getParams() {
        return params;
    }
    public Map<String, String> getParamsSign() {
        if(null == params){
            params = new TreeMap<>();
        }params.put("cliSign",this.signParam);
        return params;
    }


    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public boolean isEncode() {
        return encode;
    }

    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    public String getSignParam() {
        return signParam;
    }

    public void setSignParam(String signParam) {
        this.signParam = signParam;
    }
}
