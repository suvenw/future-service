package com.suven.framework.http.proxy.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;

import java.util.List;
import java.util.Map;

public abstract class AbstractHutoolRequestProxy extends AbstractHttpProxy implements HttpRequestProxy<HutoolRequestBuilder> {


    public AbstractHutoolRequestProxy(HttpClientConfig httpClientConfig) {
       super(httpClientConfig);
    }

    private HttpClientResponse execute(HutoolRequestBuilder httpRequestBuilder, boolean isAsync) {
        // 设置超时时长
        HttpRequest request = httpRequestBuilder.getRequest();
        request = request.timeout(this.getTimeout());
        // 设置代理
        if (isProxy()) {
            request = request.setProxy(this.getProxy());
        }

        try {
            HttpResponse response = null;
            if(isAsync){
                response = request.executeAsync();
            }else {
                response = request.execute();
            };
            int code = response.getStatus();
            boolean successful = response.isOk();
            String body = response.body();
            Map<String, List<String>> headers = response.headers();
            return  HttpClientResponse.build(successful, code, headers, body, null);
        } catch (Exception e) {
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    @Override
    public HttpClientResponse execute(HutoolRequestBuilder httpRequestBuilder) {
        HttpClientResponse response =  this.execute(httpRequestBuilder,false);
        return response;
    }

    @Override
    public HttpClientResponse executeAsync(HutoolRequestBuilder httpRequestBuilder, FutureCallbackProxy futureProxy) {
        HttpClientResponse response =  this.execute(httpRequestBuilder,true);
        return  response;
    }

    public HttpClientResponse executeAsync(HutoolRequestBuilder httpRequestBuilder) {
        HttpClientResponse response =  this.executeAsync(httpRequestBuilder,null);
        return  response;
    }

    @Override
    public HutoolRequestBuilder getRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {

        String requestUrl = url;
        if (HttpParamsUtil.isNotEmpty(params)) {
            String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
            requestUrl = baseUrl + HttpParamsUtil.parseMapToString(params, encode);
        }
        HttpRequest request = HttpRequest.get(requestUrl);

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request);

        return requestBuilder;
    }


    @Override
    public HutoolRequestBuilder postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {
        HttpRequest request = HttpRequest.post(url);

        if (encode) {
            HttpParamsUtil.forFunction(params, (k, v) -> request.form(k, HttpParamsUtil.urlEncode(v)));
        } else {
            HttpParamsUtil.forFunction(params, request::form);
        }

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request);
        return requestBuilder;

    }

    @Override
    public HutoolRequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header, boolean encode )  {
        HttpRequest request = HttpRequest.post(url);

        if (HttpParamsUtil.isNotEmpty(jsonData)) {
            request.body(jsonData);
        }

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request);
        return requestBuilder;


    }



    protected boolean isSuccess(HttpResponse response) {
        if (response == null) {
            return false;
        }
        boolean successful = response.isOk();
        return successful;
    }


}
