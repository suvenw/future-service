package com.suven.framework.core.redis.cluster;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.JedisAskDataException;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisMovedDataException;
import redis.clients.util.SafeEncoder;

import java.net.ConnectException;
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
 * @Description: (说明) 通过指定JedisPool执行管道批令,实现同结点的key的集合查询;
 *  性能最优,建议优先使用,从而实现批量处理数据功能
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class RedisClusterExecutePipeline extends RedisClusterExecuteOrig {

    private Logger logger = LoggerFactory.getLogger(RedisClusterExecutePipeline.class);

    public RedisClusterExecutePipeline(JedisCluster jedisCluster) {
        super(jedisCluster);
        this.jedisCluster =  jedisCluster;
    }
    protected void jedisPoolResource(JedisPool jedisPool,Jedis jedis){
        jedisPool.returnResource(jedis);
    }



    /**
     * 指定某个槽位,批量删除key
     * @param jedisPool 指定某个槽位
     * @param keys 批量删除key
     * @return
     */
    public Map<String, Object> batchPipelineDelete(JedisPool jedisPool, Collection<String> keys) {
        Jedis jedis = null;
        try {
            List<String > keyList = new ArrayList<>(keys);
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            keyList.forEach( key -> pipeline.del(SafeEncoder.encode(key)));
            List<Object> resultList = pipeline.syncAndReturnAll();
            Map<String,Object> resultMap = new LinkedHashMap<>(keys.size());
            int i = 0;
            for (String key : keyList) {
                Object value = resultList.get(i);
                value = this.isJedisDataException(value);
                resultMap.put(key, value);
                i++;
            }

            this.convertResultMap( keyList,resultList,resultMap);

            return resultMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                renewSlotCache.put(_key, _value);
            }
            logger.error("execute Pipeline Batch Delete  error.e:{}", e);
        } finally {
            if (jedis != null) {
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }

    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPool key与槽分组集合
     * @param keys 是否需要使用原子性方法
     * @return
     */
    //现有mget方案是定位到对应Redis节点采用管道实现，但仍需考虑2种场景
    //1、节点管理槽位关系变更
    //2、主节点挂了，也无从节点介入，槽位无节点管理
    public Map<String, byte[]> batchPipelineGet(JedisPool jedisPool, Collection<String> keys) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            List<String> keyList =  new ArrayList<>(keys);
            keyList.forEach( key -> pipeline.get(SafeEncoder.encode(key)));

            List<Object> resultList = pipeline.syncAndReturnAll();
            Map<String, byte[]> resultMap = new HashMap<>(keyList.size());

            this.convertResultMap(keyList, resultList,resultMap);

            return resultMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
               this.initSlotCache();
            }
            logger.error("mget pipeline error.e:{}", e);
        } finally {
            if (jedis != null) {
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }



    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPool key与槽分组集合
     * @param keyValueMap 是否需要使用原子性方法
     * @return
     */
    //现有mget方案是定位到对应Redis节点采用管道实现，但仍需考虑2种场景
    //1、节点管理槽位关系变更
    //2、主节点挂了，也无从节点介入，槽位无节点管理
    protected Map<String, String> batchPipelineSet(JedisPool jedisPool, Map<String,byte[]> keyValueMap, Integer secondTime ) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            List<String> keyList =  new ArrayList<>(keyValueMap.keySet());

            Map<String, String> resultMap = new HashMap<>(keyList.size());
            List<Object> resultList = new ArrayList<>();

            keyList.forEach( key ->{
                Response<String> response = null;
                byte[]  value = keyValueMap.get(key);
                byte[] keyBytes =  SafeEncoder.encode(key);
                if (secondTime == null || secondTime == 0) {
                    response = pipeline.set(keyBytes, value);
                } else {
                    response = pipeline.setex(keyBytes, secondTime, value);
                }
                resultList.add(response);

            } );
            pipeline.sync();

           this.convertResultMap( keyList, resultList, resultMap);

           return resultMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                this.initSlotCache();
            }
            logger.error(" redis batch Pipeline Set  error.e:{}", e);
        } finally {
            if (jedis != null) {
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }

    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPool key与槽分组集合
     * @param keyValueMap 在相同的槽内执行的执行map
     * @return
     */
    public Map<String, Long> batchPipelineZadd(JedisPool jedisPool, Map<String, Map<String, Double>> keyValueMap) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();

            List<String> keyList =  new ArrayList<>(keyValueMap.keySet());

            Map<String, Long> resultMap = new HashMap<>(keyList.size());

            List<Object> resultList = new ArrayList<>();

            for(Map.Entry<String, Map<String, Double>> entry : keyValueMap.entrySet()) {
                Response<Long> response = pipeline.zadd(entry.getKey(), entry.getValue());
                resultList.add( response);
            }
            pipeline.sync();

            this.convertResultMap(keyList,resultList,resultMap);

            return resultMap;

        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                this.initSlotCache();
            }
            logger.error("zadd pipeline error.e:{}", e);
//            Cat.logError(e);
        } finally {
            if (jedis != null) {
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }

    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPool key与槽分组集合
     * @param keyValueMap 是否需要使用原子性方法
     * @return
     */

    public   Map<String, Map<String, Long>> batchPipelineZrank(
            JedisPool jedisPool, Multimap<String,String> keyValueMap) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();

            Map<String, Map<String, Long>> resultMap = new HashMap<>();
            Multimap<String, Generic<String,Response<Long>>> resultMultimap = ArrayListMultimap.create();
            Map<String, Collection<String>> map = keyValueMap.asMap();
            for(String key : map.keySet()) {
                Collection<String> list = keyValueMap.get(key);
                for(String member : list) {
                    Response<Long> response = pipeline.zrank(key, member);
                    resultMultimap.put(key, Generic.build(member,response));
                }
            }
            pipeline.sync();

            for(String key : keyValueMap.keySet()) {
                Collection<Generic<String,Response<Long>>> genericsList = resultMultimap.get(key);
                if( null == genericsList || genericsList.isEmpty()){
                    continue;
                }
                Map<String,Long> memberValueMap = new HashMap<>();
                genericsList.forEach( generics ->{
                    memberValueMap.put(generics.getKey(),generics.getValue().get());
                });
                resultMap.put(key,memberValueMap);
            }
            return resultMap;

        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                this.initSlotCache();
            }
            logger.error("zadd pipeline error.e:{}", e);
        } finally {
            if (jedis != null) {
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }
    /**
     *  批量删除key的方法,通过多渠位聚群删除
     * @param jedisPool key与槽分组集合
     * @param jedisPoolKeyList 是否需要使用原子性方法
     * @return
     */

    protected    Map<String, Map<String, Long>> batchPipelineZrank(
            JedisPool jedisPool,  Collection<String> jedisPoolKeyList,
            Multimap<String,String> keyValueMap) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();

            Map<String, Map<String, Long>> resultMap = new HashMap<>();
            Multimap<String, Generic<String,Response<Long>>> resultMultimap = ArrayListMultimap.create();;
            for(String key : jedisPoolKeyList) {
                Collection<String> list = keyValueMap.get(key);
                for(String member : list) {
                    Response<Long> response = pipeline.zrank(key, member);
                    resultMultimap.put(key, Generic.build(member,response));
                }
            }
            pipeline.sync();

            for(String key : jedisPoolKeyList) {
                Collection<Generic<String,Response<Long>>> genericsList = resultMultimap.get(key);
                if( null == genericsList || genericsList.isEmpty()){
                    continue;
                }
                Map<String,Long> memberValueMap = new HashMap<>();
                genericsList.forEach( generics ->{
                    memberValueMap.put(generics.getKey(),generics.getValue().get());
                });
                resultMap.put(key,memberValueMap);
            }
            return resultMap;

        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                this.initSlotCache();
            }
            logger.error("zadd pipeline error.e:{}", e);
        } finally {
            if (jedis != null) {
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }
    /**
     * 泛型类的定义
     * @param <T> 泛型标识 --- 类型形参；
     *           T 创建对象的时候，指定具体的数据类型
     */
    protected static class Generic<T,V> {
        private T key;
        private V value;

        public Generic(T key, V value) {
            this.key = key;
            this.value = value;
        }

        public static <T,V> Generic build(T key, V value){
            return new Generic(key,value);
        }

        public T getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
    }

}
