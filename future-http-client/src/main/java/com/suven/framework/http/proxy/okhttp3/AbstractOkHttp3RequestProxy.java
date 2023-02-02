package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;
import okhttp3.*;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AbstractOkHttp3RequestProxy  extends AbstractHttpProxy implements HttpRequestProxy<Okhttp3RequestBuilder> {

    public static final MediaType CONTENT_TYPE_JSON = MediaType.get(HttpClientConstants.CONTENT_TYPE_JSON);
    protected final OkHttpClient.Builder httpClientBuilder ;

    public AbstractOkHttp3RequestProxy(HttpClientConfig httpClientConfig) {
       super(httpClientConfig);
        this.httpClientBuilder = new OkHttpClient().newBuilder();
    }
    public AbstractOkHttp3RequestProxy(HttpClientConfig httpClientConfig,OkHttpClient.Builder httpClientBuilder) {
        super(httpClientConfig);
        this.httpClientBuilder = httpClientBuilder;
    }

    @Override
    public HttpClientResponse execute(Okhttp3RequestBuilder httpRequestBuilder) {
        Request  request =  httpRequestBuilder.getRequest();
        OkHttpClient httpClient;
        // 设置代理
        if (null != httpClientConfig.getProxy()) {
            httpClient = httpClientBuilder.connectTimeout(Duration
                            .ofMillis(this.getTimeout()))
                    .writeTimeout(Duration.ofMillis(this.getTimeout()))
                    .readTimeout(Duration.ofMillis(this.getTimeout()))
                    .proxy(this.getProxy()).build();
        } else {
            httpClient = httpClientBuilder
                    .connectTimeout(Duration.ofMillis(this.getTimeout()))
                    .writeTimeout(Duration.ofMillis(this.getTimeout()))
                    .readTimeout(Duration.ofMillis(this.getTimeout())).build();
        }

        try (Response response = httpClient.newCall(request).execute()) {

            int code = response.code();
            boolean successful = response.isSuccessful();
            Map<String, List<String>> headers = response.headers().toMultimap();
            ResponseBody responseBody = response.body();
            String body = null == responseBody ? null : responseBody.string();
            return HttpClientResponse.build(successful, code, headers, body, null);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    @Override
    public HttpClientResponse executeAsync(Okhttp3RequestBuilder httpRequestBuilder, FutureCallbackProxy future) {
        OkHttpClient httpClient;
        Request  request =  httpRequestBuilder.getRequest();
        // 设置代理
        if (null != httpClientConfig.getProxy()) {
            httpClient = httpClientBuilder.connectTimeout(Duration
                            .ofMillis(this.getTimeout()))
                    .writeTimeout(Duration.ofMillis(this.getTimeout()))
                    .readTimeout(Duration.ofMillis(this.getTimeout()))
                    .proxy(this.getProxy()).build();
        } else {
            httpClient = httpClientBuilder
                    .connectTimeout(Duration.ofMillis(this.getTimeout()))
                    .writeTimeout(Duration.ofMillis(this.getTimeout()))
                    .readTimeout(Duration.ofMillis(this.getTimeout())).build();
        }

        try  {
            OkHttp3FutureProxy futureProxy  = (OkHttp3FutureProxy) future;
            OkHttp3FutureCallback callback = futureProxy.getFutureCallbackProxy();
            Call call = httpClient.newCall(request);
            call.enqueue(callback);
            HttpClientResponse  response = futureProxy.get(this.getTimeout(), TimeUnit.MILLISECONDS);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    @Override
    public Okhttp3RequestBuilder getRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {


        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        if (encode) {
            HttpParamsUtil.forFunction(params, urlBuilder::addEncodedQueryParameter);
        } else {
            HttpParamsUtil.forFunction(params, urlBuilder::addQueryParameter);
        }
        //url
        HttpUrl httpUrl = urlBuilder.build();
        Headers headers = initHeaders();
        if (header != null ){
            headers = Headers.of(header.getHeaders());
        }
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(httpUrl).headers(headers).get();
        Request request = requestBuilder.build();
        Okhttp3RequestBuilder requestProxy = new Okhttp3RequestBuilder(request);
        return requestProxy;
    }


    @Override
    public Okhttp3RequestBuilder postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (encode) {
            HttpParamsUtil.forFunction(params, formBuilder::addEncoded);
        } else {
            HttpParamsUtil.forFunction(params, formBuilder::add);
        }
        FormBody body = formBuilder.build();
        //Headers
        Headers headers = initHeaders();
        if (header != null ){
            headers = Headers.of(header.getHeaders());
        }

        //Request
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url).headers(headers).post(body);
        Request request = requestBuilder.build();

        Okhttp3RequestBuilder requestProxy = new Okhttp3RequestBuilder(request);
        return requestProxy;
    }

    @Override
    public Okhttp3RequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header, boolean encode )  {
        if (HttpParamsUtil.isEmpty(jsonData)) {
            jsonData = HttpClientConstants.EMPTY;
        }

        if(encode){
            jsonData = HttpParamsUtil.urlEncode(jsonData);
        }
        RequestBody body = RequestBody.create(jsonData, CONTENT_TYPE_JSON);
        Headers headers = initHeaders();
        if (header != null ){
            headers = Headers.of(header.getHeaders());
        }
        //Request
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(url).headers(headers).post(body);
        Request request = requestBuilder.build();

        Okhttp3RequestBuilder requestProxy = new Okhttp3RequestBuilder(request);
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
}
