package com.suven.framework.core.db;


/**
 * @Title: DataDruidConfig.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) DruidDataSource DataDruidConfig 数据库连接管理相关参数属性定义字段对象；
 *  数据库连接池的相结合配置文件类型定义参数；
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

@Deprecated
public interface DataDruidConfig {


    /**
     * DruidDataSource 对应属性的配置文件参数；可对应变更和修改
     * 根据自己的项目配置文件规则，只需要调整***PREFIX对应的参数即可；
     */
    String DATASOURCE_DRUID_CONFIG = "top.datasource.druid";
    String DATASOURCE_DRUID_PREFIX = "top.datasource.druid.";

    /**
     * 数据库对象的模块的url,username,password的前缀
     * 根据自己的项目配置文件规则，只需要调整***PREFIX对应的参数即可；
     * top.datasource.%s.%s.url        //eg: top.datasource.assets.master.url
     * top.datasource.%s.%s.username   //eg: top.datasource.assets.master.username
     * top.datasource.%s.%s.password;  //eg: top.datasource.assets.master.password
     */
      String DATASOURCE_MODULE_PREFIX = "top.datasource.";
      String DATASOURCDE_DRUID_FORMAT = "%s.%s.";



    /**
     * 通过 BeanDefinitionBuilder类，初始化DruidDataSource对应的属性参考
     */


      String URL                                        = "url";
      String USERNAME                                   = "username";
      String PASSWORD                                   = "password";
      String PUBLIC_KEY                                 = "publicKey";


      String ENABLED                                     = "enabled";


      String datasource_druid_master                    = "master";
      String datasource_druid_slave                     ="slave";
      String datasource_druid_frame                     ="druid";
      String datasource_master_name                     = "MasterDataSource";
      String datasource_slave_name                      =  "SlaveDataSource";

      String datasource_param_config_enabled            = "config.enabled";

    /**
     * 初化所有数据源DataSourceAutoConfiguration类枚举类，默认为 DataSourceGroupNameEnum
     * top.datasource.druid.enum.class=com.ds.live.base.core.db.DataSourceGroupNameEnum.class
     */
    String datasource_group_enum_class                     =   DATASOURCE_DRUID_PREFIX +  "enum.class" ;//"top.datasource.druid.frame.enabled";


    /**
     * 初化所有数据源DataSourceAutoConfiguration类配置开关，默认为falase
     * top.datasource.druid.config.enabled=true
     */
      String datasource_druid_config_enabled                     =   DATASOURCE_DRUID_PREFIX +  datasource_param_config_enabled ;//"top.datasource.druid.config.enabled";


    /**
     * 初化指定模块数据源DataSourceGroupNameEnum类配置开关，默认为 true
     * top.datasource.user.master.enabled=true
     */
      String datasource_druid_master_enabled  =                    DATASOURCE_MODULE_PREFIX +  DATASOURCDE_DRUID_FORMAT + ENABLED;//   "top.datasource.%s.%s.enabled";

    /**
     * 初化所有数据源DataSourceAutoConfiguration类配置 从数据库的总开关，
     * 对应数据库的从数据库集合开关；默认为 true,
     * 若存在对应的从数据库的配置会自动加载，若需要关闭可以将该配置设计为false,或删除对应配置
     * top.datasource.druid.user.slave.enabled=true
     */
      String datasource_druid_slave_enabled =                           DATASOURCE_MODULE_PREFIX + DATASOURCDE_DRUID_FORMAT + ENABLED;//    "top.datasource.druid.%s.slave.enabled";

      String datasource_druid_url =                                        DATASOURCE_MODULE_PREFIX + DATASOURCDE_DRUID_FORMAT + URL;//    "top.datasource.%s.%s.url";// = jdbc:postgresql://10.100.0.254:7432/redfinger
      String datasource_druid_username =                                   DATASOURCE_MODULE_PREFIX +  DATASOURCDE_DRUID_FORMAT + USERNAME;//   "top.datasource.%s.%s.username";//top.datasource.assets.master.username = redfinger
      String datasource_druid_password =                                   DATASOURCE_MODULE_PREFIX + DATASOURCDE_DRUID_FORMAT +  PASSWORD;//    "top.datasource.%s.%s.password";// top.datasource.assets.master.password = redfinger
      String datasource_druid_public_key =                                 DATASOURCE_MODULE_PREFIX + DATASOURCDE_DRUID_FORMAT +  PUBLIC_KEY;



}