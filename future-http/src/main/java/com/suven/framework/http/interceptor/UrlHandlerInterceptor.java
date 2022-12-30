package com.suven.framework.http.interceptor;//package com.suven.frame.server.Interceptor;


import com.suven.framework.http.handler.BaseHttpResponseWrite;
import com.suven.framework.http.handler.BaseHttpResponseWriteHandlerConverter;
import com.suven.framework.http.handler.IResponseHandler;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.processor.url.AnnoUrlExplain;
import com.google.common.collect.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Title: UrlHandlerInterceptor.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口拦截器,主要是实现请求接口url是否存在校验业务,排序值为2;
 */


@Component
@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_URL)
public class UrlHandlerInterceptor extends BaseHandlerInterceptorAdapter implements IHandlerInterceptor{
	private static Logger logger = LoggerFactory.getLogger(UrlHandlerInterceptor.class);


	private static ImmutableSet<String> ALLOWED_URL_PARAM = ImmutableSet.of("/","/test","/error");


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SystemRuntimeException {
		logger.info(" url request UrlHandlerInterceptor  preHandle ===================");
//		logger.warn(" UrlHandlerInterceptor logger  ===================");
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setHeader("Accept",MediaType.APPLICATION_JSON_VALUE);
		String url = request.getServletPath();
		if(ALLOWED_URL_PARAM.contains(url)){
			BaseHttpResponseWrite write = BaseHttpResponseWrite.build(response);
			write.writeSuccess();
			return false;
		}
		if( !AnnoUrlExplain.urlSet.contains(url)){
			IResultCodeEnum msgEnumType = SysResultCodeEnum.SYS_REQUEST_URL_NOT_FOUND.formatMsg(url);
			BaseHttpResponseWrite write = BaseHttpResponseWrite.build(response);
			write.write(msgEnumType);
			return false;
		}
		return true;

	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws SystemRuntimeException {
//		logger.warn("UrlHandlerInterceptor afterCompletion method 3 ...." );
	}



}
