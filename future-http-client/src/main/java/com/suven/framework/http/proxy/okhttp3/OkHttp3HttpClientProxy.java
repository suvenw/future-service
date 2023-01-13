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

package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.AbstractHttpProxy;
import com.suven.framework.http.proxy.FutureCallbackProxy;
import com.suven.framework.http.proxy.HttpProxyHeader;
import com.suven.framework.http.proxy.HttpClientResponse;
import com.suven.framework.http.util.HttpParamsUtil;
import okhttp3.*;

import java.time.Duration;
import java.util.List;
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
public class OkHttp3HttpClientProxy extends AbstractHttpProxy {
	public static final MediaType CONTENT_TYPE_JSON = MediaType.get(HttpClientConstants.CONTENT_TYPE_JSON);
	private final OkHttpClient.Builder httpClientBuilder;

	public OkHttp3HttpClientProxy() {
		this(new HttpClientConfig());
	}

	public OkHttp3HttpClientProxy(HttpClientConfig httpConfig) {
		this(new OkHttpClient().newBuilder(), httpConfig);
	}

	public OkHttp3HttpClientProxy(OkHttpClient.Builder httpClientBuilder, HttpClientConfig httpConfig) {
		super(httpConfig);
		this.httpClientBuilder = httpClientBuilder;
	}

	private HttpClientResponse exec(Request.Builder requestBuilder) {
		this.addHeader(requestBuilder);
		Request request = requestBuilder.build();

		OkHttpClient httpClient;
		// 设置代理
		if (null != httpClientConfig.getProxy()) {
			httpClient = httpClientBuilder.connectTimeout(Duration
							.ofMillis(this.getTimeout()))
					.writeTimeout(Duration.ofMillis(this.getTimeout()))
					.readTimeout(Duration.ofMillis(this.getTimeout()))
					.proxy(this.getProxy()).build();
		} else {
			httpClient = httpClientBuilder
					.connectTimeout(Duration.ofMillis(this.getTimeout()))
					.writeTimeout(Duration.ofMillis(this.getTimeout()))
					.readTimeout(Duration.ofMillis(this.getTimeout())).build();
		}

		try (Response response = httpClient.newCall(request).execute()) {

			int code = response.code();
			boolean successful = response.isSuccessful();
			Map<String, List<String>> headers = response.headers().toMultimap();
			ResponseBody responseBody = response.body();
			String body = null == responseBody ? null : responseBody.string();
			return HttpClientResponse.build(successful, code, headers, body, null);
		} catch (Exception e) {
			e.printStackTrace();
			return HttpClientResponse.build(false, 500, null, null, e.getMessage());
		}
	}

	private HttpClientResponse execAsync(Request.Builder requestBuilder,FutureCallbackProxy future) {
		this.addHeader(requestBuilder);
		Request request = requestBuilder.build();

		OkHttpClient httpClient;
		// 设置代理
		if (null != httpClientConfig.getProxy()) {
			httpClient = httpClientBuilder.connectTimeout(Duration
							.ofMillis(this.getTimeout()))
					.writeTimeout(Duration.ofMillis(this.getTimeout()))
					.readTimeout(Duration.ofMillis(this.getTimeout()))
					.proxy(this.getProxy()).build();
		} else {
			httpClient = httpClientBuilder
					.connectTimeout(Duration.ofMillis(this.getTimeout()))
					.writeTimeout(Duration.ofMillis(this.getTimeout()))
					.readTimeout(Duration.ofMillis(this.getTimeout())).build();
		}

		try  {
			Call call = httpClient.newCall(request);
//			OkHttp3FutureProxy futureProxy =  OkHttp3FutureProxy.build();
			OkHttp3FutureProxy futureProxy  = (OkHttp3FutureProxy) future;
			OkHttp3FutureCallback callback = futureProxy.getFutureCallbackProxy();
			call.enqueue(callback);
			HttpClientResponse  response= futureProxy.get();
			return response;
		} catch (Exception e) {
			e.printStackTrace();
			return HttpClientResponse.build(false, 500, null, null, e.getMessage());
		}
	}
	/**
	 * 添加request header
	 *
	 * @param builder Request.Builder
	 */
	private void addHeader(Request.Builder builder) {
		builder.header(HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA);
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
		HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
		if (encode) {
			HttpParamsUtil.forEach(params, urlBuilder::addEncodedQueryParameter);
		} else {
			HttpParamsUtil.forEach(params, urlBuilder::addQueryParameter);
		}
		HttpUrl httpUrl = urlBuilder.build();

		Request.Builder requestBuilder = new Request.Builder().url(httpUrl);
		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
		}
		requestBuilder = requestBuilder.get();

		return exec(requestBuilder);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url) {
		return this.post(url, HttpClientConstants.EMPTY);
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
		if (HttpParamsUtil.isEmpty(data)) {
			data = HttpClientConstants.EMPTY;
		}
		RequestBody body = RequestBody.create(data, CONTENT_TYPE_JSON);

		Request.Builder requestBuilder = new Request.Builder().url(url);
		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
		}
		requestBuilder = requestBuilder.post(body);

		return exec(requestBuilder);
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
		FormBody.Builder formBuilder = new FormBody.Builder();
		if (encode) {
			HttpParamsUtil.forEach(params, formBuilder::addEncoded);
		} else {
			HttpParamsUtil.forEach(params, formBuilder::add);
		}
		FormBody body = formBuilder.build();

		Request.Builder requestBuilder = new Request.Builder().url(url);
		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), requestBuilder::addHeader);
		}
		requestBuilder = requestBuilder.post(body);

		return exec(requestBuilder);
	}

	@Override
	public HttpClientResponse getAsync(String url) {
		return null;
	}

	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params) {
		return null;
	}

	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		return null;
	}

	@Override
	public HttpClientResponse postAsync(String url) {
		return null;
	}

	@Override
	public HttpClientResponse postAsync(String url, String data) {
		return null;
	}

	@Override
	public HttpClientResponse postAsync(String url, String data, HttpProxyHeader header) {
		return null;
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params) {
		return null;
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		return null;
	}
}
