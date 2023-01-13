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

package com.suven.framework.http.proxy.hutool;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.proxy.AbstractHttpProxy;
import com.suven.framework.http.proxy.FutureCallbackProxy;
import com.suven.framework.http.proxy.HttpProxyHeader;
import com.suven.framework.http.proxy.HttpClientResponse;
import com.suven.framework.http.util.HttpParamsUtil;

import java.util.List;
import java.util.Map;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  hu tool http 网络请求实现
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/
public class HutoolHttpClientProxy extends AbstractHttpProxy {
	public HutoolHttpClientProxy() {
		this(new HttpClientConfig());
	}

	public HutoolHttpClientProxy(HttpClientConfig httpConfig) {
		super(httpConfig);
	}
	private HttpClientResponse exec(HttpRequest request) {
		return execute(request,false);
	}

	private HttpClientResponse executeAsync(HttpRequest request) {
		return execute(request,true);
	}

	private HttpClientResponse execute(HttpRequest request,boolean isAsync) {
		// 设置超时时长
		request = request.timeout(this.getTimeout());
		// 设置代理
		if (isProxy()) {
			request = request.setProxy(this.getProxy());
		}

		try {
			HttpResponse response = null;
			if(isAsync){
				response = request.executeAsync();
			}else {
				response = request.execute();
			};
			int code = response.getStatus();
			boolean successful = response.isOk();
			String body = response.body();
			Map<String, List<String>> headers = response.headers();
			return  HttpClientResponse.build(successful, code, headers, body, null);
		} catch (Exception e) {
			e.printStackTrace();
			return  HttpClientResponse.build(false, 500, null, null, e.getMessage());
		}
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

		HttpRequest request = HttpRequest.get(url);

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), request::header);
		}

		return exec(request);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url) {
		HttpRequest request = HttpRequest.post(url);
		return this.exec(request);
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
		HttpRequest request = HttpRequest.post(url);

		if (HttpParamsUtil.isNotEmpty(data)) {
			request.body(data);
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), request::header);
		}
		return this.exec(request);
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
		HttpRequest request = HttpRequest.post(url);

		if (encode) {
			HttpParamsUtil.forEach(params, (k, v) -> request.form(k, HttpParamsUtil.urlEncode(v)));
		} else {
			HttpParamsUtil.forEach(params, request::form);
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), request::header);
		}
		return this.exec(request);
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
		return postAsync(url,params,header,encode);
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
		HttpRequest request = HttpRequest.post(url);

		if (HttpParamsUtil.isNotEmpty(data)) {
			request.body(data);
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), request::header);
		}
		return this.executeAsync(request);
	}



	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params) {
		return postAsync(url,params,null,true);
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {

		HttpRequest request = HttpRequest.post(url);

		if (encode) {
			HttpParamsUtil.forEach(params, (k, v) -> request.form(k, HttpParamsUtil.urlEncode(v)));
		} else {
			HttpParamsUtil.forEach(params, request::form);
		}

		if (header != null) {
			HttpParamsUtil.forEach(header.getHeaders(), request::header);
		}
		return this.executeAsync(request);
	}


}
