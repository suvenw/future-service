package com.suven.framework.http.proxy;

import com.alibaba.fastjson.JSON;
import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.constants.HttpClientConstants;
import com.suven.framework.http.proxy.okhttp3.Okhttp3RequestBuilder;
import okhttp3.Headers;

import java.net.Proxy;



/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 网络请求抽像类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/

public abstract class AbstractHttpProxy implements HttpProxy {
	protected  HttpClientConfig httpClientConfig;

	public AbstractHttpProxy(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	@Override
	public void setHttpConfig(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}


	public boolean isProxy(){
		if(null == httpClientConfig){
			return false;
		}
		return httpClientConfig.isProxy();
	}
	public boolean isHttps(){
		if(null == httpClientConfig){
			return false;
		}
		return httpClientConfig.isHttps();
	}
	public Proxy getProxy(){
		return httpClientConfig.getProxy();
	}
	public int getTimeout(){
		return httpClientConfig.getTimeout();
	}

//	private boolean isSuccess(HttpResponse response) {
//		if (response == null) {
//			return false;
//		}
//		if (response.getStatusLine() == null) {
//			return false;
//		}
//		return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
//	}

	@Override
	public <T> T getData(String body, Class<T> parseJson) {
		return JSON.parseObject(body,parseJson);
	}

	/**
	 * 添加request header
	 *
	 */
	protected Headers initJsonHeaders(){
		Headers headers = Headers.of(
				HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA,
				HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON,
				HttpClientConstants.CONTENT_JSON_KEY, HttpClientConstants.CONTENT_JSON_VALUE);
		return headers;
	}

	/**
	 * 添加request header
	 *
	 */
	protected boolean initJsonHeaders(HttpProxyHeader header){
		if(header == null){
			header = new HttpProxyHeader();
		}
		header.add(HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA)
				.add(HttpClientConstants.CONTENT_TYPE, HttpClientConstants.CONTENT_TYPE_JSON)
				.add(HttpClientConstants.CONTENT_JSON_KEY, HttpClientConstants.CONTENT_JSON_VALUE);
		return true;
	}

	/**
	 * 添加request header
	 *
	 */
	protected Headers initFormHeaders(){
		Headers headers = Headers.of(
				HttpClientConstants.USER_AGENT, HttpClientConstants.USER_AGENT_DATA);
		return headers;
	}
}
