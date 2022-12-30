package com.suven.framework.http.interceptor;

import com.suven.framework.http.data.vo.IResponseResult;
import com.suven.framework.http.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.processor.url.Cdn;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.tool.TopStringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 接口url 验证
 */
@Component
@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_REDIS_CACHE,isRun = false)
public class RedisCacheHandlerInterceptor extends BaseHandlerInterceptorAdapter implements IHandlerInterceptor, InterceptorConstants {
	private  Logger logger = LoggerFactory.getLogger(getClass());

    private static ThreadLocal<Boolean> cdn = new ThreadLocal<>();


    private RedisClusterServer redisClusterServer;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        redisClusterServer = applicationContext.getBean(REDIS_CLUSTER_SERVER, RedisClusterServer.class);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SystemRuntimeException {


        HttpRequestRemote remote =  ParamMessage.getRequestRemote();
        if(redisClusterServer == null || remote.isPostRequest()){
            return true;
        }
        try {
            StringBuilder sb =  TopStringUtils.toStringBuilder(RedisKeys.CDN_RESULT_CACHE_KEY,remote.getUrl(),remote.getSrvMd5Sign());

            String resultResponse = redisClusterServer.get(sb.toString());
            if(null != resultResponse){
                Object result =  JsonUtils.parseObject(resultResponse, Object.class);
                logger.info("========== RedisCacheHandlerInterceptor preHandle redisClusterServer data ========== result:[{}] ", result );
                BaseHttpResponseWrite write = BaseHttpResponseWrite.build(response);
                write.writeResult(result);
                cdn.set(false);
                return false;
            }
            cdn.set(true);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("RedisCacheHandlerInterceptor preHandle redis Exception: [{}]", e);
        }
        return true;

    }

    /**
     * This implementation is empty.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)throws SystemRuntimeException {
        try {
            if (cdn.get()) {
                IResponseResult resultResponse = ParamMessage.getRedisCacheResponseVo();
                if (resultResponse == null) {
                    return;
                }
                HttpRequestRemote remote = ParamMessage.getRequestRemote();
                int cdnTime = Cdn.get(remote.getUrl());
                if (cdnTime == 0) {
                    return;
                }


                StringBuilder sb = TopStringUtils.toStringBuilder(RedisKeys.CDN_RESULT_CACHE_KEY, remote.getUrl(), remote.getSrvMd5Sign());
                String result = JsonUtils.toJson(resultResponse);
                redisClusterServer.setex(sb.toString(), result, cdnTime);
                logger.info("========== RedisCacheHandlerInterceptor afterCompletion redisClusterServer.setex ========== result:[{}] ,cdnTime :[{}] ", result, cdnTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("RedisCacheHandlerInterceptor afterCompletion redis Exception: [{}]", e);
        }

    }




}
