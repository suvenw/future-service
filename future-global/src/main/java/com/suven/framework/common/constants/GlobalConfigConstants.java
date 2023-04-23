package com.suven.framework.common.constants;


/**
 * 系统全局配置文件的系统参数
 */
public interface GlobalConfigConstants {

	/**  ================ 1. System  Reflections or ComponentScan start param   ================ **/

	public static final String SERVICE_NAME= "future-service";
	public static final String COMPONENT_SCAN_BASE_PACKAGES=  "com.suven";
	public static final String MAPPER_SCAN_BASE_PACKAGES=  "com.suven.framework.*.mapper";





	/**  ================ 1. System  Reflections or ComponentScan end param   ================ **/

	/**  ================ 2. http server start param   ================ **/

	public static final String SYSTEM_PARAM_SETTINGS_NAME          = "top.jetty.server.properties";
	public static final String SYSTEM_PARAM_ENABLED                = "top.jetty.server.enabled";
	public static final String SYSTEM_PARAM_SETTINGS               = "top.jetty.server";

	public static final String JETTY_SERVER_SETTINGS_NAME          = "top.jetty.server.properties";
	public static final String JETTY_SERVER_ENABLED                = "top.jetty.server.enabled";
	public static final String JETTY_SERVER_SETTINGS               = "top.jetty.server";
	public static final String JETTY_SERVER_FILTER_DOS_ENABLED     = "top.jetty.server.dos.enabled";


	public static final String JETTY_SERVER_PORT_SETTINGS          = "top.server.port";


	public static final String TOP_SERVER_CAT_ENABLED              = "top.server.cat.enabled";
	public static final String TOP_SERVER_CAT_NAME                 = "top.server.cat.properties";
	public static final String TOP_SERVER_CAT_SETTINGS             = "top.server.cat";

	public static final String TOP_SERVER_FILE_OSS_ENABLED          = "top.server.file.oss.enabled";
	public static final String TOP_SERVER_FILE_OSS_NAME             = "top.server.file.properties";
	public static final String TOP_SERVER_FILE_OSS_CONFIG           = "top.server.file";


	public static final String TOP_SERVER_HTTP_FILE_ENABLED         = "top.server.http.file.enabled";
	public static final String TOP_SERVER_HTTP_FILE_NAME            = "top.server.http.file.properties";

	public static final String TOP_SERVER_HTTP_SYS_ENABLED          = "top.server.http.sys.enabled";
	public static final String TOP_SERVER_HTTP_SYS_NAME             = "top.server.http.sys.properties";


	public static final String TOP_SERVER_ES_ENABLED                = "top.server.es.enabled";
	public static final String TOP_SERVER_ES_NAME                   = "top.server.es.properties";
	public static final String TOP_SERVER_ES                        = "top.server.es";

	public static final String TOP_SERVER_MYBATIS_ENABLED           = "top.server.mybatis.enabled";
	public static final String TOP_SERVER_MYBATIS_NAME              = "top.server.mybatis.properties";
	public static final String TOP_SERVER_MYBATIS                   = "top.server.mybatis";

	public static final String TOP_SERVER_API_ENABLED               = "top.server.api.enabled";
	public static final String TOP_SERVER_API                       = "top.server.api";

	public static final String TOP_THIRD_ALIYUN_CONFIG_ENABLED       = "top.server.sms.enabled";
	public static final String TOP_THIRD_ALIYUN_CONFIG_NAME          = "top.server.sms.properties";
	public static final String TOP_THIRD_ALIYUN_CONFIG               = "top.server.sms";

	public static final String TOP_SHIRO_OAUTH_CONFIG_ENABLED       = "top.server.shiro.enabled";
	public static final String TOP_SHIRO_OAUTH_CONFIG_NAME          = "top.server.shiro.properties";
	public static final String TOP_SHIRO_OAUTH_CONFIG               = "top.server.shiro";



	public static final String TOP_SPIDER_CONFIG_ENABLED              = "top.server.spider.enabled";
	public static final String TOP_SPIDER_CONFIG_NAME                 = "top.server.spider.properties";
	public static final String TOP_SPIDER_CONFIG                      = "top.server.spider";

	public static final String TOP_DUBBO_SENTINEL_ENABLED             = "top.server.dubbo.sentinel.enabled";
	public static final String TOP_DUBBO_SENTINEL                     = "top.server.dubbo.sentinel";

	public static final String TOP_DUBBO_ASYNC_ENABLED                = "top.server.dubbo.async.enabled";
	public static final String TOP_DUBBO_ASYNC                        = "top.server.dubbo.async";




	/**  ================ 2. http serve end param   ================ **/

	/**  ================ 3. redis start param   ================ **/
	public static final String REDIS_AUTO_CONFIG = "top.redis.cluster";
	public static final String REDIS_AUTO_CONFIG_ENABLED = REDIS_AUTO_CONFIG + ".enabled";

	public static final String REDIS_AUTO_CONFIG_ONE_ENABLED = REDIS_AUTO_CONFIG + "one.enabled";

	public static final String REDIS_CONFIG_CLUSTER_SERVERS = REDIS_AUTO_CONFIG + ".servers";
	public static final String REDIS_CONFIG_CLUSTER_PASSWORD = REDIS_AUTO_CONFIG + ".password";

	public static final String SPRING_REDIS_AUTO_CONFIG = "spring.redis" ;
	public static final String SPRING_REDIS_AUTO_CONFIG_ENABLED = SPRING_REDIS_AUTO_CONFIG + ".enabled";


	/**  ================ 3. redis end param   ================ **/



	/**  ================ 4. MQ start param   ================ **/
	public static final String SERVICE_MQ_CONFIG_NAME = "top.server.mq.properties";
	public static final String SERVICE_MQ_CONFIG_PREFIX = "top.mq";
	public static final String KAFKA_CONFIG_PREFIX = SERVICE_MQ_CONFIG_PREFIX + ".kafka";
	public static final String KAFKA_CONFIG_ENABLED = SERVICE_MQ_CONFIG_PREFIX + ".kafka.config.enabled";
	public static final String KAFKA_PRODUCER_ENABLED = SERVICE_MQ_CONFIG_PREFIX + ".kafka.producer.enabled";
	public static final String KAFKA_CONSUMER_ENABLED = SERVICE_MQ_CONFIG_PREFIX + ".kafka.consumer.enabled";

	public static final String ROCKT_MQ_CONFIG_PREFIX  = SERVICE_MQ_CONFIG_PREFIX + ".rocketmq";
	public static final String ROCKT_MQ_CONFIG_ENABLED = SERVICE_MQ_CONFIG_PREFIX + ".rocketmq.config.enabled";

	public static final String ROCKET_MQ_PRODUCT_CONFIG_ENABLED =  "spring.rocketmq.producer.api.enabled";
	public static final String ROCKET_MQ_CONSUMER_CONFIG_ENABLED =  "spring.rocketmq.consumer.api.enabled";


	public static final String ROCKT_MQ_START_CONFIG_ENABLED = "spring.rocketmq.start.config.enabled";

	/**  ================ 4. MQ end param   ================ **/






	/**  ================ 5. password key info start param   ================ **/
	public static final String POST = "POST";
	/** 密码后缀 */
	public static final String TOP_SERVER_APPKEY = "H@s0zSix!fiNger8";

	String TOP_SERVER_ERROR_URL = "/error";

	String TOP_SERVER_REQUEST_JSON = "json";



	public static final String VERSION_INFO_SERVICE_NAME = "versionValidatorService";
	public static final String VERSION_HANDLER_METHOD_NAME = "initLastAndUpdateVersionMap";

	public static  int GAME_OPEN_OR_CLOSE = 0;
	/**  ================ 5. password key info end param   ================ **/


	/**  ================ 6. OAuth start param   ================ **/
	public static final String OAUTH_CONFIG_ENABLED = "top.server.oauth.config.enabled";
	public static final String OAUTH_CONFIG_PREFIX = "top.server.oauth";




	/**  ================ 6. OAuth end param   ================ **/

	/**  ================ 7. LOGBACK start param   ================ **/
	public static final String LOGBACK_TRACE_ID = "logback_trace_id";
	public static final String LOGBACK_LOCAL_IP = "logback_local_ip";
	/**  ================ 7. LOGBACK end param   ================ **/

	/**  ================ 8. USER start param   ================ **/
	public static final String USER_CONFIG_ENABLED = "top.server.user.config.enabled";
	public static final String USER_CONFIG_PREFIX = "top.server.user";
	/**  ================ 8. USER end param   ================ **/
}
