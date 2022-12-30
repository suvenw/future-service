package com.suven.framework.core.redis.cluster;


import com.suven.framework.util.json.JsonUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import redis.clients.jedis.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.Function;

/**
 * @author summerao
 */
public class RedisSentinelFactory implements FactoryBean<RedisSentinelInterface>, InitializingBean, DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(RedisSentinelFactory.class);
    private JedisPool jedisPool;
    private RedisSentinelConfigSettings settings = new RedisSentinelConfigSettings();




    public void setSettings(RedisSentinelConfigSettings settings){
        if(settings != null){
            this.settings = settings;
        }
    }



    @Override
    public void afterPropertiesSet() {

        try {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(settings.getPool().getMaxTotal()); //可用连接实例的最大数目，默认值为8；如果赋值为-1，则表示不限制；
            config.setMaxIdle(settings.getPool().getMaxIdle()); //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8。
            config.setMinIdle(settings.getPool().getMinIdle()); //控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值是0。
            config.setMaxWaitMillis(settings.getPool().getMaxWaitMillis());//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出
            config.setTestOnBorrow(settings.getPool().isTestOnBorrow());
            config.setTestOnReturn(settings.getPool().isTestOnReturn());

            Boolean isPassword = false;
            if(settings.getPassword() != null && !"".equals(settings.getPassword())){
                isPassword = true;
            }
            if(isPassword){
                jedisPool = new JedisPool(config, settings.getHost(), settings.getPort(), settings.getTimeout(),
                        settings.getPassword(),settings.getDatabase(),settings.getClientName() );
            }else {
                jedisPool = new JedisPool(config, settings.getHost(), settings.getPort(), settings.getTimeout());
            }
        }catch (Exception e){
            logger.warn("initJedisPoo redis  Exception from servers[{}], Exception[{}]", JsonUtils.toJson(settings),e.getMessage());
            e.printStackTrace();
        }

    }

    public RedisSentinelInterface getJedis() {
        RedisSentinelInterface service = (RedisSentinelInterface) creatProxyInstance();
        if(service != null) {
        } else {
            afterPropertiesSet();
        }
        return service;
    }




    @Override
    public void destroy() throws Exception {
        jedisPool.close();
    }

    @Override
    public RedisSentinelInterface getObject() {
        return getJedis();
    }

    @Override
    public Class<?> getObjectType() {
        return RedisSentinelInterface.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Object creatProxyInstance() {
        return Proxy.newProxyInstance(RedisSentinelInterface.class.getClassLoader(), new Class[]{RedisSentinelInterface.class}, new ProxyFactory(jedisPool));
    }

   public class ProxyFactory<T,R>  implements InvocationHandler, Function<T,R> {
        private JedisPool jedisPool;
        public ProxyFactory(JedisPool jedisPool) {
            this.jedisPool = jedisPool;
        }


        @Override
        public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
            Object result;
            try (Jedis jedis = jedisPool.getResource()) {
                RedisSentinelInvokeExtManager extManager = RedisSentinelInvokeExtManager.getInstance();
                if(null != proxyMethod && extManager.isInvokeMethod(proxyMethod)){
                    Method invokeMethod =  extManager.getInvokeMethod(proxyMethod);
                    Constructor constructor = invokeMethod.getDeclaringClass().getConstructor(Jedis.class);
                    Object pipeline =  constructor.newInstance(jedis);
                    result = invokeMethod.invoke(pipeline,args);
                    return result;
                }
                result = proxyMethod.invoke(jedis, args);
            } catch (Exception e) {
                logger.error("redis one nodes 不支持该功能,Redis Client Invoke Method Exception ",e);
                throw e;
            }
            return result;
        }

        @Override
        public R apply(T o) {
            return this.apply(o);
        }


   }



}
