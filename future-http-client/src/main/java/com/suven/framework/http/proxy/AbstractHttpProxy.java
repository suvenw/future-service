package com.suven.framework.http.proxy;

import com.alibaba.fastjson.JSON;
import com.suven.framework.http.config.HttpClientConfig;

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
	protected HttpClientConfig httpClientConfig;

	public AbstractHttpProxy(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}

	@Override
	public void setHttpConfig(HttpClientConfig httpClientConfig) {
		this.httpClientConfig = httpClientConfig;
	}


	public boolean isProxy(){
		return httpClientConfig.isProxy();
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
}
