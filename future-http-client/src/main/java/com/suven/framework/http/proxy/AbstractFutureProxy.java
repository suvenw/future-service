package com.suven.framework.http.proxy;

import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  网络请求异步返回实现类,T为各框架的返回对象
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/

public abstract class AbstractFutureProxy<R,FC> implements FutureCallbackProxy<R,FC> {

    /** 统一通过的接收业务处理线程**/
    protected final CompletableFuture<HttpClientResponse> future;
    protected final FutureCallbackProxy futureCallbackProxy;


    public AbstractFutureProxy(CompletableFuture<HttpClientResponse> future, FutureCallbackProxy futureCallbackProxy){
        this.future =  future;
        this.futureCallbackProxy = futureCallbackProxy;
    }



    public CompletableFuture getFuture(){
        return future;
    }
//    public FutureCallbackProxy getFutureCallbackProxy(){
//        return futureCallbackProxy;
//    }

    public HttpClientResponse get() throws InterruptedException, ExecutionException {
        return   future.get();
    }
    public HttpClientResponse get(long timeout, TimeUnit unit) {
        try {
            return   future.get(timeout,unit);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public  void completed(R response) {
        HttpClientResponse result =  completeResult(response);
        future.complete(result);
   }
    public boolean isDone(){
        return future.isDone();
    }


    /**
     * 通过proxy统一转换成统一的返回结果实现类
     * 结果转换实现,不同的网络架构返回的结果对象不一致,通过代码实现转换
     * @param response
     * @return
     */
    public abstract HttpClientResponse completeResult(R response);

    /**
     * 通过proxy统一转换成统一的返回结果实现类
     * 结果是否成功标识,各个不同的网络架构返回的结果,和验证功能的依据不一样,
     * @param response
     * @return
     */
    public abstract boolean isSuccess(R response) ;
    /**
     * 结果是否成功标识,各个不同的网络架构返回的结果,和验证功能的依据不一样,通过proxy统一转换成统一的返回结果实现类
     * @param response
     * @return
     */
    public  abstract int getStatusCode(R response) ;
    /**
     * 结果是否成功标识,各个不同的网络架构返回的结果,和验证功能的依据不一样,通过proxy统一转换成统一的返回结果实现类
     * @param response
     * @return
     */
    public abstract InputStream getContent(R response) ;

    /**
     * 返回统一正常结果实现
     * @param successful
     * @param code
     * @param headers
     * @param body
     * @return
     */
    protected HttpClientResponse successfulResponse( boolean successful, int code ,
                                                     Map<String, List<String>> headers, String body){
        return  HttpClientResponse.build(successful, code, headers, body, null);
    }


    /**
     * 返回统一异常结果实现
     * @param exception
     * @return
     */
    protected HttpClientResponse errorResponse(Exception exception){
        return  HttpClientResponse.build(false, 500, null, null, exception.getMessage());
    }


    public void failed(Exception ex) {
          throw new HttpClientRuntimeException(ex);
    }


    public void cancelled() {
       future.cancel(false);
    }



    protected boolean completeExceptionally(R response) {
        if(null == response){
            return false;
        }
        HttpClientRuntimeException httpClientRuntimeException =  createHttpException(response);
        future.completeExceptionally(httpClientRuntimeException);
        return true;
    }
    /**}
     * Create the appropriate exception for the given HTTP response.
     *
     * @param response HTTP response
     * @return {@link HttpClientRuntimeException} instance
     */
    protected HttpClientRuntimeException createHttpException(R response) {
        int statusCode = this.getStatusCode(response);
        if (statusCode == HttpClientConstants.SC_UNAUTHORIZED) {
            return new HttpClientRuntimeException(
                    "Salt user does not have sufficient permissions");
        }
        else {
            String content = "";
            try {
                content = new BufferedReader(new InputStreamReader(getContent(response)))
                        .lines().parallel().collect(Collectors.joining("\n"));
            }
            catch (Exception e) {
                // error trying to get the response body, nothing to do...
            }
            return new HttpClientRuntimeException("Response code: " + statusCode + ". Response body:\n" + content);
        }
    }






}
