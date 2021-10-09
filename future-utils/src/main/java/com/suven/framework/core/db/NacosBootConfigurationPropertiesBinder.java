//package top.suven.future.core.db;
//
//import com.alibaba.nacos.api.config.ConfigService;
//import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
//import com.alibaba.nacos.spring.context.properties.config.NacosConfigurationPropertiesBinder;
//import com.alibaba.nacos.spring.core.env.NacosPropertySource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.properties.ConfigurationBeanFactoryMetadata;
//import org.springframework.boot.context.properties.bind.Bindable;
//import org.springframework.boot.context.properties.bind.Binder;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.core.ResolvableType;
//import org.springframework.core.env.StandardEnvironment;
//
//import java.lang.reflect.Method;
//
//public class NacosBootConfigurationPropertiesBinder extends NacosConfigurationPropertiesBinder {
//
//	private final Logger logger = LoggerFactory.getLogger(NacosBootConfigurationPropertiesBinder.class);
//
//	private ConfigurationBeanFactoryMetadata beanFactoryMetadata;
//	private StandardEnvironment environment = new StandardEnvironment();
//
//	public NacosBootConfigurationPropertiesBinder(ConfigurableApplicationContext applicationContext) {
//		super(applicationContext);
//		this.beanFactoryMetadata = applicationContext.getBean(
//				ConfigurationBeanFactoryMetadata.BEAN_NAME,
//				ConfigurationBeanFactoryMetadata.class);
//	}
//
//	@Override
//	protected void doBind(Object bean, String beanName, String dataId, String groupId,
//						  String configType, NacosConfigurationProperties properties, String content,
//						  ConfigService configService) {
//		String name = "nacos-bootstrap-" + beanName;
//		NacosPropertySource propertySource = new NacosPropertySource(name, dataId, groupId, content, configType);
//		environment.getPropertySources().addLast(propertySource);
//		Binder binder = Binder.get(environment);
//		ResolvableType type = getBeanType(bean, beanName);
//		Bindable<?> target = Bindable.of(type).withExistingValue(bean);
//		binder.bind(properties.prefix(), target);
//		publishBoundEvent(bean, beanName, dataId, groupId, properties, content, configService);
//		publishMetadataEvent(bean, beanName, dataId, groupId, properties);
//		environment.getPropertySources().remove(name);
//	}
//
//	private ResolvableType getBeanType(Object bean, String beanName) {
//		Method factoryMethod = this.beanFactoryMetadata.findFactoryMethod(beanName);
//		if (factoryMethod != null) {
//			return ResolvableType.forMethodReturnType(factoryMethod);
//		}
//		return ResolvableType.forClass(bean.getClass());
//	}
//
//}
