package com.suven.framework.http.interceptor;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.jetty.settings.SystemParamSettings;
import com.suven.framework.http.NetworkUtil;
import com.suven.framework.http.data.vo.RequestParserVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.crypt.SignParam;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * @Title: JsonHandlerInterceptorAdapter.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口拦截器,主要是实现数据反映成对象Bean方面业务类,包括头部数据封装,排序值为3;
 */

@Component
@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_JSON)
public class JsonHandlerInterceptorAdapter extends BaseHandlerInterceptorAdapter
        implements  IHandlerInterceptor,InterceptorConstants,
        IHandlerHeaderInterceptor {
    //AbstractHandlerMethodAdapter  HandlerInterceptorAdapter

    private Logger logger = LoggerFactory.getLogger(getClass());

    private SystemParamSettings systemParamSettings;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        systemParamSettings = applicationContext.getBean(SYSTEM_PARAM_SETTINGS, SystemParamSettings.class);
    }

    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(" url request JsonHandlerInterceptorAdapter  preHandle ===================");
        //判断是get还是post
        boolean isPostReq = request.getMethod().equals(RequestMethod.POST.name());
        boolean isJson = false;
        request.setAttribute(HandlerMapping.PRODUCIBLE_MEDIA_TYPES_ATTRIBUTE,Sets.newHashSet(MediaType.APPLICATION_JSON));

        Map param = new HashMap(request.getParameterMap());
        //如果为post类型请求,且通过ParameterMap获取的内容信息为空时,再通过获取json方式读取文件流;
        if(isPostReq && param.size() < 1){
            param = postJson(request);
            if(null == param ||  param.size() < 1){
                logger.warn("[BAD PARAM] size < 1 or is null. url:{}", request.getRequestURI());
                throw new Exception("post request not param, request url:"+request.getRequestURI());
            }
            isJson = true;
        }
        //返回签名
        HttpRequestPostMessage message ;
        if(null != systemParamSettings && systemParamSettings.isHeaderToken()){
            Map<String, String>  headerInfoMap =  getHeadersInfo(request);
            message =  RequestParserVo.build().parseHeader(headerInfoMap, HttpRequestPostMessage.class);
        }else {
            message = RequestParserVo.build().parseFrom(param, HttpRequestPostMessage.class);
        }

        if(message == null) {
            throw new Exception("request data error, request url : " + request.getRequestURI());
        }

        String url = request.getServletPath();
        long netTime = message.getTimes();
        long sysTime = System.currentTimeMillis();

        message.setIp(NetworkUtil.getIpAddress(request));
        message.setUri(request.getRequestURI());
        message.setTimes(sysTime);

        //将请求公共参数设置到当前线程安全队列容器中;方便后面业务需要获取;
        ParamMessage.setRequestMessage(message);
        //将获取的请求参数,排除过滤不需要参与加密验证参数;
        ParamMessage.setJsonExcludeParamMap(url,param);
        //收集来自请求接口的代理属性信息
        HttpRequestRemote remote = new HttpRequestRemote();
        remote.setClientIp(message.getIp());
        remote.setJsonReq(isJson);

        remote.setUrl(url);
        remote.setClientIp(message.getIp());
        remote.setSrvMd5Sign(SignParam.getServerSign(param));
        remote.setCliMd5Sign(message.getCliSign());
        remote.setPostReq(isPostReq);
        remote.setNetTime(sysTime - netTime);

        ParamMessage.setRequestRemote(remote);
        logger.info("receive client request url=[{}],mode=[{}] ,param=[{}] ", url, request.getMethod(), JsonUtils.map2String(param));
        return true;
    }


    private Map postJson(HttpServletRequest request){
        try {
            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder requestValueBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                requestValueBuilder.append(inputStr);
            }
            String json  = requestValueBuilder.toString();
            if(null == json || "" .equals(json )){
                return null;
            }
            Map map = JsonUtils.toMap(json) ;
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_JSON_FAIL);
    }


    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }





    /**
     * This implementation is empty.
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        logger.debug("JsonHandlerInterceptorAdapter preHandle method ModelAndView 2 ...." );
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.debug("JsonHandlerInterceptorAdapter afterCompletion method 3 ...." );
    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterConcurrentHandlingStarted(
            HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        logger.debug("JsonHandlerInterceptorAdapter afterConcurrentHandlingStarted method 4 ...." );
    }



}
