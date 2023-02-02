

package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.proxy.HttpProxyHeader;
import com.suven.framework.http.proxy.HttpClientResponse;
import okhttp3.*;

import java.util.Map;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  OkHttp3 网络请求配置类
 *  默认代理选择器
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class OkHttp3HttpClientProxy extends AbstractOkHttp3RequestProxy {

	public OkHttp3HttpClientProxy() {
		this(new HttpClientConfig());
	}

	public OkHttp3HttpClientProxy(HttpClientConfig httpClientConfig) {
		super(httpClientConfig);
	}

	public OkHttp3HttpClientProxy(HttpClientConfig httpConfig,OkHttpClient.Builder httpClientBuilder ) {
		super(httpConfig,httpClientBuilder);
	}



	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url) {
		Okhttp3RequestBuilder request = getRequest(url,null,null,true);
		HttpClientResponse response = execute(request);
		return response;
	}




	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url, Map<String, String> params, boolean encode) {
		Okhttp3RequestBuilder request = getRequest(url,params,null,encode);
		HttpClientResponse response = execute(request);
		return response;

	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		Okhttp3RequestBuilder request = this.getRequest(url,params,header,encode);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url) {
		Okhttp3RequestBuilder request = this.postFormRequest(url,null,null,true);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param jsonData JSON 参数
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String jsonData) {
		Okhttp3RequestBuilder request = this.postJsonRequest(url,jsonData,null,true);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param jsonData   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String jsonData, HttpProxyHeader header) {
		Okhttp3RequestBuilder request = this.postJsonRequest(url,jsonData,header,true);
		HttpClientResponse response = this.execute(request);
		return response;

	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, Map<String, String> params, boolean encode) {
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,null,encode);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,header,encode);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	@Override
	public HttpClientResponse getAsync(String url) {
		OkHttp3FutureProxy future =  OkHttp3FutureProxy.build();
		Okhttp3RequestBuilder request = this.getRequest(url,null,null,true);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;

	}

	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params) {
		OkHttp3FutureProxy future =  OkHttp3FutureProxy.build();
		Okhttp3RequestBuilder request = this.getRequest(url,params,null,true);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		OkHttp3FutureProxy future =  OkHttp3FutureProxy.build();
		Okhttp3RequestBuilder request = this.getRequest(url,params,header,encode);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url) {
		OkHttp3FutureProxy future =  OkHttp3FutureProxy.build();
		Okhttp3RequestBuilder request = this.postFormRequest(url,null,null,true);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, String jsonData) {
		OkHttp3FutureProxy future =  OkHttp3FutureProxy.build();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,jsonData,null,true);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, String jsonData, HttpProxyHeader header) {
		OkHttp3FutureProxy future =  OkHttp3FutureProxy.build();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,jsonData,header,true);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params) {
		OkHttp3FutureProxy future =  OkHttp3FutureProxy.build();
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,null,true);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {

		OkHttp3FutureProxy future =  OkHttp3FutureProxy.build();
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,header,encode);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;

	}




}
