//package com.suven.framework.core.redis.cluster;
//
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.reflection.SystemMetaObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.*;
//import redis.clients.util.JedisClusterCRC16;
//
//import java.util.*;
//
///**
// * 基于JedisCluster实现管道的使用
// * 核心对象：JedisClusterInfoCache和JedisSlotBasedConnectionHandler
// */
//public class RedisClusterPipeline {
//    Logger logger = LoggerFactory.getLogger(RedisClusterInvokeMethod.class);
//    /**
//     * Redis集群缓存信息对象 Jedis提供
//     */
//    protected JedisClusterInfoCache clusterInfoCache;
//
//    /**
//     * Redis链接处理对象 继承于JedisClusterConnectionHandler,对其提供友好的调用方法 Jedis提供
//     */
//    protected JedisSlotBasedConnectionHandler connectionHandler;
//
//    /**
//     * Redis集群操作对象 Jedis提供
//     */
//    protected JedisCluster jedisCluster;
//
//    /**
//     * 构造方法
//     * 通过JedisCluster获取JedisClusterInfoCache和JedisSlotBasedConnectionHandler
//     *
//     * @param jedisCluster
//     */
//    public RedisClusterPipeline(JedisCluster jedisCluster) {
//        this.jedisCluster = jedisCluster;
//        MetaObject metaObject = SystemMetaObject.forObject(jedisCluster);
//        clusterInfoCache = (JedisClusterInfoCache) metaObject.getValue("connectionHandler.cache");
//        connectionHandler = (JedisSlotBasedConnectionHandler) metaObject.getValue("connectionHandler");
//
//    }
//
//    public JedisCluster getJedisCluster(){
//        return jedisCluster;
//    }
//
//    public JedisClusterInfoCache getClusterInfoCache() {
//        return clusterInfoCache;
//    }
//
//    public JedisSlotBasedConnectionHandler getConnectionHandler() {
//        return connectionHandler;
//    }
//
//    /**
//     * 批量get key
//     *
//     * @param keys
//     */
//    public Map<String, Object> clusterPipelineGet(List<String> keys) {
//        //要返回的结果
//        Map<String, Object> result = new HashMap<>();
//        //节点对应keys分组
//        Map<JedisPool, List<String>> node2keys = new HashMap<>();
//
//        for (String key : keys) {
//            // 计算key对应的slot
//            int slot = JedisClusterCRC16.getSlot(key);
//            // 根据slot获取对应的节点信息，将同一节点的key收在一组
//            JedisPool jedisPool = this.clusterInfoCache.getSlotPool(slot);
//            if (null == jedisPool) {
//                /** 刷新缓存的SlotPool */
//                this.connectionHandler.renewSlotCache();
//                jedisPool = this.clusterInfoCache.getSlotPool(slot);
//                if (jedisPool == null) {
//                    logger.error("clusterPipelineGet , No reachable node in cluster for slot---{}", slot);
//                    continue;
//                }
//            }
//            if (node2keys.containsKey(jedisPool)) {
//                node2keys.get(jedisPool).add(key);
//            } else {
//                List<String> list = new ArrayList<>();
//                list.add(key);
//                node2keys.put(jedisPool, list);
//            }
//        }
//        if (node2keys.isEmpty()) {
//            logger.error("clusterPipelineGet , node2keys is empty");
//            return result;
//        }
//        // 分组执行
//        for (Map.Entry<JedisPool, List<String>> group : node2keys.entrySet()) {
//            Jedis jedis = group.getKey().getResource();
//            Pipeline pipeline = jedis.pipelined();
//            // 执行本组keys
//            List<String> values = group.getValue();
//            for (String carNo : values) {
//                pipeline.get(carNo);
//            }
//            List<Object> pipelineReturns = pipeline.syncAndReturnAll();
//            //组装redis返回结果，以免请求的key和返回的values顺序错乱
//            for (int i = 0; i < values.size(); i++) {
//                result.put(values.get(i), pipelineReturns.get(i));
//            }
//            jedis.close();
//        }
//        return result;
//    }
//
//}