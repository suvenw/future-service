package com.suven.framework.http.handler;


import com.google.common.reflect.TypeToken;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.interceptor.IHandlerHeaderInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.suven.framework.http.exception.SystemRuntimeException;

import javax.servlet.http.HttpServletRequest;


/**
 * 对Controller中的入参进行转换
 * 具体的转换逻辑由子类实现 HandlerMethodArgumentResolver
 * Created by Alex on 2014/4/28
 */
public abstract class AbstractHandlerArgumentResolver<T> implements HandlerMethodArgumentResolver, IHandlerHeaderInterceptor {

    static Logger logger = LoggerFactory.getLogger(AbstractHandlerArgumentResolver.class);
    final TypeToken<T> type = new TypeToken<T>(getClass()) {};


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
//        System.err.println("AbstractHandlerArgumentResolver  supportsParameter method 5 ...." );
        return type.getRawType().isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
//        System.err.println("AbstractHandlerArgumentResolver  resolveArgument method 6 ...." );
        Object result = onResolve(parameter, webRequest, mavContainer, binderFactory);
        if (result == null) {
            logger.error("转换参数失败");
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_CHECK);
        }
        return result;
    }

    /**
     * 通过获取请求头的Content-Type类型,值是包含 "application/json" 字符串信息
     * 用于判断请求过来的参数格式,如果包含则认为是json格式,
     * 如果是json返回ture;
     * @param webRequest HttpServletRequest 网络请求对象
     * @return  boolean   如果是json返回ture;否则为false;
     * **/
    protected boolean isJsonRequestContentType(NativeWebRequest webRequest){
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        boolean requestJson = this.isJsonRequestFromContentType(request);
        return requestJson;
    }




    protected abstract Object onResolve(MethodParameter parameter,
                                        NativeWebRequest webRequest,
                                        ModelAndViewContainer mavContainer,
                                        WebDataBinderFactory binderFactory) throws Exception;
}
