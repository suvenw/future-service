package com.suven.framework.core.redis.cluster;




import redis.clients.jedis.*;


/**
 * @author summerao
 *
 */
public interface RedisSentinelInterface extends BinaryJedisCommands,MultiKeyCommands,RedisClusterInterface {

}
