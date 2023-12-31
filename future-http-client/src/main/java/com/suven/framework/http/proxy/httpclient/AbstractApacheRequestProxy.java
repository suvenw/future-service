package com.suven.framework.http.proxy.httpclient;

import cn.hutool.core.codec.Base64Encoder;
import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public abstract class AbstractApacheRequestProxy extends AbstractHttpProxy implements HttpRequestProxy<ApacheRequestBuilder> {

    private CloseableHttpClient httpClient;
    private CloseableHttpAsyncClient asyncClient;

    public AbstractApacheRequestProxy() {
        super(new HttpClientConfig());
        this.httpClient = HttpClients.createDefault();
        this.asyncClient = HttpAsyncClients.createDefault();
        this.asyncClient.start();
    }

    public AbstractApacheRequestProxy(HttpClientConfig httpConfig) {
        super(httpConfig);
        this.httpClient = HttpClients.createDefault();
        this.asyncClient = HttpAsyncClients.createDefault();
        this.asyncClient.start();
    }

    public AbstractApacheRequestProxy(HttpClientConfig httpConfig, CloseableHttpClient httpClient,CloseableHttpAsyncClient asyncClient) {
        super(httpConfig);
        this.httpClient = httpClient;
        this.asyncClient = asyncClient;
        this.asyncClient.start();
    }

    private void initHttpClient(){
            this.httpClient = HttpSSLCipherSuiteUtil.createHttpClient();
            this.asyncClient =  HttpSSLCipherSuiteUtil.createHttpAsyncClient();
            asyncClient.start();
    }

    /**
     *  根据请求参数类型,根据网络架构的返回数据结果,转换到统一规范对象HttpClientResponse
     * @param bodyMediaType ,根据请求参数类型, 0/1为 json 字符串,2.为文件流
     * @param httpResponse 网络架构的返回数据结果
     * @return
     * @throws IOException
     */
    @Override
    public HttpClientResponse getHttpClientResponse(int bodyMediaType, Object httpResponse) throws IOException {
        CloseableHttpResponse response = (CloseableHttpResponse)httpResponse;
        int code = response.getStatusLine().getStatusCode();
        boolean successful = isSuccess(response);
        Map<String, List<String>> headers = Arrays.stream(response.getAllHeaders())
                .collect(Collectors.toMap(Header::getName, (value) -> {
                    ArrayList<String> headerValue = new ArrayList<>();
                    headerValue.add(value.getValue());
                    return headerValue;
                }, (oldValue, newValue) -> newValue));
        if (null == response.getEntity()) {
            return HttpClientResponse.build(successful, code, headers, "", null);
        }
        BodyMediaTypeEnum bodyMediaTypeEnum = BodyMediaTypeEnum.code(bodyMediaType);
        String body = "";
            switch (bodyMediaTypeEnum) {
                case BODY_JSON:
                case BODY_JSON_STRING:
                    body = EntityUtils.toString(response.getEntity(), HttpClientConstants.DEFAULT_ENCODING);
                    return HttpClientResponse.build(successful, code, headers, body, null);
                case BODY_BYTES:
                    String content = EntityUtils.toString(response.getEntity(), HttpClientConstants.DEFAULT_ENCODING);
                    body = Base64Encoder.encode(content);
                    return HttpClientResponse.build(successful, code, headers, body, null);
                case BODY_FILE:
                    body = IOUtils.toString(response.getEntity().getContent(), HttpClientConstants.DEFAULT_ENCODING);
                    return HttpClientResponse.build(successful, code, headers, body, null);
                default:
        }
        return HttpClientResponse.build(successful, code, headers, "", null);
    }

    @Override
    public HttpClientResponse execute(ApacheRequestBuilder httpRequestBuilder) {
        HttpRequestBase request =  httpRequestBuilder.getRequest();
        HttpProxyRequest httpProxyRequest = httpRequestBuilder.getHttpProxyRequest();
        // 设置超时时长
        RequestConfig.Builder configBuilder = RequestConfig.custom()
                .setConnectTimeout(httpProxyRequest.getTimeout())
                .setSocketTimeout(httpProxyRequest.getTimeout())
                .setConnectionRequestTimeout(httpProxyRequest.getTimeout());
        // 设置代理
        if (httpProxyRequest.isProxy()) {
            Proxy proxy = this.getProxy();
            InetSocketAddress address = (InetSocketAddress) proxy.address();
            HttpHost host = new HttpHost(address.getHostName(), address.getPort(), proxy.type().name().toLowerCase());
            configBuilder.setProxy(host);
        }if (httpProxyRequest.isHttps()){
            initHttpClient();
        }

        request.setConfig(configBuilder.build());

        try (CloseableHttpResponse response = this.httpClient.execute(request)) {
            HttpClientResponse result =  getHttpClientResponse(httpProxyRequest.getBodyMediaType(),response);
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }



    @Override
    public HttpClientResponse executeAsync(ApacheRequestBuilder httpRequestBuilder, FutureCallbackProxy futureProxy) {
        HttpRequestBase  request =  httpRequestBuilder.getRequest();
        HttpProxyRequest httpProxyRequest = httpRequestBuilder.getHttpProxyRequest();
        try {
            // 设置超时时长
            RequestConfig.Builder configBuilder = RequestConfig.custom()
                    .setConnectTimeout(httpProxyRequest.getTimeout())
                    .setSocketTimeout(httpProxyRequest.getTimeout())
                    .setConnectionRequestTimeout(httpProxyRequest.getTimeout());



            // 设置代理
            if (httpProxyRequest.isProxy()) {
                Proxy proxy = this.getProxy();
                InetSocketAddress address = (InetSocketAddress) proxy.address();
                HttpHost host = new HttpHost(address.getHostName(), address.getPort(), proxy.type().name().toLowerCase());
                configBuilder.setProxy(host);
            }else if(httpProxyRequest.isHttps()){

            }

            request.setConfig(configBuilder.build());

            ApacheFutureCallback futureCallback = (ApacheFutureCallback)futureProxy.getFutureCallbackProxy();
            Future<HttpResponse> future = this.asyncClient.execute(request, futureCallback);
            if(!httpProxyRequest.isFutureResult()){
                return null;
            }
            //获取线程结果
            HttpResponse response = futureCallback.getFuture().get(httpProxyRequest.getTimeout(), TimeUnit.MILLISECONDS);
            futureProxy.isSuccess(response);

            HttpClientResponse result = futureProxy.getResult(httpProxyRequest.getBodyMediaType());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    @Override
    public ApacheRequestBuilder getRequest(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest)  {
        this.initHttpProxyRequest(httpProxyRequest);
        String requestUrl = url;
        if (HttpParamsUtil.isNotEmpty(params)) {
            String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
            requestUrl = baseUrl + HttpParamsUtil.parseMapToString(params,httpProxyRequest.isEncode());
        }

        HttpGet request = new HttpGet(requestUrl);
        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::addHeader);
        }else {
            addHeader(request);
        }
        ApacheRequestBuilder requestBean = new ApacheRequestBuilder(request,httpProxyRequest);

        return requestBean;
    }


    @Override
    public ApacheRequestBuilder postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest)  {
        HttpPost request = new HttpPost(url);
        this.initHttpProxyRequest(httpProxyRequest);
        if (HttpParamsUtil.isNotEmpty(params)) {
            List<NameValuePair> form = new ArrayList<>();
            HttpParamsUtil.forFunction(params, (k, v) -> form.add(new BasicNameValuePair(k, v)));
            request.setEntity(new UrlEncodedFormEntity(form, HttpClientConstants.DEFAULT_ENCODING));
        }

        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::addHeader);
        }else {
            addHeader(request);
        }
        ApacheRequestBuilder requestProxy = new ApacheRequestBuilder(request,httpProxyRequest);
        return requestProxy;

    }

    @Override
    public ApacheRequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header,  HttpProxyRequest httpProxyRequest )  {
        HttpPost request = new HttpPost(url);
        this.initHttpProxyRequest(httpProxyRequest);

        if (HttpParamsUtil.isNotEmpty(jsonData)) {
            StringEntity entity = new StringEntity(jsonData, HttpClientConstants.DEFAULT_ENCODING);
            entity.setContentEncoding(HttpClientConstants.DEFAULT_ENCODING.displayName());
            entity.setContentType(HttpClientConstants.CONTENT_TYPE_JSON);
            request.setEntity(entity);
        }
        initJsonHeaders(header);
        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::addHeader);
        }else {
            addHeader(request);
        }

        ApacheRequestBuilder requestProxy = new ApacheRequestBuilder(request,httpProxyRequest);
        return requestProxy;


    }


    /**
     * 添加request header
     *
     * @param request HttpRequestBase
     */
    protected void addHeader(HttpRequestBase request) {
        String ua = HttpClientConstants.USER_AGENT;
        Header[] headers = request.getHeaders(ua);
        if (null == headers || headers.length == 0) {
            request.addHeader(ua, HttpClientConstants.USER_AGENT_DATA);
        }
    }


    protected boolean isSuccess(HttpResponse response) {
        if (response == null) {
            return false;
        }
        if (response.getStatusLine() == null) {
            return false;
        }
        return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
    }



}
