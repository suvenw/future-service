/*
 * Copyright (c) 2019-2029, xkcoding & Yangkai.Shen & 沈扬凯 (237497819@qq.com & xkcoding.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.suven.framework.http.proxy.httpclient;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.AbstractHttpProxy;
import com.suven.framework.http.proxy.FutureCallbackProxy;
import com.suven.framework.http.proxy.HttpProxyHeader;
import com.suven.framework.http.proxy.HttpClientResponse;
import com.suven.framework.http.util.HttpParamsUtil;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  apache http 网络请求实现
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class ApacheHttpClientProxy extends AbstractHttpProxy {
	private final CloseableHttpClient httpClient;
	private final CloseableHttpAsyncClient asyncClient;

	public ApacheHttpClientProxy() {
		this(HttpClients.createDefault(),  HttpAsyncClients.createDefault(), new HttpClientConfig());
	}

	public ApacheHttpClientProxy(HttpClientConfig httpConfig) {
		this(HttpClients.createDefault(), HttpAsyncClients.createDefault(), httpConfig);
	}

	public ApacheHttpClientProxy(CloseableHttpClient httpClient,CloseableHttpAsyncClient asyncClient, HttpClientConfig httpConfig) {
		super(httpConfig);
		this.httpClient = httpClient;
		this.asyncClient = asyncClient;
	}

	/**
	 * 同步执行的方法;
	 * @param request
	 * @return
	 */
	protected HttpClientResponse exec(HttpRequestBase request) {
		this.addHeader(request);
		// 设置超时时长
		RequestConfig.Builder configBuilder = RequestConfig.custom()
				.setConnectTimeout(this.getTimeout())
				.setSocketTimeout(this.getTimeout())
				.setConnectionRequestTimeout(this.getTimeout());
		// 设置代理
		if (isProxy()) {
			Proxy proxy = this.getProxy();
			InetSocketAddress address = (InetSocketAddress) proxy.address();
			HttpHost host = new HttpHost(address.getHostName(), address.getPort(), proxy.type().name().toLowerCase());
			configBuilder.setProxy(host);
		}

		request.setConfig(configBuilder.build());

		try (CloseableHttpResponse response = this.httpClient.execute(request)) {

			StringBuffer body = new StringBuffer();
			if (response.getEntity() != null) {
				body.append(EntityUtils.toString(response.getEntity(), HttpClientConstants.DEFAULT_ENCODING));
			}

			int code = response.getStatusLine().getStatusCode();
			boolean successful = isSuccess(response);
			Map<String, List<String>> headers = Arrays.stream(response.getAllHeaders())
					.collect(Collectors.toMap(Header::getName, (value) -> {
				ArrayList<String> headerValue = new ArrayList<>();
				headerValue.add(value.getValue());
				return headerValue;
			}, (oldValue, newValue) -> newValue));
			return  HttpClientResponse.build(successful, code, headers, body.toString(), null);
		} catch (Exception e) {
			e.printStackTrace();
			return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
		}
	}

	/**
	 * 异步执行的方法
	 * @param request
	 * @param future
	 */
	protected void execAsync(HttpRequestBase request,FutureCallbackProxy future)  {
		this.addHeader(request);
		// 设置超时时长
		RequestConfig.Builder configBuilder = RequestConfig.custom()
				.setConnectTimeout(this.getTimeout())
				.setSocketTimeout(this.getTimeout())
				.setConnectionRequestTimeout(this.getTimeout());
		// 设置代理
		if (isProxy()) {
			Proxy proxy = this.getProxy();
			InetSocketAddress address = (InetSocketAddress) proxy.address();
			HttpHost host = new HttpHost(address.getHostName(), address.getPort(), proxy.type().name().toLowerCase());
			configBuilder.setProxy(host);
		}

		request.setConfig(configBuilder.build());
		if(null  == future){

		}
		if (future instanceof ApacheFutureCallback){
			ApacheFutureCallback callback = (ApacheFutureCallback)future;
			FutureCallback futureCallback = callback.getFutureCallbackProxy();
			this.asyncClient.execute(request, futureCallback);
		}


	}

	/**
	 * 添加request header
	 *
	 * @param request HttpRequestBase
	 */
	private void addHeader(HttpRequestBase request) {
		String ua = HttpClientConstants.USER_AGENT;
		Header[] headers = request.getHeaders(ua);
		if (null == headers || headers.length == 0) {
			request.addHeader(ua, HttpClientConstants.USER_AGENT_DATA);
		}
	}

	private boolean isSuccess(HttpResponse response) {
		if (response == null) {
			return false;
		}
		if (response.getStatusLine() == null) {
			return false;
		}
		return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url) {
		return this.get(url, null, false);
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
		return this.get(url, params, null, encode);
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
		String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
		url = baseUrl + HttpParamsUtil.parseMapToString(params, encode);

		HttpGet httpGet = new HttpGet(url);

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), httpGet::addHeader);
		}

		return exec(httpGet);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url) {
		HttpPost httpPost = new HttpPost(url);
		return this.exec(httpPost);
	}

	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String data) {
		return this.post(url, data, null);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String data, HttpProxyHeader header) {
		HttpPost httpPost = new HttpPost(url);

		if (HttpParamsUtil.isNotEmpty(data)) {
			StringEntity entity = new StringEntity(data, HttpClientConstants.DEFAULT_ENCODING);
			entity.setContentEncoding(HttpClientConstants.DEFAULT_ENCODING.displayName());
			entity.setContentType(HttpClientConstants.CONTENT_TYPE_JSON);
			httpPost.setEntity(entity);
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), httpPost::addHeader);
		}

		return this.exec(httpPost);
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
		return this.post(url, params, null, encode);
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
		HttpPost httpPost = new HttpPost(url);

		if (HttpParamsUtil.isNotEmpty(params)) {
			List<NameValuePair> form = new ArrayList<>();
			HttpParamsUtil.forEach(params, (k, v) -> form.add(new BasicNameValuePair(k, v)));
			httpPost.setEntity(new UrlEncodedFormEntity(form, HttpClientConstants.DEFAULT_ENCODING));
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), httpPost::addHeader);
		}

		return this.exec(httpPost);
	}

	@Override
	public HttpClientResponse getAsync(String url) {
		return postAsync(url,null,null,true);
	}


	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params) {
		return postAsync(url,params,null,true);
	}


	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		ApacheHttpFutureProxy future =  ApacheHttpFutureProxy.build();
		postAsync(future,url,params,header,true);
		HttpClientResponse result = future.get(getTimeout(), TimeUnit.MILLISECONDS);
		return result;
	}



	@Override
	public HttpClientResponse postAsync(String url) {
		ApacheHttpFutureProxy future =  ApacheHttpFutureProxy.build();
		postAsync(future,url,null,null);
		HttpClientResponse result = future.get(getTimeout(), TimeUnit.MILLISECONDS);
		return result;
	}



	@Override
	public HttpClientResponse postAsync(String url, String params) {
		ApacheHttpFutureProxy future =  ApacheHttpFutureProxy.build();
		postAsync(future,url,params,null);
		HttpClientResponse result = future.get(getTimeout(), TimeUnit.MILLISECONDS);
		return result;
	}


	@Override
	public HttpClientResponse postAsync(String url, String params, HttpProxyHeader header) {
		ApacheHttpFutureProxy future =  ApacheHttpFutureProxy.build();
		postAsync(future,url,params,header);
		HttpClientResponse result = future.get(getTimeout(), TimeUnit.MILLISECONDS);
		return result;
	}



	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params) {
		return postAsync(url,params,null,true);
	}



	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		ApacheHttpFutureProxy future =  ApacheHttpFutureProxy.build();
		postAsync(future,url,params,header,encode);
		HttpClientResponse result = future.get(getTimeout(), TimeUnit.MILLISECONDS);
		return result;
	}


//	@Override
	public void getAsync(FutureCallbackProxy future,  String url )  {
		postAsync(future,url,null,null,true);
	}

//	@Override
	public void getAsync(FutureCallbackProxy future,  String url, Map<String, String> params )  {
		postAsync(future, url,params,null,true);
	}


//	@Override
	public void getAsync(FutureCallbackProxy future,  String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {
		String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
		url = baseUrl + HttpParamsUtil.parseMapToString(params, encode);

		HttpGet httpGet = new HttpGet(url);

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), httpGet::addHeader);
		}
		execAsync(httpGet,future);
	}

//	@Override
	public void postAsync(FutureCallbackProxy future,String url )  {
		postAsync(future,url,null,null);
	}


//	@Override
	public void postAsync(FutureCallbackProxy future,String url, String params )  {
		postAsync(future,url,params,null);
	}


//	@Override
	public void postAsync(FutureCallbackProxy future,String url, String params, HttpProxyHeader header )  {
		HttpPost httpPost = new HttpPost(url);

		if (HttpParamsUtil.isNotEmpty(params)) {
			StringEntity entity = new StringEntity(params, HttpClientConstants.DEFAULT_ENCODING);
			entity.setContentEncoding(HttpClientConstants.DEFAULT_ENCODING.displayName());
			entity.setContentType(HttpClientConstants.CONTENT_TYPE_JSON);
			httpPost.setEntity(entity);
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), httpPost::addHeader);
		}
		this.execAsync(httpPost,future);
	}
//	@Override
	public void postAsync(FutureCallbackProxy future,String url, Map<String, String> params )  {
		postAsync(future,url,params,null,true);
	}
//	@Override
	public void postAsync(FutureCallbackProxy future,String url, Map<String, String> params, HttpProxyHeader header, boolean encode )  {
		HttpPost httpPost = new HttpPost(url);

		if (HttpParamsUtil.isNotEmpty(params)) {
			List<NameValuePair> form = new ArrayList<>();
			HttpParamsUtil.forEach(params, (k, v) -> form.add(new BasicNameValuePair(k, v)));
			httpPost.setEntity(new UrlEncodedFormEntity(form, HttpClientConstants.DEFAULT_ENCODING));
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), httpPost::addHeader);
		}

		this.execAsync(httpPost,future);
	}


}
