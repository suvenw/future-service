package com.suven.framework.http.handler;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @Title: JsonResponseHandlerArgumentResolver.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口统一请求返回结果逻辑实现业务类;
 */

public class JsonResponseCacheHandlerArgumentResolver extends AbstractHandlerArgumentResolver<OutputCacheResponse> {



    @Override
    protected Object onResolve(MethodParameter parameter,
                               NativeWebRequest webRequest,
                               ModelAndViewContainer mavContainer,
                               WebDataBinderFactory binderFactory) throws Exception{

        mavContainer.setRequestHandled(true);
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        return OutputCacheResponse.getInstance(request,response);
    }

}