package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.exception.HttpClientRuntimeException;
import com.suven.framework.http.proxy.FutureCallbackProxy;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.Future;

public class OkHttp3FutureCallback implements Callback, FutureCallbackProxy<Response,Callback> {


    protected final Future future;
    public OkHttp3FutureCallback(Future future) {
        this.future = future;
    }

    @Override
    public void completed(Response result) {

    }

    @Override
    public Future getFuture() {
        return future;
    }

    public void failed(Exception ex) {
        throw new HttpClientRuntimeException(ex);
    }


    public void cancelled() {
        future.cancel(false);
    }

    @Override
    public Callback getFutureCallbackProxy() {
        return this;
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException ex) {
        throw new HttpClientRuntimeException(ex);
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        completed(response);
    }
}
