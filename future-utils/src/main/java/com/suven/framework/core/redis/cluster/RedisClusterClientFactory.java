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
import java.util.*;
import java.util.function.Function;

/**
 * @author summerao
 */
public class RedisClusterClientFactory  implements FactoryBean<RedisClusterInterface>, InitializingBean, DisposableBean {

    private final int CONNECTION_TIMEOUT = 2000;//连接超时（默认2000ms）
    private static Logger logger = LoggerFactory.getLogger(RedisClusterClientFactory.class);
    private JedisCluster jedisCluster;
    private RedisClusterHandlerCache redisClusterHandlerCache ;
    private RedisClusterConfigSettings settings = new RedisClusterConfigSettings();


    private String server;
    private String password = null;

//    /**
//     * 构造方法
//     * 通过JedisCluster获取JedisClusterInfoCache和JedisSlotBasedConnectionHandler
//     *
//     * @param jedisCluster
//     */
//    public RedisClusterClientFactory(JedisCluster jedisCluster) {
//        super(jedisCluster);
//    }

    public void setServer(String server) {
        logger.warn("RedisClusterClientFactory init redis client server[{}]", server);
        this.server = server;
    }
    public void setPassword(String password){
        if(null != password){
            this.password = password;
        }
    }

    public void setSettings(RedisClusterConfigSettings settings){
        if(settings != null){
            this.settings = settings;
        }
    }




    @Override
    public void afterPropertiesSet() {
        //Jedis Cluster will attempt to discover cluster nodes automatically
        if(null == server || "".equals(server)){
            logger.warn("afterPropertiesSet init JedisPoo redis  end because from server is null server:[{}]",server);
            return;
        }
        String[] servers = server.split(";|,");
        try {
            Set<HostAndPort> jedisClusterNodes = new HashSet<>();
            for (String str : servers) {
                String[] ap = str.split(":");
                HostAndPort hp = new HostAndPort(ap[0], Integer.valueOf(ap[1]));
                jedisClusterNodes.add(hp);
            }
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(settings.getPool().getMaxTotal()); //可用连接实例的最大数目，默认值为8；如果赋值为-1，则表示不限制；
            config.setMaxIdle(settings.getPool().getMaxIdle()); //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8。
            config.setMinIdle(settings.getPool().getMinIdle()); //控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值是0。
            config.setMaxWaitMillis(settings.getPool().getMaxWaitMillis());//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出
            config.setTestOnBorrow(settings.getPool().isTestOnBorrow());
            config.setTestOnReturn(settings.getPool().isTestOnReturn());

            // 节点，超时时间，最多重定向次数，链接池
//            jedisCluster = new JedisCluster(jedisClusterNodes, settings.getTimeout(),settings.getMaxRedirections(), config);
            if(null != password){
                jedisCluster = new JedisCluster(jedisClusterNodes, CONNECTION_TIMEOUT, settings.getTimeout(), settings.getMaxRedirections(), password, config);
            }else{
                jedisCluster = new JedisCluster(jedisClusterNodes, settings.getTimeout(),settings.getMaxRedirections(), config);
            }
            initJedisPool(jedisCluster);
        }catch (Exception e){
            logger.warn("initJedisPoo redis  Exception from servers[{}], Exception[{}]", JsonUtils.toJson(servers),e.getMessage());
            e.printStackTrace();
        }

    }
    /**
     * 构造方法
     * 通过JedisCluster获取JedisClusterInfoCache和JedisSlotBasedConnectionHandler
     *
     * @param jedisCluster
     */
    public void initJedisPool(JedisCluster jedisCluster) {
        this.redisClusterHandlerCache = new RedisClusterHandlerCache();
        this.redisClusterHandlerCache.init(jedisCluster);
    }

    public RedisClusterInterface getJedis() {
        RedisClusterInterface service = (RedisClusterInterface) creatProxyInstance();
        return service;
    }


    @Override
    public void destroy() throws Exception {
        jedisCluster.close();
    }

    /**
     * T getObject() 获取泛型T的实例。用来创建Bean。当IoC容器通过getBean方法来FactoryBean创建的实例时实际获取的不是FactoryBean 本身而是具体创建的T泛型实例。等下我们会来验证这个事情。
     * @return
     */
    @Override
    public RedisClusterInterface getObject() {
        return getJedis();
    }

    /**
     * Class<?> getObjectType() 获取 T getObject()中的返回值 T 的具体类型。这里强烈建议如果T是一个接口，返回其具体实现类的类型。
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return RedisClusterInterface.class;
    }

    /**
     * default boolean isSingleton() 用来规定 Factory创建的的bean是否是单例。这里通过默认方法定义为单例。
     * @return
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

    public Object creatProxyInstance() {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        //        //ServiceProxy 为代理实现 具体见 jdk动态代理
        Class[] targetInterfaces =  {RedisClusterInterface.class};
        Class targetInterfaceClass = targetInterfaces[0];
        InvocationHandler handler = new ProxyFactory(jedisCluster,redisClusterHandlerCache);

        return Proxy.newProxyInstance(targetInterfaceClass.getClassLoader(), targetInterfaces,handler );
    }

//    public void initJedisPool(JedisCluster jedisCluster) {
//        this.jedisCluster = jedisCluster;
//        MetaObject metaObject = SystemMetaObject.forObject(jedisCluster);
//        JedisClusterInfoCache clusterInfoCache = (JedisClusterInfoCache) metaObject.getValue("connectionHandler.cache");
//        JedisSlotBasedConnectionHandler connectionHandler = (JedisSlotBasedConnectionHandler) metaObject.getValue("connectionHandler");
//    }

    public class ProxyFactory<T,R>   implements InvocationHandler, Function<T,R> {
        private final JedisCluster jedisCluster;
        private final RedisClusterHandlerCache redisHandlerCache;
        public ProxyFactory(JedisCluster jedisCluster, RedisClusterHandlerCache redisHandlerCache) {
            this.jedisCluster = jedisCluster;
            this.redisHandlerCache = redisHandlerCache;
        }


        /**
         * @param proxy Object proxy：代理实例
         * @param proxyMethod Method method：被调用的方法
         * @param args  Object[] args：被调用方法的参数
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
            Object result;
            try  {
                RedisClusterInvokeExtManager extManager = RedisClusterInvokeExtManager.getInstance();
                boolean isProxyMethod =  extManager.isInvokeMethod(proxyMethod);
                if( isProxyMethod){
//                    Map<String, byte[]> keysValuesMap, Integer secondTime
//                    result = pipeline.refinedMset((Map<String, byte[]> )args[0],(Integer)args[1]);
//                    return result;
                    Method invokeMethod = extManager.getInvokeMethod(proxyMethod);
                    Constructor  constructor = invokeMethod.getDeclaringClass().getConstructor(jedisCluster.getClass(),redisHandlerCache.getClass());
                    Object pipeline =  constructor.newInstance(jedisCluster,redisHandlerCache);
//                    RedisClusterPipelineBatch pipeline = new RedisClusterPipelineBatch(jedisCluster,redisHandlerCache);
                    return invokeMethod.invoke(pipeline,args);
                }
                switch (proxyMethod.getName()) {
                    case "toString":
                        return this.toString();
                }

                result = proxyMethod.invoke(jedisCluster, args);
            } catch (Exception e) {
                logger.error("redis one nodes 不支持该功能,Redis Client Invoke Method Exception:{}",e);
                e.printStackTrace();
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
