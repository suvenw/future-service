package com.suven.framework.http.interceptor;

import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

public interface IHandlerInterceptor {



    /**
     * 通过 ApplicationContext 获取拦截器的实现bean类
     * @param applicationContext
     */
    void setApplicationContext(ApplicationContext applicationContext);


}
