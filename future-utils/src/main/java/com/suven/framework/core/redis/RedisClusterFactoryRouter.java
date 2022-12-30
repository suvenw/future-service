package com.suven.framework.core.redis;


import com.suven.framework.core.redis.cluster.*;
import com.suven.framework.core.redis.ext.ExpansionDeal;
import com.suven.framework.core.spring.SpringBootAutoConfigSetting;
import redis.clients.util.SafeEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import redis.clients.jedis.exceptions.JedisDataException;


/**
 * @Title: RedisClusterTest.java
 * @author Joven.wang
 * @date 2016年7月26日
 * @version V1.0
 * @Description: TODO(说明)
 */

public class RedisClusterFactoryRouter {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ExpansionDeal expansionDeal;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private RedisClusterConfigSettings redisClusterConfigSettings;

	@Autowired
	private SpringBootAutoConfigSetting configSetting;

	protected final  String REDIS_RESULT_DEFAULT_OK  = "OK";

	protected boolean isOK(String result){
		return REDIS_RESULT_DEFAULT_OK.equals(result);
	}

	protected boolean isOK(Long result){
		return (null != result && result == 1) ? true : false;
	}



	public boolean isOpenWrite() {/** 是否写*/
		return redisClusterConfigSettings.getClient().isWrite();
	}

	public boolean isOpenRead() {//是否可读read
		return redisClusterConfigSettings.getClient().isRead();
	}

	public boolean isOpenDaoCache() {//dao是否开启缓存
		return redisClusterConfigSettings.getClient().isDaoOpen();
	}

	/** 目前只支持两种模式,默认是集群模式,
	 * 不是聚群模式,就是单结点模式;
	 * 暂时不做哨兵模式逻辑,
	 * 哨兵模式使用单结点模式逻辑
	 ***/
	public boolean isSentinel(){
		boolean sentinel = RedisClientModeEnum.isSentinel(redisClusterConfigSettings.getClient().getModel());
		return  sentinel;
	}
	/** 目前只支持两种模式,默认是集群模式,
	 * 不是聚群模式,就是单结点模式;
	 * 暂时不做哨兵模式逻辑,
	 * 哨兵模式使用单结点模式逻辑
	 ***/
	public boolean isClusterGroup(){
		boolean cluster = RedisClientModeEnum.isGroup(redisClusterConfigSettings.getClient().getModel());
		return  cluster;
	}
	/** 目前只支持两种模式,默认是集群模式,
	 * 不是聚群模式,就是单结点模式;
	 * 暂时不做哨兵模式逻辑,
	 * 哨兵模式使用单结点模式逻辑
	 ***/
	public boolean isCluster(){
		boolean cluster = RedisClientModeEnum.isCluster(redisClusterConfigSettings.getClient().getModel());
		return  cluster;
	}

	/**
	 *
	 * 1.如果是单个聚群模式,按RedisSentinelInterface返回
	 * 2.如果是多组聚群模式,即返回通过 redisCacheKey 做路由查找对应的分组;
	 * 3.如果是单结点或哨兵模式,按RedisSentinelInterface返回
	 * @param redisCacheKey
	 * @return
	 */
	public RedisClusterInterface getRedisClusterFactoryBykey(Object redisCacheKey ){

		//1.如果是单个聚群模式,按RedisSentinelInterface返回,默认模式,如果模式为空时,执行单聚群械 isCluster=true
		if(isCluster()){
			RedisClusterInterface jedisClient = applicationContext.getBean(RedisClusterInterface.class.getSimpleName(),RedisClusterInterface.class);
			logger.info("get redis RedisClusterInterface bean={},class={}",redisCacheKey,jedisClient.getClass());
			return jedisClient;
		}
		//1.如果是单结点或哨兵模式,按RedisSentinelInterface返回
		if (isSentinel()){
			RedisSentinelInterface jedisClient = applicationContext.getBean(RedisSentinelInterface.class.getSimpleName(), RedisSentinelInterface.class);
			logger.info("get redis RedisSentinelInterface bean={},class={}",redisCacheKey,jedisClient.getClass());
			return jedisClient;
		}
		//3.如果是多组聚群模式,即返回通过 redisCacheKey 做路由查找对应的分组;
		if(isClusterGroup()){
			RedisClusterInterface jedisClient =  this.getRedisClusterGroup(redisCacheKey);
			logger.info("get redis RedisClusterGroupInterface bean={},class={}",redisCacheKey,jedisClient.getClass());
			return jedisClient;
		}
		throw new  RuntimeException("RedisClusterFactoryRouter getRedisClusterFactory by Model is null " +
				"please in application-redis.properties input [top.redis.cluster.client.model=cluster] or(sentinel/group) ");


	}

	private RedisClusterInterface getRedisClusterGroup(Object redisCacheKey){

		int dataSource = -1;
		if(redisCacheKey instanceof String){
			dataSource = expansionDeal.getRedisInstance((String) redisCacheKey);
		}else if(redisCacheKey instanceof byte[]) {
			dataSource = expansionDeal.getRedisInstance(new String((byte[]) redisCacheKey) );
		}
		if(dataSource == -1){
			//当redis key 获取不到指的redis数据源,通过项目名称获取业务redis 数据源;
			logger.info("RedisClusterInterface getRedisClusterFactoryByKey redisCacehKey[{}]",redisCacheKey);
		}
		RedisClusterEnum groupEnum = RedisClusterEnum.findByNumType(dataSource);
		if(null == groupEnum){
			logger.error("failed to get redis cluster client for type: {}", redisCacheKey);
			throw new JedisDataException( "Cannot use Jedis when getRedisSource. Please use input param is RedisClusterEnum");
		}
		RedisClusterInterface factory = applicationContext.getBean(groupEnum.getGroupName(),RedisClusterInterface.class);
		return factory;
	}
//
//	public RedisClusterInterface getRedisClusterClient(RedisClusterEnum redisClient ){
//    	logger.info("get redis cluster bean={},class={}",redisClient.getGroupName(),RedisClusterInterface.class.getSimpleName());
//		if (isSentinel()){
//			RedisSentinelInterface jedisClient = applicationContext.getBean(RedisSentinelInterface.class.getSimpleName(), RedisSentinelInterface.class);
//			return jedisClient;
//		}
//        RedisClusterInterface factory = applicationContext.getBean(RedisClusterInterface.class.getSimpleName(),RedisClusterInterface.class);
//        return factory;
//    }
//
//
//	/**
//	 * 通过指业务规范传出redis 数据源池类型,获取数据源
//	 *
//	 * @param redisClient
//	 * @return
//	 */
//	private RedisClusterInterface getRedisClusterFactory(RedisClusterEnum redisClient) {
//
//		if (null == redisClient) {
//			throw new JedisDataException( "Cannot use Jedis when getRedisSource. input param is RedisClusterEnum is null ");
//		}
//		RedisClusterInterface server = this.getRedisClusterClient(redisClient);
//		if(null == server){
//			logger.error("failed to get redis cluster client for type: {}", redisClient);
//			throw new JedisDataException( "Cannot use Jedis when getRedisSource. Please use input param is RedisClusterEnum");
//		}
//		return server;
//	}
//
//	/**
//	 * 通过redis redisCacheKey 的标签值,获取对应的redis数据源池;
//	 *
//	 * @param redisCacheKey
//	 * @return
//	 */
//
//	public RedisClusterInterface getRedisClusterFactoryBykey(String redisCacheKey) {
////		logger.info("RedisClusterInterface getRedisClusterFactoryBykey redisCacehKey[{}]",redisCacheKey);
//		if (isSentinel()){//获取单结点的redis Bean 实例
//			RedisSentinelInterface jedisClient = applicationContext.getBean(RedisSentinelInterface.class.getSimpleName(), RedisSentinelInterface.class);
//			return jedisClient;
//		}//否则获取集群实例;
//		//通过配置获取当前项目名称;
//		int dataSource = expansionDeal.getRedisInstance(redisCacheKey);
//		if(dataSource == -1){
//			//当redis key 获取不到指的redis数据源,通过项目名称获取业务redis 数据源;
//			logger.info("RedisClusterInterface getRedisClusterFactoryBykey redisCacehKey[{}]",redisCacheKey);
//		}
//		RedisClusterEnum type = RedisClusterEnum.findByNumType(dataSource);
//		return this.getRedisClusterFactory(type);
//	}
//
//	public RedisClusterInterface getRedisClusterFactoryBykey(byte[] redisCacheKey) {
//		return getRedisClusterFactoryBykey(SafeEncoder.encode(redisCacheKey));
//	}
//
//	/**
//	 * 测试用例实现
//	 * @param type
//	 */
//	private void getClientFactoryTest(RedisClusterEnum type) {
//		RedisClusterInterface rc = getRedisClusterFactory(type);
//		rc.set("rediskey", "welcome redis 3.0");
//		System.out.println(".......getClientFactoryTest............"+ rc.get("rediskey"));
//		System.out.println(rc.toString());
//
//	}

	


}
