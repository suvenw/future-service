package com.suven.framework.core.redis;


import com.suven.framework.core.redis.cluster.RedisClusterInterface;
import com.suven.framework.core.redis.cluster.RedisConfigSettings;
import com.suven.framework.core.redis.ext.ExpansionDeal;
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
	private RedisConfigSettings redisConfigSettings;

	protected final  String REDIS_RESULT_DEFAULT_OK  = "OK";

	protected boolean isOK(String result){
		return REDIS_RESULT_DEFAULT_OK.equals(result);
	}

	protected boolean isOK(Long result){
		return (null != result && result == 1) ? true : false;
	}



	public boolean isOpenWrite() {/** 是否写*/
		return redisConfigSettings.getClient().isWrite();
	}

	public boolean isOpenRead() {//是否可读read
		return redisConfigSettings.getClient().isRead();
	}

	public boolean isOpenDaoCache() {//dao是否开启缓存
		return redisConfigSettings.getClient().isDaoOpen();
	}



	private RedisClusterInterface getRedisClusterClient(RedisClusterEnum redisClient ){
    	logger.info("get redis cluster bean={},class={}",redisClient.getGroupName(),RedisClusterInterface.class.getSimpleName());
        RedisClusterInterface factory = applicationContext.getBean(redisClient.getGroupName(),RedisClusterInterface.class);
        return factory;
    }


	/**
	 * 通过指业务规范传出redis 数据源池类型,获取数据源
	 * 
	 * @param type
	 * @return
	 */
	public RedisClusterInterface getRedisClusterFactory(RedisClusterEnum type) {
		if (null == type) {
			throw new JedisDataException( "Cannot use Jedis when getRedisSource. input param is RedisClusterEnum is null ");
		}
		RedisClusterInterface server = this.getRedisClusterClient(type);
		if(null == server){
			logger.error("failed to get redis cluster client for type: {}", type);
			throw new JedisDataException( "Cannot use Jedis when getRedisSource. Please use input param is RedisClusterEnum");
		}
		return server;
	}

	/**
	 * 通过redis redisCacheKey 的标签值,获取对应的redis数据源池;
	 * 
	 * @param redisCacheKey
	 * @return
	 */

	public RedisClusterInterface getRedisClusterFactoryBykey(String redisCacheKey) {
//		logger.info("RedisClusterInterface getRedisClusterFactoryBykey redisCacehKey[{}]",redisCacheKey);
		//通过配置获取当前项目名称;
		int dataSource = expansionDeal.getRedisInstance(redisCacheKey);
		if(dataSource == -1){
			//当redis key 获取不到指的redis数据源,通过项目名称获取业务redis 数据源;
			logger.info("RedisClusterInterface getRedisClusterFactoryBykey redisCacehKey[{}]",redisCacheKey);
		}
		RedisClusterEnum type = RedisClusterEnum.findByNumType(dataSource);
		return this.getRedisClusterFactory(type);
	}

	public RedisClusterInterface getRedisClusterFactoryBykey(byte[] redisCacheKey) {
		return getRedisClusterFactoryBykey(SafeEncoder.encode(redisCacheKey));
	}

	/**
	 * 测试用例实现
	 * @param type
	 */
	private void getClientFactoryTest(RedisClusterEnum type) {
		RedisClusterInterface rc = getRedisClusterFactory(type);
		rc.set("rediskey", "welcome redis 3.0");
		System.out.println(".......getClientFactoryTest............"+ rc.get("rediskey"));
		System.out.println(rc.toString());

	}

	


}
