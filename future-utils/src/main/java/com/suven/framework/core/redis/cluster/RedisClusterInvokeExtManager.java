package com.suven.framework.core.redis.cluster;

import com.suven.framework.common.constants.ReflectionsScan;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;
import java.util.*;

public class RedisClusterInvokeExtManager {
    private static Map<String, RedisClientInvokeMethod> methodNameMap = new HashMap<>();
    private Map<Integer, JedisPool> slots = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(RedisClusterInvokeExtManager.class);

    private RedisClusterInvokeExtManager(){};
    public static RedisClusterInvokeExtManager getInstance() {
        return LazyRedisClusterInvokeCache.INSTANCE;
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
    public RedisClientInvokeMethod getRedisClusterInvokeMethod(Method proxyMethod) {
        String methodName = RedisClientInvokeMethod.build().method(proxyMethod).toString();
        if( methodNameMap.containsKey(methodName)){
            RedisClientInvokeMethod method = methodNameMap.get(methodName);
            return method;

        }
        return null;

    }

   private static class LazyRedisClusterInvokeCache {
       private static final RedisClusterInvokeExtManager INSTANCE = new RedisClusterInvokeExtManager();

       static {
           Set<Class<? extends RedisClusterInvokeMethod>> classList = ReflectionsScan.reflections.getSubTypesOf(RedisClusterInvokeMethod.class);
           if (null != classList && !classList.isEmpty()) {
               for (Class<?> clazz : classList) {
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
