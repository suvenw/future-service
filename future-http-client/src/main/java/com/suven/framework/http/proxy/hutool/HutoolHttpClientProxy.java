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

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.proxy.HttpProxyHeader;
import com.suven.framework.http.proxy.HttpClientResponse;
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
public class HutoolHttpClientProxy extends AbstractHutoolRequestProxy {

	public HutoolHttpClientProxy() {
		super(new HttpClientConfig());
	}

	public HutoolHttpClientProxy(HttpClientConfig httpConfig) {
		super(httpConfig);
	}


	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url) {
		HutoolRequestBuilder request = this.getRequest(url,null,null,true);
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
		HutoolRequestBuilder request = this.getRequest(url,params,null,encode);
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
		HutoolRequestBuilder request = this.getRequest(url,params,header,encode);
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
		HutoolRequestBuilder request = this.postFormRequest(url,null,null,true);
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
		HutoolRequestBuilder request = this.postJsonRequest(url,data,null,true);
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
		HutoolRequestBuilder request = this.postJsonRequest(url,data,header,true);
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
		HutoolRequestBuilder request = this.postFormRequest(url,params,null,encode);
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
		HutoolRequestBuilder request = this.postFormRequest(url,params,header,encode);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	@Override
	public HttpClientResponse getAsync(String url) {

		HutoolRequestBuilder request = this.getRequest(url,null,null,true);
		HttpClientResponse response = this.executeAsync(request);
		return response;

	}



	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params) {
		HutoolRequestBuilder request = this.getRequest(url,params,null,true);
		HttpClientResponse response = this.executeAsync(request);
		return response;
	}



	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		HutoolRequestBuilder request = this.getRequest(url,params,header,true);
		HttpClientResponse response = this.executeAsync(request);
		return response;
	}



	@Override
	public HttpClientResponse postAsync(String url) {
		HutoolRequestBuilder request = this.postFormRequest(url,null,null,true);
		HttpClientResponse response = this.executeAsync(request);
		return response;
	}



	@Override
	public HttpClientResponse postAsync(String url, String data) {
		HutoolRequestBuilder request = this.postJsonRequest(url,data,null,true);
		HttpClientResponse response = this.executeAsync(request);
		return response;
	}


	@Override
	public HttpClientResponse postAsync(String url, String data, HttpProxyHeader header) {
		HutoolRequestBuilder request = this.postJsonRequest(url,data,header,true);
		HttpClientResponse response = this.executeAsync(request);
		return response;
	}



	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params) {

		HutoolRequestBuilder request = this.postFormRequest(url,params,null,true);
		HttpClientResponse response = this.executeAsync(request);
		return response;
	}

	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {

		HutoolRequestBuilder request = this.postFormRequest(url,params,null,true);
		HttpClientResponse response = this.executeAsync(request);
		return response;
	}


}
