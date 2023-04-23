package com.suven.framework.core.redis.cluster;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public abstract class RedisSentinelInvokeMethod {

    Logger logger = LoggerFactory.getLogger(RedisSentinelInvokeMethod.class);
    protected Jedis jedis;

    public RedisSentinelInvokeMethod(Jedis jedis){
        this.jedis = jedis;
    }



}
