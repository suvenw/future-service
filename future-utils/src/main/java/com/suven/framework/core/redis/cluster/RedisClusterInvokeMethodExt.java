package com.suven.framework.core.redis.cluster;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class RedisClusterInvokeMethodExt extends RedisClusterInvokeMethod {


    private Logger logger = LoggerFactory.getLogger(RedisClusterInvokeMethodExt.class);

    private RedisClusterExecuteBatch executeBatch;

    /**
     * 构造方法
     * 通过JedisCluster获取JedisClusterInfoCache和JedisSlotBasedConnectionHandler
     *
     * @param jedisCluster
     * @param redisClusterPipeline
     */
    public RedisClusterInvokeMethodExt(JedisCluster jedisCluster, RedisClusterHandlerCache redisClusterPipeline) {
        super(jedisCluster, redisClusterPipeline);
        this.executeBatch = new RedisClusterExecuteBatch(jedisCluster);
    }

    /**
     * 批量删除key的方法
     * @param keyList
     * @return
     */
    public Map<String, Object> refinedDel(Collection <String> keyList) {
        if(keyList == null){
            return null;
        }
        AtomicBoolean isOrig = new AtomicBoolean(false);
        try {
            //原始循环处理实现
            if ( executeBatch.isExecuteOrig()) {
                return executeBatch.origBatchDelete(keyList);
            }
            Map<JedisPool,Collection<String>> jedisPoolKeysMap =  redisClusterHandlerCache.jedisPoolMap(keyList);
            Map<String, Object> resultMap = executeBatch.batchJedisPoolDelete(jedisPoolKeysMap,isOrig);
            return resultMap;
        } catch (Exception e) {
            logger.error("refinedDel exception:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                executeBatch.initSlotCache();
            }
        }
    }

    /**
     * 批量key查找的方法
     * @param keyList
     * @return
     */
    public Map<String, byte[]> refinedMget(Collection <String> keyList) {
        AtomicBoolean isOrig = new AtomicBoolean(false);
        try {
            //原始循环处理实现
            if ( executeBatch.isExecuteOrig()) {
                return executeBatch.origBatchGet(keyList);
            }
            Map<JedisPool,Collection<String>> jedisPoolKeysMap =  redisClusterHandlerCache.jedisPoolMap(keyList);
            Map<String, byte[]> resultMap = executeBatch.batchJedisPoolGet(jedisPoolKeysMap,isOrig);
           return resultMap;

        } catch (Exception e) {
            logger.error("refinedMget exception:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                executeBatch.initSlotCache();
            }
        }
    }

    /**
     * 批量key set和过期时间实现方法
//     * @param keysValuesMap
     * @return
     */
    public Map<String,String> refinedMset(Map<String, byte[]> keysValuesMap, Integer secondTime) {

        AtomicBoolean isOrig = new AtomicBoolean(false);
        try {
            //原始循环处理实现
            if ( executeBatch.isExecuteOrig()) {
                return executeBatch.origBatchSet(keysValuesMap,secondTime);
            }
            Map<JedisPool,Collection<String>> jedisPoolKeysMap =  redisClusterHandlerCache.jedisPoolMap(keysValuesMap.keySet());
            Map<String,String> resultMap = executeBatch.batchJedisPoolSet(jedisPoolKeysMap,keysValuesMap,secondTime,isOrig);
            return resultMap;

        } catch (Exception e) {
            logger.error("refinedMset Exception:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                executeBatch.initSlotCache();
            }
        }
    }

    public Map<String, Long> refinedZadd(Map<String, Map<String, Double>> keyValueMap){
        AtomicBoolean isOrig = new AtomicBoolean(false);
        try {
            //原始循环处理实现
            if ( executeBatch.isExecuteOrig()) {
                return executeBatch.origBatchZadd(keyValueMap);
            }
            Map<JedisPool,Collection<String>> jedisPoolKeysMap =  redisClusterHandlerCache.jedisPoolMap(keyValueMap.keySet());

            Map<String,Long> resultMap = executeBatch.batchJedisPoolZadd(jedisPoolKeysMap,keyValueMap,isOrig);
            return resultMap;

        } catch (Exception e) {
            logger.error("refinedZadd Exception:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                executeBatch.initSlotCache();
            }
        }
    }

    public Map<String, Map<String, Long>> refinedZrank( Map<String, List<String>> keyValueMap){
        AtomicBoolean isOrig = new AtomicBoolean(false);
        try {
            //原始循环处理实现
            if ( executeBatch.isExecuteOrig()) {
                return executeBatch.origBatchZrank(keyValueMap);
            }
            Map<JedisPool,Collection<String>> jedisPoolKeysMap =  redisClusterHandlerCache.jedisPoolMap(keyValueMap.keySet());

            Multimap<String,String > multimap =   ArrayListMultimap.create();

            keyValueMap.entrySet().forEach(entity -> {
                String key = entity.getKey();
                entity.getValue().forEach(keyValue -> {
                    multimap.put(key, keyValue);
                });
            });

            Map<String, Map<String, Long>> resultMap = executeBatch.batchJedisPoolZrank(jedisPoolKeysMap,multimap,isOrig);
            return resultMap;

        } catch (Exception e) {
            logger.error("refinedZadd Exception:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                executeBatch.initSlotCache();
            }
        }
    }



}
