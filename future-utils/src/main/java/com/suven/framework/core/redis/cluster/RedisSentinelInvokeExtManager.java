package com.suven.framework.core.redis.cluster;

import com.suven.framework.common.constants.ReflectionsScan;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.*;

public class RedisSentinelInvokeExtManager {
    static Map<String, RedisClientInvokeMethod> methodNameMap = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(RedisSentinelInvokeExtManager.class);

    private RedisSentinelInvokeExtManager(){};
    public static RedisSentinelInvokeExtManager getInstance() {
        return LazyRedisClientInvokeCache.INSTANCE;
    }



    public boolean isInvokeMethod(Method invokeMethod) {
        String methodName = RedisClientInvokeMethod.build().method(invokeMethod).toString();
        return methodNameMap.containsKey(methodName);
    }

    public Method getInvokeMethod(Method proxyMethod) {
        String methodName = RedisClientInvokeMethod.build().method(proxyMethod).toString();
        if( methodNameMap.containsKey(methodName)){
            RedisClientInvokeMethod method = methodNameMap.get(methodName);
            return method.getMethod();

        }
        return null;

    }
    public RedisClientInvokeMethod getRedisInvokeMethod(Method proxyMethod) {
        String methodName = RedisClientInvokeMethod.build().method(proxyMethod).toString();
        if( methodNameMap.containsKey(methodName)){
            RedisClientInvokeMethod method = methodNameMap.get(methodName);
            return method;

        }
        return null;

    }

   private static class LazyRedisClientInvokeCache {
        private static final RedisSentinelInvokeExtManager INSTANCE = new RedisSentinelInvokeExtManager();
        static {
            Set<Class<? extends RedisSentinelInvokeMethod>> classList = ReflectionsScan.reflections.getSubTypesOf(RedisSentinelInvokeMethod.class);
            if(null != classList && !classList.isEmpty()){
               for (Class<?> clazz : classList){
                   Set<Method> methodSet = ReflectionUtils.getAllMethods(clazz);
                   methodSet.forEach(method -> {
                       //方法名;
                       RedisClientInvokeMethod invokeMethod =
                               RedisClientInvokeMethod.build().method(method);
                       methodNameMap.putIfAbsent(invokeMethod.toString(), invokeMethod);
                   });
               }
            }
        }
    }





}
