package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.proxy.HttpRequestBuilder;

import okhttp3.Request;

public class Okhttp3RequestBuilder implements HttpRequestBuilder<Request> {

    private Request request;
    public Okhttp3RequestBuilder(Request request){
        this.request = request;
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
