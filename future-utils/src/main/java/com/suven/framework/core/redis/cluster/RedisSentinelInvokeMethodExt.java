package com.suven.framework.core.redis.cluster;


import com.suven.framework.common.cat.CatCacheKeySign;
import com.suven.framework.core.db.IterableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RedisSentinelInvokeMethodExt extends RedisSentinelInvokeMethod {

    Logger logger = LoggerFactory.getLogger(RedisSentinelInvokeMethodExt.class);
    private Jedis jedis;

    public RedisSentinelInvokeMethodExt(Jedis jedis){
        super(jedis);
        this.jedis = jedis;
    }




    //自定义实现重写的方法
    public String setex(byte[] key,  byte[] value,int seconds){
      return   jedis.setex(new String(key),seconds,new String(value));
    }

    /**
     * mset操作，同时设置失效时间
     *
     * @param keysvalues {key1:value1,key2:value2...}，key-value集合
     * @param time 失效时间，单位秒
     * @return {key1:result1,key2:result2...}，map集合，key对应入参，result为redis返回的结果；
     * <br>异常情况下返回size为0的map对象或者部分执行成功的key对应的结果
     */
    Map<String, Object> refinedMset(Map<String, byte[]> keysvalues, Integer time){
        List<String> list = new ArrayList<>();
        keysvalues.entrySet().forEach( entry ->{
            list.add(entry.getKey());
            list.add(new String(entry.getValue()));
        } );
        jedis.mset(list.toArray(new String[list.size()]));
        return null;
    }

    public String get(@CatCacheKeySign String key) {
       return jedis.get(key);
    }
}
