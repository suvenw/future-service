//package com.suven.framework.core.redis;
//
//
//import com.suven.framework.core.redis.cluster.RedisClusterInterface;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
//
///**
// * @Title: RedisClusterTest.java
// * @author Joven.wang
// * @date   2016年7月26日
// * @version V1.0
// * @Description: TODO(说明)
// */
//
//@Component
//public class RedisClusterTest {
//
//
////	@Autowired
//	public RedisClusterFactoryRouter redisClientRouter;
////
//
//	 @PostConstruct
//	public void getClientFactory() {
//		RedisClusterInterface redis = redisClientRouter.getRedisClusterFactory(RedisClusterEnum.REDIS_GROUP_DEFAULT);
//		redis.set("haha","hah");
//		 System.out.println(".......mfxRedisClusterClient............"+redis.get("haha"));
//		 System.out.println(redis.toString());
//
//		 redis.set("show", "show RedisClusterServer");
//		 System.out.println("..........showRedisClusterClient........."+redis.get("show"));
////		 List<String> list = Arrays.asList("peter", "anna", "mike", "xenia");
////		 List<String> mlist = redis.mget("haha","show");
//		 redis.setex("showTime", 12, "showTime 12");
//
////		 redis.z
//
//	}
//
//
//
//
//
//
//
//}
