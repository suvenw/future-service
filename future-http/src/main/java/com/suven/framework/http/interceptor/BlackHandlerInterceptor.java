package com.suven.framework.http.interceptor;//package com.suven.frame.server.Interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.suven.framework.http.exception.BusinessLogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @Title: BlackHandlerInterceptor.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口拦截器,主要是实现黑名单方面业务类,排序值为6;
 */

@Component
@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_BLACK)
public class BlackHandlerInterceptor extends BaseHandlerInterceptorAdapter implements IHandlerInterceptor,InterceptorConstants{
	private static Logger logger = LoggerFactory.getLogger(BlackHandlerInterceptor.class);

	private IHandlerValidator handlerValidator;


	public BlackHandlerInterceptor(){}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		Object validator  = applicationContext.getBean(BLACK_HANDLER_VALIDATOR);

		if(null != validator && validator instanceof  IHandlerValidator){
			handlerValidator = (IHandlerValidator)validator;
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BusinessLogicException {
		logger.info(" url request BlackHandlerInterceptor  preHandle ===================");
		if(handlerValidator == null){
			return true;
		}
		if(!handlerValidator.isPostRequest(request)){
			return true;
		}
		return handlerValidator.validatorData();

	}



}
