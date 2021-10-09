/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.interceptor;


import com.suven.framework.http.validator.UserTokenHandlerValidator;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.processor.url.UrlExplain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Title: TokenHandlerInterceptor.java
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
@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_TOKEN)
public class TokenHandlerInterceptor extends BaseHandlerInterceptorAdapter implements IHandlerInterceptor,InterceptorConstants{
	private Logger logger = LoggerFactory.getLogger(TokenHandlerInterceptor.class);

	private UserTokenHandlerValidator userTokenValidator;

    public TokenHandlerInterceptor(){}


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        UserTokenHandlerValidator validator = applicationContext.getBean(USER_TOKEN_HANDLER_VALIDATOR, UserTokenHandlerValidator.class);
        if(null != validator && validator instanceof  IHandlerValidator){
            userTokenValidator = validator;
        }
    }

    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SystemRuntimeException {
        logger.info(" url request TokenHandlerInterceptor  preHandle ===================");
        if(null == userTokenValidator ){
            return true;
        }//get请求，不需要登陆
        if(!userTokenValidator.isPostRequest(request)){
            return true;
        }

        HttpRequestRemote remote = ParamMessage.getRequestRemote();
        //验证服务是否维护 ProjectMaintainValidator
//        boolean isMaintain = userTokenValidator.getProjectCache().getUnchecked(remote.getModule());
//        if(isMaintain){
//            userTokenValidator.getProjectCache().invalidate(remote.getModule());
//            //系统维护中
//            throw new SystemRuntimeException(SysMsgEnumType.SYS_PROJECT_MAINTAIN);
//        }
        if(remote == null){
            logger.warn(" TokenHandlerInterceptor RequestRemote is null remote: [{}] ", remote );
            return true;
        }
        //url在白明单中，不需要验证
        if(UrlExplain.isWhite(remote.getUrl())){
            return true;
        }

        boolean isLogin = false;
        if(UrlExplain.mustCheck(remote.getUrl())){//强制登陆状态,直接查redis,否则查临时内存缓存
            isLogin = userTokenValidator.notCacheValidator();
        }else {
            isLogin = userTokenValidator.validatorData();
        }

        if(isLogin ){
            return isLogin;
        }
        userTokenValidator.getTokenError();
		return true;

	}



}
