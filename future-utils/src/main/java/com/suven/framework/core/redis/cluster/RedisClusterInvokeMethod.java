package com.suven.framework.core.redis.cluster;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

public abstract class RedisClusterInvokeMethod {


    private Logger logger = LoggerFactory.getLogger(RedisClusterInvokeMethod.class);

    protected JedisCluster jedisCluster;
    protected RedisClusterHandlerCache redisClusterHandlerCache;



    /**
     * 构造方法
     * 通过JedisCluster获取JedisClusterInfoCache和JedisSlotBasedConnectionHandler
     *
     * @param jedisCluster
     */
    public RedisClusterInvokeMethod(JedisCluster jedisCluster, RedisClusterHandlerCache redisClusterHandlerCache) {
        this.jedisCluster  = jedisCluster;
        this.redisClusterHandlerCache  = redisClusterHandlerCache;
//        this.executeBatch = new RedisClusterExecuteBatch(jedisCluster);
    }

}
