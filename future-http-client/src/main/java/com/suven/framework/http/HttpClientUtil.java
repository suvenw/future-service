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

import java.util.Map;


/**
 * <p>
 * 请求工具类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2019/12/24 18:15
 */
public class HttpClientUtil {
	private static HttpProxy proxy;

	private static class HttpProxyInstance {
		private static final HttpClientUtil INSTANCE = new HttpClientUtil();
		private static final HttpClientConfig HTTP_CONFIG = HttpClientConfig.builder().toTimeout(HttpClientConstants.DEFAULT_TIMEOUT);
	}

	public static HttpClientUtil getInstance() {
		return getInstance(HttpProxyInstance.HTTP_CONFIG);
	}
	public static HttpClientUtil getInstance(HttpClientConfig httpConfig) {
		if(proxy == null){
			HttpProxyInstance.INSTANCE.selectHttpProxy();
		}if(httpConfig == null){
			proxy.setHttpConfig(HttpProxyInstance.HTTP_CONFIG);
		}
		return HttpProxyInstance.INSTANCE;
	}

	/**
	 * 选择网络请求架构实现代理逻辑
	 */
	protected static void selectHttpProxy() {
		HttpProxy defaultProxy = null;
		ClassLoader classLoader = HttpClientUtil.class.getClassLoader();
		// 基于 okhttp3
		if (null == defaultProxy && ClassUtil.isPresent("okhttp3.OkHttpClient", classLoader)) {
			defaultProxy = createHttpProxy(OkHttp3HttpClientProxy.class);
		}
		// 基于 java 11 HttpClient
		if (ClassUtil.isPresent("java.net.http.HttpClient", classLoader)) {
			defaultProxy = createHttpProxy(JavaHttpClientProxy.class);
		}
		// 基于 httpclient
		if (null == defaultProxy && ClassUtil.isPresent("org.apache.http.impl.client.HttpClients", classLoader)) {
			defaultProxy = createHttpProxy(ApacheHttpClientProxy.class);
		}
		// 基于 hutool
		if (null == defaultProxy && ClassUtil.isPresent("cn.hutool.http.HttpRequest", classLoader)) {
			defaultProxy = createHttpProxy(HutoolHttpClientProxy.class);
		}

		if (defaultProxy == null) {
			throw new HttpClientRuntimeException("Has no HttpImpl defined in environment!");
		}
		setHttp(defaultProxy);

	}

	/**
	 * 创建网络实现的代码类
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	protected static <T extends HttpProxy> HttpProxy createHttpProxy(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Throwable e) {
			return null;
		}
	}

	protected static void setHttp(HttpProxy httpProxy) {
		proxy = httpProxy;
	}

	private static void checkHttpNotNull(HttpProxy proxy) {
		if (null == proxy) {
			getInstance();
		}
	}


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
