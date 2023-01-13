/*
 * Copyright (c) 2019-2029, Dreamlu 卢春梦 (596392912@qq.com & www.dreamlu.net).
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

package com.suven.framework.http.proxy.java11;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.*;
import com.suven.framework.http.util.HttpParamsUtil;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): java11 http 网络请求配置类
 *  默认代理选择器
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/
public  class JavaHttpClientProxy extends AbstractHttpProxy {
	private final HttpClient.Builder clientBuilder;

	public JavaHttpClientProxy() {
		this(new HttpClientConfig());
	}

	public JavaHttpClientProxy(HttpClientConfig httpConfig) {
		this(HttpClient.newBuilder(), httpConfig);
	}

	public JavaHttpClientProxy(HttpClient.Builder clientBuilder, HttpClientConfig httpConfig) {
		super(httpConfig);
		this.clientBuilder = clientBuilder;
	}

	/**
	 * HTTP Client  异步发送请求使用示例
	 * */
//	public static void t1() throws ExecutionException, InterruptedException {
//		HttpClient client = HttpClient.newHttpClient();
//		HttpRequest request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:8080/test/hello")).build();
//		HttpResponse.BodyHandler<String> responseBodyHandler = HttpResponse.BodyHandlers.ofString();
//		CompletableFuture<HttpResponse<String>> sendAsync = client.sendAsync(request, responseBodyHandler);
//		HttpResponse<String> response = sendAsync.get();
//		String body = response.body();
//		System.out.println(body);
//	}

	private HttpClientResponse exec(HttpRequest.Builder builder) {
		this.addHeader(builder);
		try {
			HttpClient client;

			if (this.isProxy()) {
				client = clientBuilder.connectTimeout(Duration.ofMillis(this.getTimeout()))
						.proxy(new DefaultProxySelector(httpClientConfig)).build();
			} else {
				client = clientBuilder.connectTimeout(Duration.ofMillis(this.getTimeout())).build();
			}
			HttpRequest request = builder.build();
			HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

			int code = httpResponse.statusCode();
			boolean successful = isSuccess(httpResponse);
			Map<String, List<String>> headers = httpResponse.headers().map();
			String body = httpResponse.body();
			return  HttpClientResponse.build(successful, code, headers, body, null);
		} catch (Exception e) {
			e.printStackTrace();
			return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
		}
	}


	private HttpClientResponse execAsync(HttpRequest.Builder builder) {
		this.addHeader(builder);
		try {
			HttpClient client;

			if (this.isProxy()) {
				client = clientBuilder.connectTimeout(Duration.ofMillis(this.getTimeout()))
						.proxy(new DefaultProxySelector(httpClientConfig)).build();
			} else {
				client = clientBuilder.connectTimeout(Duration.ofMillis(this.getTimeout())).build();
			}
			HttpRequest request = builder.build();

			Future<HttpResponse<String>> future = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

			HttpResponse<String> httpResponse = future.get();

			int code = httpResponse.statusCode();
			boolean successful = isSuccess(httpResponse);
			Map<String, List<String>> headers = httpResponse.headers().map();
			String body = httpResponse.body();
			return  HttpClientResponse.build(successful, code, headers, body, null);
		} catch (Exception e) {
			e.printStackTrace();
			return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
		}
	}

	private boolean isSuccess(HttpResponse<String> response) {
		if (response == null) {
			return false;
		}
		return response.statusCode() >= 200 && response.statusCode() < 300;
	}

	/**
	 * 添加request header
	 *
	 * @param builder HttpRequest.Builder
	 */
	private void addHeader(HttpRequest.Builder builder) {
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
		String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
		String reqUrl = baseUrl + HttpParamsUtil.parseMapToString(params, encode);

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(reqUrl)).GET()
				.timeout(Duration.ofMillis(this.getTimeout()));

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), builder::header);
		}

		return exec(builder);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url) {
		return this.post(url, null);
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
		HttpRequest.Builder builder = HttpRequest.newBuilder()
				.uri(URI.create(url)).timeout(Duration.ofMillis(httpClientConfig.getTimeout()));

		if (HttpParamsUtil.isNotEmpty(data)) {
			builder.POST(HttpRequest.BodyPublishers.ofString(data, HttpClientConstants.DEFAULT_ENCODING));
			builder.header(HttpClientConstants.CONTENT_ENCODING, HttpClientConstants.DEFAULT_ENCODING.displayName());
			builder.header(HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON);
		} else {
			builder.POST(HttpRequest.BodyPublishers.noBody());
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), builder::header);
		}

		return this.exec(builder);
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
		String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
		String reqUrl = baseUrl + HttpParamsUtil.parseMapToString(params, encode);
		return this.post(reqUrl, null, header);
	}

	@Override
	public HttpClientResponse getAsync(String url) {
		return getAsync(url,null,null,true);
	}

	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params) {
		return getAsync(url,params,null,true);
	}

	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
		String reqUrl = baseUrl + HttpParamsUtil.parseMapToString(params, encode);

		HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(reqUrl)).GET()
				.timeout(Duration.ofMillis(this.getTimeout()));

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), builder::header);
		}
		return execAsync(builder);
	}

	@Override
	public HttpClientResponse postAsync(String url) {
		return postAsync(url,null,null);
	}

	@Override
	public HttpClientResponse postAsync(String url, String data) {
		return postAsync(url,data,null);
	}

	@Override
	public HttpClientResponse postAsync(String url, String data, HttpProxyHeader header) {

		HttpRequest.Builder builder = HttpRequest.newBuilder()
				.uri(URI.create(url)).timeout(Duration.ofMillis(httpClientConfig.getTimeout()));

		if (HttpParamsUtil.isNotEmpty(data)) {
			builder.POST(HttpRequest.BodyPublishers.ofString(data, HttpClientConstants.DEFAULT_ENCODING));
			builder.header(HttpClientConstants.CONTENT_ENCODING, HttpClientConstants.DEFAULT_ENCODING.displayName());
			builder.header(HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON);
		} else {
			builder.POST(HttpRequest.BodyPublishers.noBody());
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), builder::header);
		}

		return this.execAsync(builder);
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params) {
		return this.postAsync(url, null, null,true);
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		String baseUrl = HttpParamsUtil.appendIfNotContain(url, "?", "&");
		String reqUrl = baseUrl + HttpParamsUtil.parseMapToString(params, encode);
		return this.postAsync(reqUrl, null, header);
	}
}
