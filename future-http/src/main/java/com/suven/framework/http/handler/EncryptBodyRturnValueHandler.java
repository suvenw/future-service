//package com.ds.live.base.http.handler;
//
//import com.alibaba.fastjson.JSON;
//import com.ds.live.base.http.data.vo.ResponseResultVo;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.context.request.NativeWebRequest;
//import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
//import org.springframework.web.method.support.ModelAndViewContainer;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//
///**
// *
// * EncryptBodyRturnValueHandler
// * @description 加密实体返回值组装器
// * @date 2018/8/16 21:53
// */
//public class EncryptBodyRturnValueHandler implements HandlerMethodReturnValueHandler {
//
//    @Override
//    public boolean supportsReturnType(MethodParameter returnType) {
//        return returnType.hasMethodAnnotation(ResponseBody.class);
//    }
//
//    @Override
//    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
//        //请自定义你的处理逻辑。
//        //此处我是将返回值进行AES加密，然后返回给客户端
//        ResponseBody encryptBody = returnType.getMethodAnnotation(ResponseBody.class);
//        mavContainer.setRequestHandled(true);
//        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//        PrintWriter outWriter = response.getWriter();
////        Encrypt encrypt = getEncrypt(encryptBody.method());
////        Object encode = encrypt.encode(returnValue);
//        String jsonString = "";
//        if(encryptBody!=null) {
//            jsonString = JSON.toJSONString(encryptBody);
//        }
//        outWriter.write(jsonString);
//        outWriter.flush();
//        outWriter.close();
//    }
//}
