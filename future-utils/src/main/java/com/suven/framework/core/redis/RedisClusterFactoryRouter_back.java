//package com.suven.framework.core.redis;
//
//
//import com.suven.framework.core.redis.cluster.*;
//import com.suven.framework.core.redis.ext.ExpansionDeal;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import redis.clients.jedis.exceptions.JedisDataException;
//import redis.clients.util.SafeEncoder;
//
//import static com.suven.framework.core.redis.cluster.RedisConfig.REDIS_CLUSTER_CLIENT_NAME;
//import static com.suven.framework.core.redis.cluster.RedisConfig.REDIS_SENTINEL_CLIENT_NAME;
//
//
///**
// * @Title: RedisClusterTest.java
// * @author Joven.wang
// * @date 2016年7月26日
// * @version V1.0
// * @Description: TODO(说明)
// */
//
//public class RedisClusterFactoryRouter_back {
//
//	protected Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private ExpansionDeal expansionDeal;
//
//	@Autowired
//	private ApplicationContext applicationContext;
//
//	@Autowired
//	private RedisClusterConfigSettings redisClusterConfigSettings;
//
//
//	protected final  String REDIS_RESULT_DEFAULT_OK  = "OK";
//	protected final String REDIS_RESULT_MESSAGE = "单结点暂时不支持此方法";
//
//	protected boolean isOK(String result){
//		return REDIS_RESULT_DEFAULT_OK.equals(result);
//	}
//
//	protected boolean isOK(Long result){
//		return (null != result && result == 1) ? true : false;
//	}
//
//	protected long isLong(Long result){
//		return (null == result ? 0 : result);
//	}
//
//
//
//	public boolean isOpenWrite() {/** 是否写*/
//		return redisClusterConfigSettings.getClient().isWrite();
//	}
//
//	public boolean isOpenRead() {//是否可读read
//		return redisClusterConfigSettings.getClient().isRead();
//	}
//
//	public boolean isOpenDaoCache() {//dao是否开启缓存
//		return redisClusterConfigSettings.getClient().isDaoOpen();
//	}
//
//	/** 目前只支持两种模式,默认是集群模式,
//	 * 不是聚群模式,就是单结点模式;
//	 * 暂时不做哨兵模式逻辑,
//	 * 哨兵模式使用单结点模式逻辑
//	 ***/
//	public boolean isSentinel(){
//		boolean cluster = RedisClientModeEnum.isCluster(redisClusterConfigSettings.getClient().getModel());
//		return  !cluster;
//	}
//
//
//	public RedisSentinelInterface getRedisSentinel( String key){
//			RedisSentinelInterface jedisClient = applicationContext.getBean(REDIS_SENTINEL_CLIENT_NAME, RedisSentinelInterface.class);
//			return jedisClient;
//	}
//
//	public RedisClusterInterface getRedisCluster( String key){
//		  RedisClusterInterface jedisClient = applicationContext.getBean(REDIS_CLUSTER_CLIENT_NAME,RedisClusterInterface.class);
//		return jedisClient;
//	}
//	public RedisSentinelInterface getRedisSentinel( byte[] key){
//		RedisSentinelInterface jedisClient = applicationContext.getBean(REDIS_SENTINEL_CLIENT_NAME, RedisSentinelInterface.class);
//		return jedisClient;
//	}
//
//	public RedisClusterInterface getRedisCluster( byte[] key){
//		RedisClusterInterface jedisClient = applicationContext.getBean(RedisClusterEnum.REDIS_GROUP_DEFAULT.getGroupName(),RedisClusterInterface.class);
//		return jedisClient;
//	}
//
//
//
//
//	private RedisInterface getRedisClusterClient(RedisClusterEnum redisClient ){
//    	logger.info("get redis cluster bean={},class={}",redisClient.getGroupName(),RedisClusterInterface.class.getSimpleName());
//
//        RedisClusterInterface factory = applicationContext.getBean(redisClient.getGroupName(),RedisClusterInterface.class);
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
//	private RedisInterface getRedisClusterFactory(RedisClusterEnum redisClient) {
//
//		if (null == redisClient) {
//			throw new JedisDataException( "Cannot use Jedis when getRedisSource. input param is RedisClusterEnum is null ");
//		}
//		RedisInterface server = this.getRedisClusterClient(redisClient);
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
//	public RedisInterface getRedisClusterFactoryByKey(String redisCacheKey) {
////		logger.info("RedisClusterInterface getRedisClusterFactoryBykey redisCacehKey[{}]",redisCacheKey);
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
//	public RedisInterface getRedisClusterFactoryByKey(byte[] redisCacheKey) {
//		return getRedisClusterFactoryByKey(SafeEncoder.encode(redisCacheKey));
//	}
//
//	/**
//	 * 测试用例实现
//	 * @param type
//	 */
//	private void getClientFactoryTest(RedisClusterEnum type) {
//		RedisInterface rc = getRedisClusterFactory(type);
////		rc.set("rediskey", "welcome redis 3.0");
////		System.out.println(".......getClientFactoryTest............"+ rc.get("rediskey"));
//		System.out.println(rc.toString());
//
//	}
//
//
//
//
//}
