package com.suven.framework.http.proxy;


import java.util.concurrent.Future;

public interface FutureCallbackProxy<R,FC> {

    FC getFutureCallbackProxy();
     void completed(R result);
    Future getFuture();
}
