

package com.suven.framework.http.proxy.okhttp3;

import com.suven.framework.http.config.HttpClientConfig;
import com.suven.framework.http.proxy.HttpProxyDefaultRequest;
import com.suven.framework.http.proxy.HttpProxyHeader;
import com.suven.framework.http.proxy.HttpClientResponse;
import com.suven.framework.http.proxy.HttpProxyRequest;
import okhttp3.*;

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
public class OkHttp3HttpClientProxy extends AbstractOkHttp3RequestProxy {

	public OkHttp3HttpClientProxy() {
		this(new HttpClientConfig());
	}

	public OkHttp3HttpClientProxy(HttpClientConfig httpClientConfig) {
		super(httpClientConfig);
	}

	public OkHttp3HttpClientProxy(HttpClientConfig httpConfig,OkHttpClient.Builder httpClientBuilder ) {
		super(httpConfig,httpClientBuilder);
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		Okhttp3RequestBuilder request = this.getRequest(url,null,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * GET 请求
	 *
	 * @param url              URL
	 * @param httpProxyRequest HttpProxyParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url, HttpProxyRequest httpProxyRequest) {
		Okhttp3RequestBuilder request = this.getRequest(url,null,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * GET 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url     URL
	 * @param params  参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                encode 是否需要encode转码值为true 或 false, 默认为false
	 *                proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url, Map<String, String> params, int timeout) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setTimeout(timeout);
		Okhttp3RequestBuilder request = this.getRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 *               httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *               timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *               bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *               futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *               encode 是否需要encode转码值为true 或 false, 默认为false
	 *               proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url, Map<String, String> params, boolean encode) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setEncode(encode);
		Okhttp3RequestBuilder request = this.getRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * GET 表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param header 请求头
	 *               httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *               timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *               bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *               futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *               encode 是否需要encode转码值为true 或 false, 默认为false
	 *               proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url, Map<String, String> params, HttpProxyHeader header) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		Okhttp3RequestBuilder request = this.getRequest(url,params,header,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * GET 表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param params           参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url, Map<String, String> params, HttpProxyRequest httpProxyRequest) {
		Okhttp3RequestBuilder request = this.getRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * GET  表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param params           参数
	 * @param header           请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse get(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest) {
		Okhttp3RequestBuilder request = this.getRequest(url,params,header,httpProxyRequest);
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
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,null,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST JSON 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String data) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;

	}

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url     URL
	 * @param data    JSON 参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String data, int timeout) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setTimeout(timeout);
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 *               httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *               timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *               bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *               futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *               encode 是否需要encode转码值为true 或 false, 默认为false
	 *               proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 *               https 是否使用证书  https 的值为 true 或 false, 默认为false
	 * @param header
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String data, HttpProxyHeader header) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,header,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param data             JSON 参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String data, HttpProxyRequest httpProxyRequest) {
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param data             JSON 参数
	 * @param header           请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout() 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, String data, HttpProxyHeader header, HttpProxyRequest httpProxyRequest) {
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,header,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 表单 请求, 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url     URL
	 * @param params  form 参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, Map<String, String> params, int timeout) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setTimeout(timeout);
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, Map<String, String> params, boolean encode) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setEncode(encode);
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 *               httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类 ,具体默认值 HttpProxyDefaultParameter
	 *               timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *               bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *               futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *               encode 是否需要encode转码值为true 或 false, 默认为false
	 *               proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, Map<String, String> params, HttpProxyHeader header) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,header,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param params           form 参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类 ,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, Map<String, String> params, HttpProxyRequest httpProxyRequest) {

		Okhttp3RequestBuilder request = this.postFormRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param params           form 参数
	 * @param header           请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse post(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest) {

		Okhttp3RequestBuilder request = this.postFormRequest(url,params,header,httpProxyRequest);
		HttpClientResponse response = this.execute(request);
		return response;
	}

	/**
	 * GET 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse getAsync(String url) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.getRequest(url,null,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * GET 请求
	 *
	 * @param url              URL
	 * @param httpProxyRequest HttpProxyParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse getAsync(String url, HttpProxyRequest httpProxyRequest) {
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.getRequest(url,null,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * GET 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url     URL
	 * @param params  参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                encode 是否需要encode转码值为true 或 false, 默认为false
	 *                proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, int timeout) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setTimeout(timeout);
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.getRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * GET 请求
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param encode 是否需要 url encode
	 *               httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *               timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *               bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *               futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *               encode 是否需要encode转码值为true 或 false, 默认为false
	 *               proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, boolean encode) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setEncode(encode);
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.getRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * GET 表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params 参数
	 * @param header 请求头
	 *               httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *               timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *               bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *               futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *               encode 是否需要encode转码值为true 或 false, 默认为false
	 *               proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.getRequest(url,params,header,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * GET 表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param params           参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyRequest httpProxyRequest) {
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.getRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * GET  表单 请求,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param params           参数
	 * @param header           请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse getAsync(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest) {
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.getRequest(url,params,header,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST 请求
	 *
	 * @param url URL
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,null,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST JSON 请求
	 *
	 * @param url  URL
	 * @param data JSON 参数
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, String data) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url     URL
	 * @param data    JSON 参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, String data, int timeout) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setTimeout(timeout);
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param data   JSON 参数
	 *               httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *               timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *               bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *               futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *               encode 是否需要encode转码值为true 或 false, 默认为false
	 *               proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @param header
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, String data, HttpProxyHeader header) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,header,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param data             JSON 参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, String data, HttpProxyRequest httpProxyRequest) {
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST JSON 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param data             JSON 参数
	 * @param header           请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout() 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, String data, HttpProxyHeader header, HttpProxyRequest httpProxyRequest) {
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postJsonRequest(url,data,header,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST 表单 请求, 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url     URL
	 * @param params  form 参数
	 * @param timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, int timeout) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setTimeout(timeout);
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param encode 是否需要 url encode
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, boolean encode) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder().setEncode(encode);
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url    URL
	 * @param params form 参数
	 * @param header 请求头
	 *               httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类 ,具体默认值 HttpProxyDefaultParameter
	 *               timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *               bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *               futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *               encode 是否需要encode转码值为true 或 false, 默认为false
	 *               proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header) {
		HttpProxyRequest httpProxyRequest = HttpProxyDefaultRequest.builder();
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,header,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param params           form 参数
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类 ,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyRequest httpProxyRequest) {
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,null,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;
	}

	/**
	 * POST 表单 请求 ,获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *
	 * @param url              URL
	 * @param params           form 参数
	 * @param header           请求头
	 * @param httpProxyRequest HttpProxyParameter   网络请求,个性业务参数扩张请求接口类,具体默认值 HttpProxyDefaultParameter
	 *                         timeout 获取超时间,单位为毫秒,方法传过来,按方法执行,否则按系统配置默认执行
	 *                         bodyMediaType 自定义返回文件类型,（默认值 0）0/1.为JSON字符串,2.为文件流byte[]数组, 3.为文件流
	 *                         futureResult 的值为 true 或false ,true时,为主线程读取异步线程的结果,false为由异步线程 Callback ,返回HttpClientResponse为null
	 *                         encode 是否需要encode转码值为true 或 false, 默认为false
	 *                         proxy 是否使用代理 proxy的值为 true 或 false, 默认为false
	 * @return 结果
	 */
	@Override
	public HttpClientResponse postAsync(String url, Map<String, String> params, HttpProxyHeader header, HttpProxyRequest httpProxyRequest) {
		OkHttp3FutureCallback future =  OkHttp3FutureCallback.build();
		Okhttp3RequestBuilder request = this.postFormRequest(url,params,header,httpProxyRequest);
		HttpClientResponse response = this.executeAsync(request,future);
		return response;

	}



}
