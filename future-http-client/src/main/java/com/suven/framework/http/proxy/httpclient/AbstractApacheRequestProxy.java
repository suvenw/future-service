package com.suven.framework.http.proxy.httpclient;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
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


    @Override
    public HttpClientResponse execute(ApacheRequestBuilder httpRequestBuilder) {
        HttpRequestBase request =  httpRequestBuilder.getRequest();
        // 设置超时时长
        RequestConfig.Builder configBuilder = RequestConfig.custom()
                .setConnectTimeout(this.getTimeout())
                .setSocketTimeout(this.getTimeout())
                .setConnectionRequestTimeout(this.getTimeout());
        // 设置代理
        if (isProxy()) {
            Proxy proxy = this.getProxy();
            InetSocketAddress address = (InetSocketAddress) proxy.address();
            HttpHost host = new HttpHost(address.getHostName(), address.getPort(), proxy.type().name().toLowerCase());
            configBuilder.setProxy(host);
        }if (isHttps()){
            initHttpClient();
        }

        request.setConfig(configBuilder.build());

        try (CloseableHttpResponse response = this.httpClient.execute(request)) {

            StringBuffer body = new StringBuffer();
            if (response.getEntity() != null) {
                body.append(EntityUtils.toString(response.getEntity(), HttpClientConstants.DEFAULT_ENCODING));
            }

            int code = response.getStatusLine().getStatusCode();
            boolean successful = isSuccess(response);
            Map<String, List<String>> headers = Arrays.stream(response.getAllHeaders())
                    .collect(Collectors.toMap(Header::getName, (value) -> {
                        ArrayList<String> headerValue = new ArrayList<>();
                        headerValue.add(value.getValue());
                        return headerValue;
                    }, (oldValue, newValue) -> newValue));
            return  HttpClientResponse.build(successful, code, headers, body.toString(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    @Override
    public HttpClientResponse executeAsync(ApacheRequestBuilder httpRequestBuilder, FutureCallbackProxy future) {
        return executeAsync(httpRequestBuilder,future,true);
    }

    @Override
    public HttpClientResponse executeAsync(ApacheRequestBuilder httpRequestBuilder, FutureCallbackProxy futureProxy, boolean isGetResult) {
        HttpRequestBase  request =  httpRequestBuilder.getRequest();
        try {
            // 设置超时时长
            RequestConfig.Builder configBuilder = RequestConfig.custom()
                    .setConnectTimeout(this.getTimeout())
                    .setSocketTimeout(this.getTimeout())
                    .setConnectionRequestTimeout(this.getTimeout());



            // 设置代理
            if (isProxy()) {
                Proxy proxy = this.getProxy();
                InetSocketAddress address = (InetSocketAddress) proxy.address();
                HttpHost host = new HttpHost(address.getHostName(), address.getPort(), proxy.type().name().toLowerCase());
                configBuilder.setProxy(host);
            }else if(isHttps()){

            }

            request.setConfig(configBuilder.build());

            ApacheFutureCallback futureCallback = (ApacheFutureCallback)futureProxy.getFutureCallbackProxy();
            Future<HttpResponse> future = this.asyncClient.execute(request, futureCallback);
            if(!isGetResult){
                return null;
            }
            //获取线程结果
            HttpResponse response = futureCallback.getFuture().get(getTimeout(), TimeUnit.MILLISECONDS);
            futureProxy.isSuccess(response);

            HttpClientResponse result = futureProxy.getResult();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }

    @Override
    public ApacheRequestBuilder getRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {

        String requestUrl = url;
        if (HttpParamsUtil.isNotEmpty(params)) {
            String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
            requestUrl = baseUrl + HttpParamsUtil.parseMapToString(params, encode);
        }

        HttpGet request = new HttpGet(requestUrl);
        if (header != null) {
            HttpParamsUtil.forFunction(header.getHeaders(), request::addHeader);
        }else {
            addHeader(request);
        }
        ApacheRequestBuilder requestBean = new ApacheRequestBuilder(request);

        return requestBean;
    }


    @Override
    public ApacheRequestBuilder postFormRequest(String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {
        HttpPost request = new HttpPost(url);

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
        ApacheRequestBuilder requestProxy = new ApacheRequestBuilder(request);
        return requestProxy;

    }

    @Override
    public ApacheRequestBuilder postJsonRequest(String url, String jsonData, HttpProxyHeader header, boolean encode )  {
        HttpPost request = new HttpPost(url);

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

        ApacheRequestBuilder requestProxy = new ApacheRequestBuilder(request);
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
