package com.suven.framework.http.proxy.hutool;


import cn.hutool.http.HttpRequest;
import com.suven.framework.http.proxy.HttpRequestBuilder;

public class HutoolRequestBuilder implements HttpRequestBuilder<HttpRequest> {

    private HttpRequest request;
    public HutoolRequestBuilder(HttpRequest request){
        this.request = request;
    }

    @Override
    public HttpRequest getRequest() {
        return request;
    }
}
