package com.suven.framework.http.proxy;



import com.suven.framework.http.proxy.okhttp3.Okhttp3RequestBuilder;
import org.apache.http.HttpResponse;

import java.util.Map;

public interface HttpRequestProxy <T extends HttpRequestBuilder>{

    /**
     *  同步执行逻辑请求的方法实现
     * @param httpRequestBuilder
     * @return
     */
    HttpClientResponse execute(T httpRequestBuilder) ;

    /**
     *  异步执行逻辑请求的方法实现,默认是读取返回结果
     * @param httpRequestBuilder 代码各业务的请求的Request
     * @param future 返回处理的异步线程
     * @return
     */
    HttpClientResponse executeAsync(T httpRequestBuilder, FutureCallbackProxy future);

    /**
     *  异步执行逻辑请求的方法实现
     * @param httpRequestBuilder 代码各业务的请求的Request
     * @param future 返回处理的异步线程
     * param isGetResult  true 或false ,true时异步读取结果,false不读取线程结果,HttpClientResponse为null
     * @return
     */
    HttpClientResponse executeAsync(T httpRequestBuilder, FutureCallbackProxy future, boolean isGetResult);
    /**
     * GET 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
     * @param url 请求url
     * @param params 请求参数 Map的k-v集合
     * @param header 请求头部参数
     * @param encode 是否需要encode转码,true 或 false, 默认为true
     * @return HttpRequestBuilder 代码请求对象
     */
    T getRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode );



    /**
     * POST 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
     * @param url 请求url
     * @param params 请求参数 Map的k-v集合
     * @param header 请求头部参数
     * @param encode 是否需要encode转码,true 或 false, 默认为true
     * @return HttpRequestBuilder 代码请求对象
     */
    T postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode );

    /**
     * POST 方式 提交表单实现的业务,转换请求执行的逻辑的实现方法,返回各业务的请求对像的代理对象HttpRequestBuilder
     * @param url 请求url
     * @param jsonData 请求参数 JSON 格式数据
     * @param header 请求头部参数
     * @param encode 是否需要encode转码,true 或 false, 默认为true
     * @return HttpRequestBuilder 代码请求对象
     */
    T  postJsonRequest(String url, String jsonData, HttpProxyHeader header, boolean encode );
}
