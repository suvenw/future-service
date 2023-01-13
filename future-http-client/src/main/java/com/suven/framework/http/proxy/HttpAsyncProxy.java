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

package com.suven.framework.http.proxy;


import java.util.Map;


/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2021-09-13
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明): http 网络请求接口类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/

public interface HttpAsyncProxy {


	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	HttpClientResponse  getAsync(String url);






	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @return 结果
	 */
	HttpClientResponse getAsync(String url, Map<String, String> params);



	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode);


	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	HttpClientResponse postAsync(String url);



	/**
	 * POST 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	HttpClientResponse postAsync(String url, String data);



	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 * @param header 请求头
	 * @return 结果
	 */
	HttpClientResponse postAsync(String url, String data, HttpProxyHeader header);



	/**
	 * POST 请求
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @return 结果 是否需要 url encode
	 */
	HttpClientResponse postAsync(String url, Map<String, String> params);



	/**
	 * POST 请求,通过线程get()的阻塞式获取结果的实现
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, boolean encode);

//	void getAsync(FutureCallbackProxy future,String url);
//	void getAsync(FutureCallbackProxy future,String url, Map<String, String> params);
//
//	void getAsync(FutureCallbackProxy future,String url, Map<String, String> params,HttpProxyHeader header, boolean encode);
//
//
//	void postAsync(FutureCallbackProxy future,String url);
//
//	void postAsync(FutureCallbackProxy future,String url, String data);
//
//	void postAsync(FutureCallbackProxy future,String url, String data, HttpProxyHeader header);
//
//	void postAsync(FutureCallbackProxy future,String url, Map<String, String> params);
//
//	void postAsync(FutureCallbackProxy future,String url, Map<String, String> params, HttpProxyHeader header, boolean encode);
}
