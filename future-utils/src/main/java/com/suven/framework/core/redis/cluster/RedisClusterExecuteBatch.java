package com.suven.framework.core.redis.cluster;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @Title: RedisClusterExecutePipeline.java
 * @author Joven.wang
 * @date   2022-09-29 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) 通过指定多管理JedisPool执行管道批令,实现同结点的key的集合查询;
 *  性能最优,建议优先使用,从而实现批量处理数据功能
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class RedisClusterExecuteBatch extends RedisClusterExecutePipeline {

    private Logger logger = LoggerFactory.getLogger(RedisClusterExecuteBatch.class);

    public RedisClusterExecuteBatch(JedisCluster jedisCluster) {
        super(jedisCluster);
        this.jedisCluster =  jedisCluster;
    }
    protected void jedisPoolResource(JedisPool jedisPool,Jedis jedis){
        jedisPool.returnResource(jedis);
    }

    /**
     * 批量删除key的方法,通过多渠位聚群删除
     *  **/
    public Map<String, Object> batchJedisPoolDelete(Map<JedisPool,Collection<String>> jedisPoolKeysMap, AtomicBoolean isOrig  ) {
        Map<String, Object> resultMap = new HashMap<>();
        if(jedisPoolKeysMap == null){
            return resultMap;
        }
        try {
            for (Map.Entry<JedisPool, Collection<String>> entry : jedisPoolKeysMap.entrySet()) {
                Map<String, Object> map = batchPipelineDelete(entry.getKey(), entry.getValue());
                if (map == null) {
                    map = this.origBatchDelete(entry.getValue());
                    isOrig.set(true);
                }
                resultMap.putAll(map);
            }
        } catch (Exception e) {
            logger.error("executeDel e:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                this.initSlotCache();
            }
        }
        return resultMap;
    }


    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPoolKeysMap key与槽分组集合
     * @param isOrig 是否需要使用原子性方法
     * @return
     */
    public Map<String, byte[]> batchJedisPoolGet(Map<JedisPool,Collection<String>> jedisPoolKeysMap, AtomicBoolean isOrig  ) {
        Map<String, byte[]> resultMap = new HashMap<>();
        if(jedisPoolKeysMap == null){
            return resultMap;
        }
        try {
            for (Map.Entry<JedisPool, Collection<String>> entry : jedisPoolKeysMap.entrySet()) {
                Map<String, byte[]> map = batchPipelineGet(entry.getKey(), entry.getValue());
                if (map == null) {
                    map = this.origBatchGet(entry.getValue());
                    isOrig.set(true);
                }
                resultMap.putAll(map);
            }
        } catch (Exception e) {
            logger.error("executeDel e:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                this.initSlotCache();
            }
        }
        return resultMap;
    }



    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPoolKeysMap key与槽分组集合
     * @param isOrig 是否需要使用原子性方法
     * @return
     */
    public Map<String,String> batchJedisPoolSet(Map<JedisPool,Collection<String>> jedisPoolKeysMap,
                                                  Map<String,byte[]>  keyValueMap,
                                                  Integer secondTime , AtomicBoolean isOrig  ) {
        Map<String, String> resultMap = new HashMap<>();
        if(jedisPoolKeysMap == null){
            return resultMap;
        }
        try {
            for (Map.Entry<JedisPool, Collection<String>> entry : jedisPoolKeysMap.entrySet()) {
                Map<String,byte[]> keyValue = new HashMap<>();
                entry.getValue().forEach(key ->{
                    byte[] data =  keyValueMap.get(key);
                    keyValue.put(key,data);
                });
                Map<String, String> map = batchPipelineSet(entry.getKey(), keyValue,secondTime);
                if (map == null) {
                    map = this.origBatchSet(keyValue,secondTime);
                    isOrig.set(true);
                }
                resultMap.putAll(map);

            }
        } catch (Exception e) {
            logger.error("executeDel e:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                this.initSlotCache();
            }
        }
        return resultMap;
    }

    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPoolKeysMap key与槽分组集合
     * @param isOrig 是否需要使用原子性方法
     * @return
     */
    public Map<String,Long> batchJedisPoolZadd(Map<JedisPool,Collection<String>> jedisPoolKeysMap,
                                                 Map<String, Map<String, Double>>  keyValueMap,AtomicBoolean isOrig  ) {
        Map<String, Long> resultMap = new HashMap<>();
        if(jedisPoolKeysMap == null){
            return resultMap;
        }
        try {
            for (Map.Entry<JedisPool, Collection<String>> entry : jedisPoolKeysMap.entrySet()) {
                Map<String, Map<String, Double>> keyValue = new HashMap<>();
                entry.getValue().forEach(key ->{
                    Map<String, Double> data =  keyValueMap.get(key);
                    keyValue.put(key,data);
                });
                Map<String, Long> map = batchPipelineZadd(entry.getKey(), keyValue);
                if (map == null) {
                    map = this.origBatchZadd(keyValue);
                    isOrig.set(true);
                }
                resultMap.putAll(map);

            }
        } catch (Exception e) {
            logger.error("executeDel e:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                this.initSlotCache();
            }
        }
        return resultMap;
    }




    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPoolKeysMap key与槽分组集合
     * @param isOrig 是否需要使用原子性方法
     * @return
     */
    public Map<String, Map<String, Long>> batchJedisPoolZrank(Map<JedisPool,Collection<String>> jedisPoolKeysMap,
                                                              Multimap<String,String > multimap, AtomicBoolean isOrig  ) {
        Map<String, Map<String, Long>> resultMap = new HashMap<>();
        if(jedisPoolKeysMap == null){
            return resultMap;
        }
        try {
            for (Map.Entry<JedisPool, Collection<String>> entry : jedisPoolKeysMap.entrySet()) {
                Collection<String> jedisPoolKeyList =  entry.getValue();
                Map<String, Map<String, Long>> map = batchPipelineZrank(entry.getKey(), jedisPoolKeyList, multimap);
                if (map == null) {
                    map = this.origBatchZrank(jedisPoolKeyList,multimap);
                    isOrig.set(true);
                }
                resultMap.putAll(map);

            }
        } catch (Exception e) {
            logger.error("executeDel e:{}", e);
            throw e;
        } finally {
            if (isOrig.get()) {
                this.initSlotCache();
            }
        }
        return resultMap;
    }


}
