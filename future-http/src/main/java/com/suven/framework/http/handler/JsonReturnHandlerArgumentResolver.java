package com.suven.framework.http.handler;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletResponse;


/**
 * @Title: JsonReturnHandlerArgumentResolver.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口统一请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
 */

public class JsonReturnHandlerArgumentResolver extends AbstractHandlerArgumentResolver<OutputMessage> {



    @Override
    protected Object onResolve(MethodParameter parameter,
                               NativeWebRequest webRequest,
                               ModelAndViewContainer mavContainer,
                               WebDataBinderFactory binderFactory) throws Exception{

        mavContainer.setRequestHandled(true);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        return OutputMessage.getInstance(response);
    }

}