package com.suven.framework.http.proxy.httpclient;


import com.suven.framework.http.proxy.HttpRequestBuilder;
import org.apache.http.client.methods.HttpRequestBase;

public class ApacheRequestBuilder implements HttpRequestBuilder<HttpRequestBase> {

    private HttpRequestBase request;
    public ApacheRequestBuilder(HttpRequestBase request){
        this.request = request;
    }

    @Override
    public HttpRequestBase getRequest() {
        return request;
    }
}
