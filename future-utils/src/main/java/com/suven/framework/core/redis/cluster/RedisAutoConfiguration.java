package com.suven.framework.core.redis.cluster;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.core.redis.RedisClusterEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

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
@ConditionalOnClass({ RedisClusterClientFactory.class, RedisClusterInterface.class})
@EnableConfigurationProperties(RedisConfigSettings.class)
@ConditionalOnProperty(name = GlobalConfigConstants.REDIS_AUTO_CONFIG_ENABLED,  matchIfMissing = true)
public class RedisAutoConfiguration implements RedisConfig, EnvironmentAware, BeanDefinitionRegistryPostProcessor {
	private final Logger logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);

	private RedisConfigSettings redisConfigSettings;
	private Binder binder;

	@Override
	public void setEnvironment(Environment environment) {
 		binder = Binder.get(environment);
 		try {
            BindResult<RedisConfigSettings> result = binder.bind(REDIS_AUTO_CONFIG_PREFIX, RedisConfigSettings.class);//.get();
            if (null == result ||  !result.isBound()) {
                logger.warn("init RedisConfigSettings parameter from RedisAutoConfiguration result is null,"
                        + "REDIS_AUTO_CONFIG_PREFIX [{}]",REDIS_AUTO_CONFIG_PREFIX);
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

		Set<RedisClusterEnum> sourceNames = new LinkedHashSet<>();
		sourceNames.addAll(RedisClusterEnum.getRedisClusters());
		logger.info("start redis sourceNames={}",sourceNames==null?"": JsonUtils.toJson(sourceNames));
		if (sourceNames == null || sourceNames.isEmpty()) {
			return;
		}

		for (RedisClusterEnum redisGroupBeanName : sourceNames) {

			String servers = this.getProperty(REDIS_CLUSTER_SERVERS,redisGroupBeanName);
			if(servers == null){
				continue;
			}
			String password = this.getProperty(REDIS_CLUSTER_PASSWORD,redisGroupBeanName);

 			BeanDefinitionBuilder redisFactoryDefinition = BeanDefinitionBuilder
					.genericBeanDefinition(RedisClusterClientFactory.class);
			redisFactoryDefinition.addPropertyValue(REDIS_CLUSTER_PARAM_SERVER, servers);
			redisFactoryDefinition.addPropertyValue(REDIS_CLUSTER_PARAM_PASSWORD, password);

			redisFactoryDefinition.addPropertyValue(REDIS_CLUSTER_PARAM, redisConfigSettings);

			registry.registerBeanDefinition(redisGroupBeanName.getGroupName(), redisFactoryDefinition.getBeanDefinition());
			logger.info("start redis cluster bean=[{}],definition=[{}]",redisGroupBeanName.getGroupName(),redisFactoryDefinition.getBeanDefinition());
			logger.warn("RedisAutoConfiguration BeanDefinitions=[{}] Registry --- routingRedisCluster Successfully ......      ",redisGroupBeanName.getGroupName());
		}

	}

	private String getProperty(String prefix, RedisClusterEnum beanName){
		StringBuilder sb = new StringBuilder(prefix);
		if(beanName != RedisClusterEnum.REDIS_GROUP_DEFAULT){
			sb.append(".").append(beanName.getConfig());
		}
		String config = null;
		try {
			BindResult<String> result = binder.bind(sb.toString().trim(), String.class);
			if (null != result && result.isBound()) {
				config = result.get().trim();
				logger.info("start redis cluster loading property=[{}],servers=[{}]", sb.toString(), config);
			}
		}catch (NoSuchElementException ne){
			return config;
		}catch (Exception e){
			e.printStackTrace();
			logger.error("start redis cluster loading property=[{}],servers=[{}],Exception =[{}]",sb.toString(), config, e);
		}
		return config;


	}

}
