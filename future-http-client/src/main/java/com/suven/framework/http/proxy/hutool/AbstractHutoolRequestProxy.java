package com.suven.framework.http.proxy.hutool;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract class AbstractHutoolRequestProxy extends AbstractHttpProxy implements HttpRequestProxy<HutoolRequestBuilder> {


    public AbstractHutoolRequestProxy(HttpClientConfig httpClientConfig) {
       super(httpClientConfig);
    }

    private HttpClientResponse getHttpClientResponse(int bodyMediaType, HttpResponse response ) throws IOException {
        int code = response.getStatus();
        boolean successful = response.isOk();
        Map<String, List<String>> headers = response.headers();

        if (null == response.body()) {
            return HttpClientResponse.build(successful, code, headers, "", null);
        }
        BodyMediaTypeEnum bodyMediaTypeEnum = BodyMediaTypeEnum.code(bodyMediaType);
        String body = "";
        switch (bodyMediaTypeEnum) {
            case BODY_JSON:
            case BODY_JSON_STRING:
                body =  response.body();
                return HttpClientResponse.build(successful, code, headers, body, null);
            case BODY_BYTES:
                body = Base64Encoder.encode(response.bodyBytes());
                return HttpClientResponse.build(successful, code, headers, body, null);
            case BODY_FILE:

                body =  IOUtils.toString( response.bodyStream(), HttpClientConstants.DEFAULT_ENCODING);
                return HttpClientResponse.build(successful, code, headers, body, null);
            default:
                return HttpClientResponse.build(successful, code, headers, body, null);
        }
    }



    private HttpClientResponse execute(HutoolRequestBuilder httpRequestBuilder, boolean isAsync) {
        // 设置超时时长
        HttpRequest request = httpRequestBuilder.getRequest();
        HttpProxyRequest proxyRequest = httpRequestBuilder.getHttpProxyRequest();
        request = request.timeout(proxyRequest.getTimeout());
        // 设置代理
        if (proxyRequest.isProxy()) {
            request = request.setProxy(this.getProxy());
        }

        try {
            HttpResponse response = null;
            if(isAsync){
                response = request.executeAsync();
                if(!proxyRequest.isFutureResult()){
                    return null;
                }

            }else {
                response = request.execute();
            };

            HttpClientResponse result = this.getHttpClientResponse(proxyRequest.getBodyMediaType(),response);
            return  result;
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
    public HutoolRequestBuilder getRequest(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest proxyRequest )  {
        this.initHttpProxyRequest(proxyRequest);
        String requestUrl = url;
        if (HttpParamsUtil.isNotEmpty(params)) {
            String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
            requestUrl = baseUrl + HttpParamsUtil.parseMapToString(params,proxyRequest.isEncode());
        }
        HttpRequest request = HttpRequest.get(requestUrl);

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request,proxyRequest);

        return requestBuilder;
    }


    @Override
    public HutoolRequestBuilder postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest proxyRequest  )  {
        HttpRequest request = HttpRequest.post(url);
        this.initHttpProxyRequest(proxyRequest);
        if (proxyRequest.isEncode()) {
            HttpParamsUtil.forFunction(params, (k, v) -> request.form(k, HttpParamsUtil.urlEncode(v)));
        } else {
            HttpParamsUtil.forFunction(params, request::form);
        }

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request,proxyRequest);
        return requestBuilder;

    }

    @Override
    public HutoolRequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header, HttpProxyRequest  proxyRequest )  {
        HttpRequest request = HttpRequest.post(url);

        if (HttpParamsUtil.isNotEmpty(jsonData)) {
            request.body(jsonData);
        }
        initJsonHeaders(header);
        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::header);
        }
        HutoolRequestBuilder requestBuilder = new HutoolRequestBuilder(request,proxyRequest);
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
