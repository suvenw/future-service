package com.suven.framework.core.redis.cluster;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Multimap;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Response;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @Title: RedisClusterExecuteOrig.java
 * @author Joven.wang
 * @date   2022-09-29 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) 通过循环执行redis api 查询,从且得到期结果的数据,性能差,不太建议使用,
 *  只为保证数据功能实现
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class RedisClusterExecuteOrig {

    protected static final Cache<String, String> renewSlotCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();
    protected final static String _key = "renew";
    protected final static String _value = "1";

    protected JedisCluster jedisCluster;
    public RedisClusterExecuteOrig(JedisCluster jedisCluster) {
        this.jedisCluster =  jedisCluster;
    }

    public boolean isExecuteOrig(){
        if (_value.equals(renewSlotCache.getIfPresent(_key))) {
                return true;
        }
        return false;
    }

    public boolean initSlotCache(){
        renewSlotCache.put(_key,_value);
        return true;
    }

    public Object isJedisDataException(Object value){
        if (value != null && value instanceof JedisDataException) {
            this.initSlotCache();
            return null;
        }
        return value;
    }

    protected void convertResultMap(List<String> keyList,  List<Object> resultList,Map resultMap) {
        int i = 0;
        for (String key : keyList) {
            Object value = resultList.get(i);
            value = this.isJedisDataException(value);
            if (value instanceof Response) {
                Response data = (Response)value;
                resultMap.put(key,data.get() );
            }else if (value instanceof String) {
                resultMap.put(key,value );
            } else if(value instanceof byte[]) {
                byte[] data = (byte[]) value;
                resultMap.put(key, data);
            }else {
                resultMap.put(key, value);
            }
            i++;
        }
    }



    protected Map<String, byte[]> origBatchGet(Collection <String> keyList) {
        Map<String, byte[]> resultMap = new HashMap<>(keyList.size());
        for (Object key : keyList) {
            if (key instanceof String) {
                String k = (String) key;
                byte[] value = jedisCluster.get(k.getBytes());
                resultMap.put(k,value );
            } else {
                byte[] k = (byte[]) key;
                byte[] value = jedisCluster.get(k);
                resultMap.put(new String(k), value);
            }
        }
        return resultMap;
    }

    /**
     * 逐个逐个删除key
     *
     * @param keyList
     * @return
     */
    protected Map<String, Object> origBatchDelete(Collection<String> keyList) {
        Map<String, Object> resMap = new HashMap<>(keyList.size());
        keyList.forEach(key ->{
            String value = jedisCluster.get(key);
            resMap.put(key, value);
        });
        return resMap;
    }
    protected Map<String, byte[]> origDeleteByte(Collection<byte[]> keyList) {
        Map<String, byte[]> resMap = new HashMap<>(keyList.size());
        keyList.forEach(key ->{
            byte[] value = jedisCluster.get(key);
            resMap.put(new String(key), value);
        });
        return resMap;
    }

    /**
     * 效率最差做法，在找不到key对应的hash槽位时这么做
     *
     * @param keysvalues
     * @param time
     * @return
     */
    protected Map<String, String > origBatchSet(Map<String, byte[]> keysValuesMap, Integer secondTime) {
        Map<String,String> resultMap = new HashMap<>();
        keysValuesMap.keySet().forEach( key ->{
            String value;
            if (secondTime == null || secondTime == 0) {
                value = jedisCluster.set(key.getBytes(), keysValuesMap.get(key));
            } else {
                value = jedisCluster.setex(key.getBytes(), secondTime, keysValuesMap.get(key));
            }
            resultMap.put(key,value );
        });
        return resultMap;
    }

    protected Map<String, Long> origBatchZadd(Map<String, Map<String, Double>> keyValueMap) {
        Map<String, Long> resultMap = new HashMap<>();
        for (String key : keyValueMap.keySet()) {
            Map<String, Double> values = keyValueMap.get(key);
            Long value =   jedisCluster.zadd(key, values);
            resultMap.put(key, value);
        }
        return resultMap;
    }


    protected Map<String, Map<String, Long>> origBatchZrank( Map<String, List<String>> keyValueMap) {
        Map<String, Map<String, Long>> resultMap = new HashMap<>();
        for (String key : keyValueMap.keySet()) {
            List<String> list = keyValueMap.get(key);
            Map<String, Long> map = new HashMap<String, Long>();
            for(String mem : list) {
                Long index = jedisCluster.zrank(key, mem);
                map.put(key, index);
            }
            resultMap.put(key, map);
        }
        return resultMap;
    }

    protected Map<String, Map<String, Long>> origBatchZrank(Collection<String> keyValueList, Multimap<String,String > multimap) {
        Map<String, Map<String, Long>> resultMap = new HashMap<>();
        for (String key : keyValueList) {
            Collection<String> list = multimap.get(key);
            Map<String, Long> map = new HashMap<String, Long>();
            for(String mem : list) {
                Long index = jedisCluster.zrank(key, mem);
                map.put(key, index);
            }
            resultMap.put(key, map);
        }
        return resultMap;
    }



    protected long origBatchIncr(String key, int time) {
        long val = jedisCluster.incr(key);
        jedisCluster.setex(key, time, jedisCluster.get(key));
        return val;
    }

    protected long origBatchDecr(String key, int time) {
        long val = jedisCluster.decr(key);
        jedisCluster.setex(key, time, jedisCluster.get(key));
        return val;
    }

    protected Map<String, Object> origBatchExpire(Map<String, Integer> keysvalues) {
        Map<String, Object> resultMap = new HashMap<>();
        for (String key : keysvalues.keySet()) {
            resultMap.put(key, jedisCluster.expire(key, keysvalues.get(key)));
        }
        return resultMap;
    }
}
