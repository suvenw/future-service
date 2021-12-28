package com.suven.framework.http.handler;


import com.suven.framework.common.api.IRequestVo;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.util.tool.IoUtilConverter;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;


/**
 * @Title: JsonRequestHandlerArgumentResolver.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口统一请求实现转换逻辑实现业务类;
 */

public class JsonRequestHandlerArgumentResolver extends AbstractHandlerArgumentResolver<IRequestVo> {

    @Override
    protected Object onResolve(MethodParameter parameter,
                               NativeWebRequest webRequest,
                               ModelAndViewContainer mavContainer,
                               WebDataBinderFactory binderFactory)  throws Exception {

        mavContainer.setRequestHandled(true);
        if(parameter.getParameterType() == HttpServletRequest.class){
            return null;
        }
        /**
         *  获取http请求信息的实现方法记录
         * HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
         * HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        **/

        boolean isJsonRequest =  ParamMessage.getRequestRemote().isJsonRequest();
//        boolean isJsonRequest =   this.isJsonRequestContentType(webRequest);
        Object  result = this.parserDate(webRequest, parameter.getParameterType(),isJsonRequest);
        return result;
    }


    @SuppressWarnings("unchecked")
    private  <T> T parseBody(NativeWebRequest webRequest, Class<T> clazz,boolean isJsonRequest) {

        T t ;
        try {
            if(isJsonRequest){
                String jsonString =  ParamMessage.getJsonParseString();
                Method method = clazz.getMethod("parseJson", String.class, Class.class);
                t = (T) method.invoke(clazz.newInstance(), jsonString, clazz);
            }else {
                if(webRequest == null ) {
                    return null;
                }
                Map body =  webRequest.getParameterMap();
                Method method = clazz.getMethod("parseFrom", Map.class, Class.class);
                t = (T) method.invoke(clazz.newInstance(), body, clazz);
            }

        }catch (Exception e) {
            e.printStackTrace();
            if(e instanceof SystemRuntimeException){
                throw  (SystemRuntimeException)e;
            }else {
                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR).format(e.getCause().getMessage());
            }
        }
        return t;
    }

    private <T> T parserDate(NativeWebRequest webRequest, Class<T> clazz,boolean isJsonRequest){
        if(clazz == null){
            return null;
        }
        boolean isExtendsIRequestVo = IRequestVo.class.isAssignableFrom(clazz);
        if(!isExtendsIRequestVo){
            String REQUEST_OBJECT_ERROR_MSG = "receive client request Object class :["+clazz+"] not extends RequestParserVo or implements IRequestVo ";
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR).format(REQUEST_OBJECT_ERROR_MSG);

        }
        T t  = null;
        try {
            IRequestVo requestVo = (IRequestVo) clazz.newInstance();
            if(isJsonRequest){
                String jsonString =  ParamMessage.getJsonParseString();
                t  = requestVo.parseJson(jsonString,clazz);
            }else {
                if(webRequest == null ) {
                    return null;
                }
                Map  body = webRequest.getParameterMap();
                t  = requestVo.parseFrom(( Map<String, Object>)body,clazz);
            }

        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof SystemRuntimeException){
                throw  (SystemRuntimeException)e;
            }else {
                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR).format(e.getCause().getMessage());
            }
        }

        return t;
    }


//    @Override
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//
//        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
//        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(servletRequest, MultipartHttpServletRequest.class);
//
//        ExcelRequestBody annotation = parameter.getParameterAnnotation(ExcelRequestBody.class);
//        if (multipartRequest != null) {
//            List<Object> result = new ArrayList<>();
//            List<MultipartFile> files = multipartRequest.getFiles(annotation.name());
//            for (MultipartFile file : files) {
//                if (converters.supportsExcelType(annotation.type())) {
//                    List<?> part = converters.fromExcel(annotation, file.getInputStream());
//                    result.addAll(part);
//                }
//            }
//            return result;
//        }
//        return null;
//
//    }


    private String getJsonRequestBody(HttpServletRequest servletRequest) {
        boolean isJson = this.isJsonRequestFromContentType(servletRequest);
        if (isJson) {
            try {
                String jsonBody = IOUtils.toString(servletRequest.getInputStream(), this.CHARSET_CONTENT_UTF8);
                return jsonBody;
            } catch (Exception e) {
                logger.error("getJsonRequestBody exception ", e);
                throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_JSON_FAIL);
            }
        }
        return null;
    }


}