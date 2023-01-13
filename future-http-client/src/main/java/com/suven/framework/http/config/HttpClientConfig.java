package com.suven.framework.http.config;


import com.suven.framework.http.constants.HttpClientConstants;

import java.net.Proxy;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  http 网络请求配置类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class HttpClientConfig {
	/**
	 * 超时时长，单位毫秒
	 */
	private int timeout = HttpClientConstants.DEFAULT_TIMEOUT;
	/**
	 * 代理配置
	 */
	private Proxy proxy;

	public HttpClientConfig() {
	}
	public HttpClientConfig(Proxy proxy) {
		this.proxy = proxy;
	}
	public HttpClientConfig(int timeout, Proxy proxy) {
		this.timeout = timeout;
		this.proxy = proxy;
	}

	public static HttpClientConfig builder(){
		return new HttpClientConfig();
	};

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public HttpClientConfig toTimeout(int timeout) {
		this.timeout = timeout;
		return this;
	}

	public boolean isProxy(){
		return  null != proxy;
	}
	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
}
