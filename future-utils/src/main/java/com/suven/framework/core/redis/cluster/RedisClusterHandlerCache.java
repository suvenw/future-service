package com.suven.framework.core.redis.cluster;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisClusterInfoCache;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSlotBasedConnectionHandler;
import redis.clients.util.JedisClusterCRC16;

import java.util.Collection;
import java.util.Map;

public class RedisClusterHandlerCache {

    Logger logger = LoggerFactory.getLogger(RedisClusterHandlerCache.class);

    /**
     * Redis集群缓存信息对象 Jedis提供
     */
    protected JedisClusterInfoCache clusterInfoCache;

    /**
     * Redis链接处理对象 继承于JedisClusterConnectionHandler,对其提供友好的调用方法 Jedis提供
     */
    protected JedisSlotBasedConnectionHandler connectionHandler;

    /**
     * Redis集群操作对象 Jedis提供
     */
    protected JedisCluster jedisCluster;

    /**
     * 构造方法
     * 通过JedisCluster获取JedisClusterInfoCache和JedisSlotBasedConnectionHandler
     *
     * @param jedisCluster
     */
    public RedisClusterHandlerCache init (JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
        MetaObject metaObject = SystemMetaObject.forObject(jedisCluster);
        clusterInfoCache = (JedisClusterInfoCache) metaObject.getValue("connectionHandler.cache");
        connectionHandler = (JedisSlotBasedConnectionHandler) metaObject.getValue("connectionHandler");
        return this;
    }

    public JedisCluster getJedisCluster(){
        return jedisCluster;
    }

    public JedisClusterInfoCache getClusterInfoCache() {
        return clusterInfoCache;
    }

    public JedisSlotBasedConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }


    public Map<JedisPool,Collection<String>> jedisPoolMap(Collection<String> keys){
        Multimap<JedisPool,String > map =  jedisPoolMultimap(keys);
        return map.asMap();
    }
    public Map<JedisPool,Collection<byte[]>> jedisPoolMapByte(Collection<byte[]> keys){
        Multimap<JedisPool,byte[] > map =  jedisPoolMultimapByte(keys);
        return map.asMap();
    }
    public Multimap<JedisPool,String> jedisPoolMultimap(Collection<String> keys){
        Multimap<JedisPool,String > map =   ArrayListMultimap.create();
        keys.forEach(key ->{
            try {
                // 计算key对应的slot
                int slot = JedisClusterCRC16.getSlot(key);
                // 根据slot获取对应的节点信息，将同一节点的key收在一组
                JedisPool jedisPool = this.clusterInfoCache.getSlotPool(slot);
                if (null == jedisPool) {
                    /** 刷新缓存的SlotPool */
                    this.connectionHandler.renewSlotCache();
                    jedisPool = this.clusterInfoCache.getSlotPool(slot);
                    if (jedisPool == null) {
                        logger.error("clusterPipelineGet , No reachable node in cluster for slot---{}", slot);
                        return;
                    }
                }
                map.put(jedisPool ,key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return map;

    }

    public Multimap<JedisPool,byte[]> jedisPoolMultimapByte(Collection<byte[]> keys){
        Multimap<JedisPool,byte[] > map =   ArrayListMultimap.create();
        keys.forEach(key ->{
            try {
                // 计算key对应的slot
                int slot = JedisClusterCRC16.getSlot(key);
                // 根据slot获取对应的节点信息，将同一节点的key收在一组
                JedisPool jedisPool = this.clusterInfoCache.getSlotPool(slot);
                if (null == jedisPool) {
                    /** 刷新缓存的SlotPool */
                    this.connectionHandler.renewSlotCache();
                    jedisPool = this.clusterInfoCache.getSlotPool(slot);
                    if (jedisPool == null) {
                        logger.error("clusterPipelineGet , No reachable node in cluster for slot---{}", slot);
                        return;
                    }
                }
                map.put(jedisPool ,key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return map;

    }


}
