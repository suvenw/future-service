package com.suven.framework.core.redis.cluster;




import redis.clients.jedis.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
/**
 * @author summerao
 *
 */
public interface RedisClusterInterface extends JedisCommands, BasicCommands, BinaryJedisClusterCommands, MultiKeyJedisClusterCommands, JedisClusterScriptingCommands {


		/**
	 * mget操作
	 *
	 * @param keys [key1,key2...]，key值list集合
	 * @return {key1:value1,key2:value2...}，每个入参key值获取到的对应的value，不存在为null；
	 * <br>异常情况下返回值为null或者size为0的map对象或者部分执行成功的key对应的结果
	 */
	Map<String, byte[]> refinedMget(Collection<String> keys);

	/**
	 * 多key值del操作
	 *
	 * @param keys [key1,key2...]，key值list集合
	 * @return {key1:result1,key2:result2...}，map集合，key对应入参，result为redis返回的结果；
	 * <br>异常情况下返回size为0的map对象或者部分执行成功的key对应的结果
	 */
	Map<String, Object> refinedDel(Collection<String> keys);

	/**
	 * mset操作
	 *
	 * @param keysvalues {key1:value1,key2:value2...}，key-value集合
	 * @return	{key1:result1,key2:result2...}，map集合，key对应入参，result为redis返回的结果；
	 * <br>异常情况下返回size为0的map对象或者部分执行成功的key对应的结果
     */
	Map<String, Object> refinedMset(Map<String, byte[]> keysvalues);

	/**
	 * mset操作，同时设置失效时间
	 *
	 * @param keysvalues {key1:value1,key2:value2...}，key-value集合
	 * @param time 失效时间，单位秒
	 * @return {key1:result1,key2:result2...}，map集合，key对应入参，result为redis返回的结果；
	 * <br>异常情况下返回size为0的map对象或者部分执行成功的key对应的结果
	 */
	Map<String, Object> refinedMset(Map<String, byte[]> keysvalues, Integer time);



	/**
	 * 设置指定key,在有效时间里增加、或减少 指定值；
	 * @param key
	 * @param expireTime 失效时间，单位秒
	 * @return
	 */
	long refinedIncrByExpire(String key, long value, int expireTime,boolean refreshTime);


	/**
	 * 批量zadd操作
	 *
	 * @param keysvalues {key1:{mem11:score11,mem12:score12...},key2:{mem21:score21,mem22:score22...}...}
	 * @return {key1:result1,key2:result2...}，map集合，key对应入参，result为redis返回的结果；
	 * <br>异常情况下返回size为0的map对象或者部分执行成功的key对应的结果
     */
	Map<String, Long> refinedZadd(Map<String, Map<String, Double>> keysvalues);

	/**
	 * 批量zrank
	 *
	 * @param keysvalues {key1:[mem11,mem12...],key2:[mem21,mem22...]...}
	 * @return {key1:{mem11:index11,mem12:index12...},key2:{mem21:index21,mem22:index22...}}，map集合，key对应入参，value为map集合，可以为成员，value为对应索引
	 * <br>异常情况下返回size为0的map对象或者部分执行成功的key对应的结果
     */
	Map<String, Map<String, Long>> refinedZrank(Map<String, List<String>> keysvalues);

	/**
	 * 批量expire
	 *
	 * @param keysvalues {key1:time1,key2:time2...}
     */
	Map<String, Object> refinedExpire(Map<String, Integer> keysvalues);



}
