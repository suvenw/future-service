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

package com.suven.framework.http;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.exception.HttpClientRuntimeException;
import com.suven.framework.http.proxy.HttpProxy;
import com.suven.framework.http.proxy.HttpProxyHeader;
import com.suven.framework.http.proxy.HttpClientResponse;
import com.suven.framework.http.proxy.httpclient.ApacheHttpClientProxy;
import com.suven.framework.http.proxy.hutool.HutoolHttpClientProxy;
import com.suven.framework.http.proxy.java11.JavaHttpClientProxy;
import com.suven.framework.http.proxy.okhttp3.OkHttp3HttpClientProxy;
import com.suven.framework.http.util.ClassUtil;

import java.net.Proxy;
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
public class HttpClientUtil extends HttpClientUtilProxy {

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public static HttpClientResponse get(String url) {
		checkHttpNotNull(proxy);
		return proxy.get(url);
	}
	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public static HttpClientResponse getAsync(String url) {
		checkHttpNotNull(proxy);
		return proxy.getAsync(url);
	}
	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public static HttpClientResponse getAsync(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.getAsync(url, params,null, encode);
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public static HttpClientResponse get(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.get(url, params, encode);
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
	public static HttpClientResponse get(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.get(url, params, header, encode);
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	public static HttpClientResponse post(String url) {
		checkHttpNotNull(proxy);
		return proxy.post(url);
	}

	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	public static HttpClientResponse post(String url, String data) {
		checkHttpNotNull(proxy);
		return proxy.post(url, data);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	public static HttpClientResponse post(String url, String data, HttpProxyHeader header) {
		checkHttpNotNull(proxy);
		return proxy.post(url, data, header);
	}

	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	public static HttpClientResponse post(String url, Map<String, String> params, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.post(url, params, encode);
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
	public static HttpClientResponse post(String url, Map<String, String> params, HttpProxyHeader header, boolean encode) {
		checkHttpNotNull(proxy);
		return proxy.post(url, params, header, encode);
	}
}
