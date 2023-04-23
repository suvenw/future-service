package com.suven.framework.core.db.druid;

import java.lang.annotation.Annotation;

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

public interface IDataSourceGroupProperties extends Annotation {


    /** 获取取数据库配置名称 **/
    DruidDatasourceGroup getDatasourceGroup();


    default boolean getDefaultTargetDataSource(){
        return false;
    };



}
