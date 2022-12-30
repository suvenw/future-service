package com.suven.framework.core.db;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.suven.framework.util.tool.Splitable;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @Title: DataSourceAutoConfig.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) DruidDataSource 数据库聚群，启动和管理的配置实现类；；
 *  数据库连接池的相结合配置文件类型定义参数；
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


/**
 * 应用服务起动类，加载数据源实现模块组实现类
 * 数据库聚群，启动和管理的配置实现类；
 * top.datasource.druid.config.enabled =true.表示启动该实现类来初始化DataSourceGroupNameEnum对象管理的数据源，
 * 默认值为false,
 * 数据库的初始参数都是统一的，实现类为DruidDataConfig，统一从Environment environment 初始化获取；
 * // ImportBeanDefinitionRegistrar
 * // BeanDefinitionRegistryPostProcessor
 * setEnvironment()-->postProcessBeanDefinitionRegistry() --> postProcessBeanFactory()
 */
@Deprecated
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,JdbcTemplateAutoConfiguration.class})
@ConditionalOnProperty(name = DataDruidConfig.datasource_druid_config_enabled,  matchIfMissing = true)
@ConfigurationProperties
public class DataSourceAutoConfig implements DataDruidConfig, EnvironmentAware, BeanDefinitionRegistryPostProcessor,ApplicationContextAware{

    private final Logger logger = LoggerFactory.getLogger(DataSourceAutoConfig.class);

    private DruidDataConfig druidConfig;
    private  Binder binder;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        try {
            binder = Binder.get(environment);
            druidConfig = binder.bind(DATASOURCE_DRUID_CONFIG, DruidDataConfig.class).get();
            logger.warn("DataSourceAutoConfiguration init DruidDataConfig info=[{}]", druidConfig.toString());
        }catch (Exception e){
            logger.error(" DataSourceAutoConfig init DruidDataConfig Exception:[{}]",e);
        }

    }





    @Bean("dataSource")
    @ConditionalOnMissingBean
    public DataSource routingDataSource() {
        DynamicDataSourceEnum dataSource =  DynamicDataSourceEnum.getInstance();
        Map<String, Enum> sourceGroupEnumMap =  DatasourceEnumManager.getDatasourceEnumMap();

        if (sourceGroupEnumMap == null || sourceGroupEnumMap.isEmpty()) {
            throw  new RuntimeException("DynamicDataSource init DataSource Enum Map isEmpty ");
        }
        for (Enum dataName : sourceGroupEnumMap.values()) {
            if( ! DataSourceGroupEnumInterface.class.isAssignableFrom(dataName.getClass()) ){
               continue;
            }
            DataSourceGroupEnumInterface groupEnum = (DataSourceGroupEnumInterface)dataName;
            dataSource.initDataByGroup(groupEnum,applicationContext);
        }
        dataSource.setTargetDataSources();

        logger.warn("Dynamic DataSource Registry --- routingDataSource Successfully ...      ");
        return dataSource;
    }


    public void registerBeanDefinitions( AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry){
        postProcessBeanDefinitionRegistry(registry);
    }

    /**
     * 将 DataSourceGroupNameEnum 数据源组 生成bean对象注入到spring管理容器中；
     * 通过 datasource_druid_enabled 对应模块数据库配置总开关，默认值为true
     * 通过 datasource_druid_slave_enabled 对应模块从数据库配置总开关，默认值为true
     * 将模块初始化后数据源信息，并将结果生成spring bean 名称缓存到DataSourceGroupNameEnum对象中
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {


        Map<String, Enum> sourceMap =  DatasourceEnumManager.getDatasourceEnumMap();
        if (sourceMap == null || sourceMap.isEmpty()) {
            return;
        }
        boolean isPrimary = true;
        //从数据库配置总开关，
        boolean dataSourceSlaveEnabled =
                property(datasource_druid_slave_enabled,
                        datasource_druid_frame,
                        datasource_druid_slave,
               true);


        for (Enum datasourceEnum : sourceMap.values()) {
            if(!(datasourceEnum instanceof  DataSourceGroupEnumInterface)){
                continue;
            }
            DataSourceGroupEnumInterface dataName = (DataSourceGroupEnumInterface)datasourceEnum;

            //模块数据库 主-从 配置总开关，
            boolean moduleDataSourceEnabled = property(datasource_druid_master_enabled,
                    dataName.getModule(),
                    datasource_druid_master,
                   true);

            //模块从数据库配置总开关，
            boolean moduleDataSourceSlaveEnabled = property(datasource_druid_slave_enabled,
                    dataName.getModule(),
                    datasource_druid_slave,
                    true);

            /**
             * 通过模块对应的配置文件获取主数据库信息，如果不存在就跳该模块的对应的所有数据库
             */
            String url = property(datasource_druid_url,dataName.getModule(),datasource_druid_master);
            if (null == url || "".equals(url) || !moduleDataSourceEnabled) {
                continue;
            }
            String username = property(datasource_druid_username,dataName.getModule(),datasource_druid_master);
            String password = property(datasource_druid_password,dataName.getModule(),datasource_druid_master);
            String publicKey = property(datasource_druid_public_key,dataName.getModule(),datasource_druid_master);

            /**
             * 注入到spring bean的名称生成规则；（模块文称+ master）
             */
            String datasourceMasterBeanName = builderString(dataName.getModule() , DataSourceTypeEnum.MASTER.name().toLowerCase()) ;

            BeanDefinitionBuilder datasourceFactory = initDatasourceBean(druidConfig,url,username,password,publicKey);
            BeanDefinition beanDefinition =  datasourceFactory.getBeanDefinition();
            if(isPrimary){//设置唯一主数据库
                beanDefinition.setPrimary(true);
                isPrimary = false;
            }
            registry.registerBeanDefinition(datasourceMasterBeanName, beanDefinition);
            List<String> datasourceSlaveBeanNameList = new ArrayList<>();
            int i = 0 ;
           String slaveName =  DataSourceTypeEnum.SLAVE.name().toLowerCase();
            while (dataSourceSlaveEnabled && moduleDataSourceSlaveEnabled){

                String slave = i == 0 ?  slaveName : slaveName + i;

                /**
                 * 注入到spring bean的名称生成规则；（模块文称+ _slave + 序列号1,2,3...）
                 */
                String datasourceSlaveBeanName = builderString(dataName.getModule(), slaveName,  i);
                url = property(datasource_druid_url,dataName.getModule(),slave);
                if (null == url || "".equals(url)) {
                    break;
                }
                username = property(datasource_druid_username,dataName.getModule(),slave);
                password = property(datasource_druid_password,dataName.getModule(),slave);
                publicKey = property(datasource_druid_public_key,dataName.getModule(),slave);

                datasourceFactory = initDatasourceBean(druidConfig,url,username,password,publicKey);
                registry.registerBeanDefinition(datasourceSlaveBeanName, datasourceFactory.getBeanDefinition());
                datasourceSlaveBeanNameList.add(datasourceSlaveBeanName);
                i++;

            }
            /**
             * 将模块初始化后数据源信息，并将结果生成spring bean 名称缓存到DataSourceGroupNameEnum 对象中
             */
            DatasourceEnumManager.setDataSourceGroup(dataName,datasourceMasterBeanName,datasourceSlaveBeanNameList);

            logger.warn("DataSourceAutoConfig postProcessBeanDefinitionRegistry Registry --- dataSourceName[{}],datasourceMasterBeanName[{}], slaveDataSources[{}] Successfully ...",dataName,datasourceMasterBeanName,datasourceSlaveBeanNameList);

        }

    }

    private String property(String dataName){
        if(dataName == null || "".equals(dataName)){
            return null;
        }
        try {
            BindResult<String> result = binder.bind(dataName,String.class);
            if(null != result && result.isBound()){
                return result.get().trim();
            }
        }catch (Exception e){}
        return null;
    }
    private String property(String format, String dataName,String masterOrSlave){
        String str = String.format(format,dataName,masterOrSlave);
        return property(str);
    }

    private boolean property(String format, String dataName,String masterOrSlave, boolean defaultResult){
        String pro = property(format,dataName,masterOrSlave);
        if(pro == null || "".equals(pro)){
           return defaultResult;
        }
       return Boolean.parseBoolean(pro);
    }



    /**
     * 初始化DruidDataSource对象
     * 通过BeanDefinitionBuilder生成DruidDataSource对象实现类
     * 并且通过配置文件获取对应的指定属性
     * @param url
     * @param username
     * @param password
     * @return
     */
    private BeanDefinitionBuilder initDatasourceBean(DruidDataConfig druid,String url,String username,String password,String publicKey){
        BeanDefinitionBuilder datasourceFactory = BeanDefinitionBuilder.genericBeanDefinition(DruidDataSource.class);
        datasourceFactory.setLazyInit(true);          //设置是否懒加载
        datasourceFactory.setScope(BeanDefinition.SCOPE_SINGLETON);       //设置scope,为单例类型
        datasourceFactory.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);  //设置是否可以被其他对象自动注入

        datasourceFactory.addPropertyValue(URL, url);
        datasourceFactory.addPropertyValue(USERNAME, username.trim());
        try {
            if(null == publicKey || "".equals(publicKey)){
                datasourceFactory.addPropertyValue(PASSWORD, password.trim());
                logger.warn("initDatasourceBean datasourceFactory password is not decrypt,publicKey=[{}], password=[{}]");
            }else{
                String sourcePassword = ConfigTools.decrypt(publicKey.trim(),password.trim());
                datasourceFactory.addPropertyValue(PASSWORD, sourcePassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.warn("ConfigTools.decrypt Exception ,publicKey=[{}], password=[{}]",publicKey,password);
        }

        initDataSource(datasourceFactory,druid);
        return datasourceFactory;
    }


//

    /**
     * 通过配置类对象,初始化数据库的默认配置参数；
     * @param datasourceFactory
     */
    private void initDataSource(BeanDefinitionBuilder datasourceFactory,DruidDataConfig druid){
        if(druid == null){return;}
        List<Field> fieldList = FieldUtils.getAllFieldsList(druid.getClass());
        if(fieldList == null){return;}
        for (Field field : fieldList){
            try {
                Object value = this.getFieldValueByName(field,druid);
                if(null == value){
                    continue;
                }
                datasourceFactory.addPropertyValue(field.getName(),value);
            }catch (Exception e){
                logger.error("druid configuration initialization filter", e);
            }

        }
    }
    /**
     * 根据属性名获取属性值
     * */
    private Object getFieldValueByName(Field field, Object o) {
        try {
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" ;
            if(field.getType() == Boolean.class){
                getter = "is" ;
            }
            getter = getter + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将集合字符与下画线的方式拼接起来,形成新的字符串
     * @param params
     * @return
     */
    public static String builderString(Object ... params){
        StringBuilder sb = new StringBuilder();
        if(params == null || params.length == 0){
            return null;
        }
        for (int i = 0,j = params.length - 1; i < j ; i++  ) {
            sb.append(params[i]).append(Splitable.ATTRIBUTE_SPLIT);
        }
        sb.append(params[params.length -1]);
        return sb.toString();

    }
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }


    public static void main(String[] args) throws Exception {
        String[] arg = {"111","222","3333","4444","55"};
//        String publicKey ="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALjJGlf1WUfSGU1YfdJ03XN8vZtQerLOFe9RmtYqcyH0/CEvMu4EkqQdMu5U9SaY/QudGOa3RPZFszT1dr0537UCAwEAAQ==";
//        String publicKe2 ="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALctRyll6lpMqioF6Bzz7sAjcuLiGVB0bNJ6rXTOoJ5a0SOvr+NYVwKwDgLWK4jwgLpK5jntuIAL/WtaJ5Lbyu0CAwEAAQ==";
//        String password="tw6QKWEPNRALe/2zahhiTVUBKYCKkPPWHD5FEATdPxcBg/17c9Rhb5pIrj4/fpgsdOWHCXkiVxzETAN89KxGCw==";
//        String passwor1="EnWI5XlmmModG6CBbk/DZs7rthugnAVyCL7N/2fFp2d8O2MY5xWeQFHtllzz9nSUz1c8rrRd5QsHf+T7sKTSig==";
//        com.alibaba.druid.filter.config.ConfigTools.main(arg);
//        String ps = ConfigTools.decrypt(publicKe2,passwor1);


        System.out.println();
    }


}
