package com.suven.framework.http.proxy.httpclient;

import com.suven.framework.http.exception.HttpClientRuntimeException;
import com.suven.framework.http.proxy.FutureCallbackProxy;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.util.concurrent.Future;

public class ApacheFutureCallback implements FutureCallback<HttpResponse>, FutureCallbackProxy<HttpResponse,FutureCallback> {


    protected final Future future;
    public ApacheFutureCallback(Future future) {
        this.future = future;
    }

    @Override
    public void completed(HttpResponse result) {

    }

    @Override
    public Future getFuture() {
        return future;
    }

    @Override
    public void failed(Exception ex) {
        throw new HttpClientRuntimeException(ex);
    }


    public void cancelled() {
        future.cancel(false);
    }

    @Override
    public FutureCallback getFutureCallbackProxy() {
        return this;
    }
}
