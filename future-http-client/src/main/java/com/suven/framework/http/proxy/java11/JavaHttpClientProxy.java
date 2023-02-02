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
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;
import java.util.Map;
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
public  class JavaHttpClientProxy extends AbstractJavaRequestProxy {

	public JavaHttpClientProxy() {
		super(new HttpClientConfig());
	}
	public JavaHttpClientProxy(HttpClientConfig httpConfig) {
		super( httpConfig);
	}

	public JavaHttpClientProxy( HttpClientConfig httpConfig,HttpClient.Builder clientBuilder) {
		super(httpConfig,clientBuilder);
	}


	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url) {
		JavaRequestBuilder request = this.getRequest(url,null,null,true);
		HttpClientResponse response = this.execute(request);
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
		JavaRequestBuilder request = this.getRequest(url,params,null,encode);
		HttpClientResponse response = this.execute(request);
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
		JavaRequestBuilder request = this.getRequest(url,params,header,encode);
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
		JavaRequestBuilder request = this.postFormRequest(url,null,null,true);
		HttpClientResponse response = this.execute(request);
		return response;
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
		JavaRequestBuilder request = this.postJsonRequest(url,null,null,true);
		HttpClientResponse response = this.execute(request);
		return response;
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
		JavaRequestBuilder request = this.postJsonRequest(url,data,header,true);
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
		JavaRequestBuilder request = this.postFormRequest(url,params,null,encode);
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
		JavaRequestBuilder request = this.postFormRequest(url,params,header,encode);
		HttpClientResponse response = this.execute(request);
		return response;

	}

	@Override
	public HttpClientResponse getAsync(String url) {
		JavaRequestBuilder request = this.getRequest(url,null,null,true);
		HttpClientResponse response = this.executeAsync(request,null);
		return response;

	}

	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params) {
		JavaRequestBuilder request = this.getRequest(url,params,null,true);
		HttpClientResponse response = this.executeAsync(request,null);
		return response;
	}

	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		JavaRequestBuilder request = this.getRequest(url,params,header,true);
		HttpClientResponse response = this.executeAsync(request,null);
		return response;

	}

	@Override
	public HttpClientResponse postAsync(String url) {
		JavaRequestBuilder request = this.postFormRequest(url,null,null,true);
		HttpClientResponse response = this.executeAsync(request,null);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, String data) {
		JavaRequestBuilder request = this.postJsonRequest(url,data,null,true);
		HttpClientResponse response = this.executeAsync(request,null);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, String data, HttpProxyHeader header) {
		JavaRequestBuilder request = this.postJsonRequest(url,data,header,true);
		HttpClientResponse response = this.executeAsync(request,null);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params) {
		JavaRequestBuilder request = this.postFormRequest(url, null, null,true);
		HttpClientResponse response = this.executeAsync(request,null);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		JavaRequestBuilder request = this.postFormRequest(url, params, header,true);
		HttpClientResponse response = this.executeAsync(request,null);
		return response;

	}
}
