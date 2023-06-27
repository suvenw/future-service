/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.interceptor;


import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.processor.url.UrlExplain;
import com.suven.framework.http.validator.JwtHandlerValidator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Title: JwtHandlerInterceptor.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口拦截器,主要是实现请求接口 用户是否登陆后的token 校验业务,排序值为8;
 */


@Component
@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_JWT)
public class JwtHandlerInterceptor extends BaseHandlerInterceptorAdapter implements IHandlerInterceptor{

    private JwtHandlerValidator jwtValidator;

    public JwtHandlerInterceptor(){}


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        JwtHandlerValidator validator = applicationContext.getBean("jwtHandlerValidator", JwtHandlerValidator.class);
        if(null != validator && validator instanceof  IHandlerValidator){
            jwtValidator = validator;
        }

    }

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SystemRuntimeException {

        HttpRequestRemote remote = ParamMessage.getRequestRemote();
        if(remote == null){
            return true;
        }
        if(UrlExplain.isWhite(remote.getUrl())){
            return true;
        }
        //TOKEN KEY
        String X_ACCESS_TOKEN = "X-Access-Token";
        String token = request.getHeader(X_ACCESS_TOKEN);
        return  jwtValidator.tokenValidator(token);

	}




}
