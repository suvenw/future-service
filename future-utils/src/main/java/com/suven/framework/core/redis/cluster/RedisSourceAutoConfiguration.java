package com.suven.framework.core.redis.cluster;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




// 注：当存在 RedisClusterClientFactory.class 和 RedisClusterInterface.class 存在的情况下，该配置路径才生效
@Configuration
@ConditionalOnClass({ RedisClusterClientFactory.class, RedisClusterInterface.class})
@AutoConfigureAfter({RedisAutoConfiguration.class, RedisConfigSettings.class})
@EnableConfigurationProperties({RedisConfigSettings.class})
@ConditionalOnProperty(name = GlobalConfigConstants.REDIS_AUTO_CONFIG_ONE_ENABLED,  matchIfMissing = false)
public class RedisSourceAutoConfiguration {

   private static final Logger logger = LoggerFactory.getLogger(RedisSourceAutoConfiguration.class);

   @Autowired
   private  RedisConfigSettings redisConfigSettings;


    //注：只有当BeanFactory中不存在DataSourceInitializer类的Bean的情况下才会有效
   @ConditionalOnMissingBean
   @Bean
   public RedisClusterInterface redisClusterClientFactory() {
       RedisClusterClientFactory factory =  new RedisClusterClientFactory();
       factory.setServer(redisConfigSettings.getServers());
       factory.setPassword(redisConfigSettings.getPassword());
       factory.setSettings(redisConfigSettings);
       factory.afterPropertiesSet();
       RedisClusterInterface cluster = factory.getJedis();
       return cluster;
   }

//   @Bean
//   public RedisClusterSourceFactory redisClusterSourceFactory(){
//       RedisClusterSourceFactory factory  = new RedisClusterSourceFactory();
//       map.put(redisConfigSettings.getServerName(),redisClusterClientFactory());
//       factory.setRedisClusterMap(map);
//       factory.setDefaultRedisClusterClient("oneRedisClusterClient");
//       factory.setProjectServerName(redisConfigSettings.getServerName());
//       return factory;
//   }

}