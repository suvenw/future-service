package com.suven.framework.http.proxy.java11;

import com.suven.framework.http.proxy.HttpRequestBuilder;

import java.net.http.HttpRequest;

public class JavaRequestBuilder implements HttpRequestBuilder<HttpRequest> {

    private HttpRequest request;
    public JavaRequestBuilder(HttpRequest request){
        this.request = request;
    }

    @Override
    public HttpRequest getRequest() {
        return request;
    }
}
