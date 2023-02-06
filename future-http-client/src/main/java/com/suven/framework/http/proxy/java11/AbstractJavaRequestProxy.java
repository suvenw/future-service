package com.suven.framework.http.proxy.java11;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;
import okhttp3.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public abstract class AbstractJavaRequestProxy extends AbstractHttpProxy implements HttpRequestProxy<JavaRequestBuilder> {

    public static final MediaType CONTENT_TYPE_JSON = MediaType.get(HttpClientConstants.CONTENT_TYPE_JSON);
    protected final HttpClient.Builder httpClientBuilder;

    public AbstractJavaRequestProxy(HttpClientConfig httpClientConfig) {
       super(httpClientConfig);
        this.httpClientBuilder = HttpClient.newBuilder();
    }
    public AbstractJavaRequestProxy(HttpClientConfig httpClientConfig, HttpClient.Builder httpClientBuilder) {
        super(httpClientConfig);
        this.httpClientBuilder = httpClientBuilder;
    }

    @Override
    public HttpClientResponse execute(JavaRequestBuilder httpRequestBuilder) {
        HttpRequest request =  httpRequestBuilder.getRequest();
        try {
            HttpClient client;
            if (this.isProxy()) {
                client = httpClientBuilder.connectTimeout(Duration.ofMillis(this.getTimeout()))
                        .proxy(new DefaultProxySelector(httpClientConfig)).build();
            } else {
                client = httpClientBuilder.connectTimeout(Duration.ofMillis(this.getTimeout())).build();
            }
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            int code = httpResponse.statusCode();
            boolean successful = isSuccess(httpResponse);
            Map<String, List<String>> headers = httpResponse.headers().map();
            String body = httpResponse.body();
            return  HttpClientResponse.build(successful, code, headers, body, null);
        } catch (Exception e) {
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }
    @Override
    public HttpClientResponse executeAsync(JavaRequestBuilder httpRequestBuilder, FutureCallbackProxy futureProxy) {
        return executeAsync(httpRequestBuilder,futureProxy,true);
    }
    @Override
    public HttpClientResponse executeAsync(JavaRequestBuilder httpRequestBuilder, FutureCallbackProxy futureProxy,boolean isGetResult) {
        HttpRequest  request =  httpRequestBuilder.getRequest();
        try {
            HttpClient client;

            if (this.isProxy()) {
                client = httpClientBuilder.connectTimeout(Duration.ofMillis(this.getTimeout()))
                        .proxy(new DefaultProxySelector(httpClientConfig)).build();
            } else {
                client = httpClientBuilder.connectTimeout(Duration.ofMillis(this.getTimeout())).build();
            }
            Future<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            if(!isGetResult){
                return null;
            }

            HttpResponse<String> httpResponse = future.get(this.getTimeout(), TimeUnit.MILLISECONDS);

            int code = httpResponse.statusCode();
            boolean successful = isSuccess(httpResponse);
            Map<String, List<String>> headers = httpResponse.headers().map();
            String body = httpResponse.body();
            return  HttpClientResponse.build(successful, code, headers, body, null);

        } catch (Exception e) {
            e.printStackTrace();
            return HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    @Override
    public JavaRequestBuilder getRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {

        String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
        String reqUrl = baseUrl + HttpParamsUtil.parseMapToString(params, encode);

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().uri(URI.create(reqUrl)).GET()
                .timeout(Duration.ofMillis(this.getTimeout()));

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), requestBuilder::header);
        }
        HttpRequest request = requestBuilder.build();
        JavaRequestBuilder requestBean = new JavaRequestBuilder(request);

        return requestBean;
    }


    @Override
    public JavaRequestBuilder postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {
         String requestUrl = url;
        if (HttpParamsUtil.isNotEmpty(params)) {
            String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
            requestUrl = baseUrl + HttpParamsUtil.parseMapToString(params, encode);
        }
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl)).timeout(Duration.ofMillis(httpClientConfig.getTimeout()));

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), builder::header);
        }
        builder.POST(HttpRequest.BodyPublishers.noBody());
        HttpRequest request  = builder.build();
        JavaRequestBuilder requestProxy = new JavaRequestBuilder(request);
        return requestProxy;

    }

    @Override
    public JavaRequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header, boolean encode )  {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url)).timeout(Duration.ofMillis(httpClientConfig.getTimeout()));

        if (HttpParamsUtil.isNotEmpty(jsonData)) {
            if(encode){
                jsonData = HttpParamsUtil.urlEncode(jsonData);
            }
            builder.POST(HttpRequest.BodyPublishers.ofString(jsonData, HttpClientConstants.DEFAULT_ENCODING));
            builder.header(HttpClientConstants.CONTENT_ENCODING, HttpClientConstants.DEFAULT_ENCODING.displayName());
            builder.header(HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON);
            builder.header(HttpClientConstants.CONTENT_JSON_KEY, HttpClientConstants.CONTENT_JSON_VALUE);
        } else {
            builder.POST(HttpRequest.BodyPublishers.noBody());
        }

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), builder::header);
        }
        HttpRequest request  = builder.build();
        JavaRequestBuilder requestProxy = new JavaRequestBuilder(request);
        return requestProxy;


    }

    /**
     * 添加request header
     *
     */
    protected Headers initHeaders(){
        Headers headers = Headers.of(HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA);
        return headers;
    }

    protected boolean isSuccess(HttpResponse<String> response) {
        if (response == null) {
            return false;
        }
        return response.statusCode() >= 200 && response.statusCode() < 300;
    }

}
