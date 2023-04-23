package com.suven.framework.core.redis.cluster;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @Title: RedisAutoConfiguration.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) Redis 配置启动加载实现类,默认是自动启动,可以在配置文件关闭:top.redis.cluster.enabled=false;
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


@ConditionalOnWebApplication
@Configuration
@ConditionalOnClass({ RedisSentinelFactory.class, RedisClusterInterface.class})
@EnableConfigurationProperties(RedisSentinelConfigSettings.class)
@ConditionalOnProperty(name = GlobalConfigConstants.SPRING_REDIS_AUTO_CONFIG_ENABLED,  matchIfMissing = false)
public class RedisSentinelAutoConfiguration implements RedisConfig, EnvironmentAware, BeanDefinitionRegistryPostProcessor {
	private final Logger logger = LoggerFactory.getLogger(RedisSentinelAutoConfiguration.class);

	private RedisSentinelConfigSettings redisConfigSettings;
	private Binder binder;

	@Override
	public void setEnvironment(Environment environment) {
 		binder = Binder.get(environment);
 		try {
            BindResult<RedisSentinelConfigSettings> result = binder.bind(GlobalConfigConstants.SPRING_REDIS_AUTO_CONFIG, RedisSentinelConfigSettings.class);//.get();
            if (null == result ||  !result.isBound()) {
                logger.warn("init RedisConfigSettings parameter from RedisClientAutoConfiguration result is null,"
                        + "spring.redis [{}]",GlobalConfigConstants.SPRING_REDIS_AUTO_CONFIG);
            }
            redisConfigSettings = result.get();
		}catch (Exception e){
			logger.warn("RedisConfigSettings init param Exception e[{}]", e);
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}


	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

		if(redisConfigSettings == null || redisConfigSettings.getHost() == null || "".equals( redisConfigSettings.getHost() )){
			logger.warn("start one nodes redis client Host is null ...........");
			return;
		}
		logger.info("start one nodes redis sourceNames=[{}]", JsonUtils.toJson(redisConfigSettings));

		BeanDefinitionBuilder redisFactoryDefinition = BeanDefinitionBuilder
				.genericBeanDefinition(RedisSentinelFactory.class);
		redisFactoryDefinition.addPropertyValue(REDIS_CLUSTER_PARAM, redisConfigSettings);
		registry.registerBeanDefinition(RedisSentinelInterface.class.getSimpleName(), redisFactoryDefinition.getBeanDefinition());
		logger.info("start redis Client beanName=[{}],BeanClassName=[{}]",RedisSentinelInterface.class.getSimpleName(),redisFactoryDefinition.getBeanDefinition().getBeanClassName());
		logger.warn("RedisSentinelAutoConfiguration BeanDefinitions=[{}] Registry --- RedisSentinel Successfully ......      ",RedisSentinelInterface.class.getSimpleName());

	}

}
