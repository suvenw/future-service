package com.suven.framework.http.proxy.httpclient;

import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;
import com.suven.framework.http.proxy.FutureCallbackProxy;
import com.suven.framework.http.proxy.HttpClientResponse;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;


public class ApacheFutureCallback   implements FutureCallback<HttpResponse> , FutureCallbackProxy<HttpResponse,ApacheFutureCallback> {
    private HttpResponse httpResponse;
    final CompletableFuture<HttpResponse> future = new CompletableFuture<>();


    public static ApacheFutureCallback build(){
        return new ApacheFutureCallback();
    }

    @Override
    public HttpResponse getFutureResponse() {
        return httpResponse;
    }

    @Override
    public ApacheFutureCallback getFutureCallbackProxy() {
        return this;
    }

    @Override
    public Future<HttpResponse> getFuture() {
        return  future;
    }

    @Override
    public HttpClientResponse getResult() {
        HttpResponse httpResponse = getFutureResponse();
        int code = this.getStatusCode(httpResponse);
        try {
            boolean successful = this.isSuccess(httpResponse);
            Map<String, List<String>> headers = getHeaders(httpResponse);
            String body = this.getBody(httpResponse);
            HttpClientResponse result = successfulResponse(successful,code,headers,body);
            return result;

        }catch (Exception e){
            InputStream error = getContent(httpResponse);
            this.createHttpException(code,error);
            return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
        }
    }


    @Override
    public int getStatusCode(HttpResponse response) {
        if(null == response){
            return HttpClientConstants.SC_BAD_REQUEST;
        }
        int statusCode =  response.getStatusLine().getStatusCode();
        return statusCode;
    }

    @Override
    public boolean isSuccess(HttpResponse response) {
        this.httpResponse = response;
        if (response == null) {
            return false;
        }
        if (response.getStatusLine() == null) {
            return false;
        }
        return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
    }



    @Override
    public Map<String, List<String>> getHeaders(HttpResponse httpResponse) {
        Map<String, List<String>> headers = Arrays.stream(httpResponse.getAllHeaders())
                .collect(Collectors.toMap(Header::getName, (value) -> {
                    ArrayList<String> headerValue = new ArrayList<>();
                    headerValue.add(value.getValue());
                    return headerValue;
                }, (oldValue, newValue) -> newValue));
        return headers;
    }

    @Override
    public String getBody(HttpResponse httpResponse) throws Exception{
        StringBuffer body = new StringBuffer();
        if (httpResponse.getEntity() != null) {
            body.append(EntityUtils.toString(httpResponse.getEntity(), HttpClientConstants.DEFAULT_ENCODING));
        }
        return body.toString();
    }

    public InputStream getContent(HttpResponse response) {
        if(null == response){
            return null;
        }
        try {
            InputStream is = response.getEntity().getContent();
            return is;
        }catch (Exception e){
            throw new HttpClientRuntimeException(e);
        }

    }
    /** FutureCallback 相关的业务接口 **/
    @Override
    public void completed(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
        future.complete(httpResponse);
    }

    @Override
    public void cancelled() {
        future.cancel(false);
    }

    @Override
    public void failed(Exception ex) {
        future.completeExceptionally(ex);
    }
}
