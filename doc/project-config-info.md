## [项目接口文档](doc)


### 1.基础配置文件说明：application-base.properties 

```
#--------------------------------DUBBO---------------------------------#

nacos.home.conf=127.0.0.1:8848
dubbo.application.logger=slf4j
dubbo.application.qos.enable=false
dubbo.application.qos.port=22222
dubbo.application.qos.accept.foreign.ip=false

#项目启动扫描包目录,多个目录以逗号隔开
dubbo.scan.basePackages=top.suven.futurn

#微服务器注册中心,可以使用zookeeper,也可以使用nacos做为微服务的注册中心
#dubbo.registry.address=zookeeper://192.168.170.37:2181?backup=192.168.170.37:2182,192.168.170.37:2183
#微服务器注册中心,使用nacos做为微服务的注册配置中心
dubbo.registry.address = nacos://127.0.0.1:8848?backup=127.0.0.1:8848
dubbo.jetty.directory=/data/applogs/monitor 
#服务器地址
spring.cloud.nacos.config.server-addr=127.0.0.1:8848
#默认为Public命名空间,可以省略不写
spring.cloud.nacos.config.namespace=Public
#指定配置群组 --如果是Public命名空间 则可以省略群组配置
spring.cloud.nacos.config.group=futurn-service
#文件名 -- 如果没有配置则默认为 ${spring.application.name}
spring.cloud.nacos.config.prefix=member


# 微服务器部署 dubbo生产端组
dubbo.provider.group=DEV
# 微服务器部署 dubbo生产端组版本号
dubbo.provider.version=1.0.0
dubbo.provider.dispatcher=message

# 微服务器部署 dubbo消费端组
dubbo.consumer.group=DEV
# 微服务器部署 dubbo消费端组版本号
dubbo.consumer.version=1.0.0
# 微服务器部署 dubbo消费端组版本号
dubbo.consumer.timeout=60000
# 微服务器部署 dubbo消费端是否检查可用性
dubbo.consumer.check=false
# 微服务器部署 dubbo消费端组 是否懒加载
dubbo.consumer.lazy=true

dubbo.protocol.name=dubbo
dubbo.protocol.accepts=200
dubbo.protocol.threads=200
dubbo.protocol.threadpool=cached

#监控服务
#dubbo.monitor.protocol=registry

#服务限流降级哨兵配置,服务地址
#top.dubbo.sentinel.dashboardServer=192.168.170.37:8866
#服务限流降级哨兵配置,是否开始
#top.dubbo.sentinel.openFlow=true
#服务限流降级哨兵配置,日志目录
#top.dubbo.sentinel.logPath=/data/webapps/hsz/dubbo-sentinel-log

#微服服务隔断哨兵模块
top.async.asyncCorePoolSize=20
top.async.asyncMaxPoolSize=128
top.async.asyncQueueCapacity=500
top.async.asyncThreadNamePrefix=spring-async-thread-
top.async.keepAliveSeconds=60
top.async.allowCoreThreadTimeOut=false
top.async.poolSize=64

#-----------------------------------END---------------------------------#

#-----------------------------------nacos start---------------------------------#

nacos.config.server-addr = 127.0.0.1:8848

#-----------------------------------nacos END---------------------------------#

#--------------------------------PROJECT--------------------------------#
####Jetty properties########
# Jetty 启用处理请求的线程数，设置值小于或等于处理器的2倍(建议值64)
#server.jetty.acceptors=2
# 设置Jetty selectors 线程数 ，使用的选择器线程数 (建议值128)
#server.jetty.selectors=4
# put或post方法最大字节数(建议值10000000)
server.max-http-header-size=10000000
# HTTP发布或放置内容的最大大小（以字节为单位）
server.jetty.max-http-post-size=10000000000
#服务拦截的根目录
server.servlet.context-path=/top

#配置文件传输
spring.servlet.multipart.enabled = true
spring.servlet.multipart.file-size-threshold = 0
#单个文件的最大上限
spring.servlet.multipart.max-file-size = 10MB
#单个请求的文件总大小上限
spring.servlet.multipart.max-request-size=1000MB

#服务基本目录
top.jetty.server.documentRoot=/data/server
#服务最小线程池数(建议值100)
top.jetty.server.threadPool.minThreads=8
#服务最大线程池:建议:1000(建议值5000)
top.jetty.server.threadPool.maxThreads=200
#服务最大线程池:建议:3000(建议值5000)
top.jetty.server.threadPool.idleTimeout=3000
#是否在调用void dump(Appendable out, String indent)是输出更详细的信息:建议:false
top.jetty.server.threadPool.detailedDump=false

#运行服务监控数据上报
top.jetty.server.lowResources.period=1000
top.jetty.server.lowResources.idleTimeout=200
top.jetty.server.lowResources.monitorThreads=true
top.jetty.server.lowResources.maxConnections=10
top.jetty.server.lowResources.maxMemory=1000
top.jetty.server.lowResources.maxLowResourcesTime=5000




#-----------------------------------END---------------------------------#

#--------------------------------DOS FILTER  START--------------------------------#
#防dos 攻击拦截实现配置
top.jetty.filter.dos.enabled=true
top.jetty.filter.dos.config.urlPatterns=/top/*,/server/*
top.jetty.filter.dos.config.exclusions=*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*
top.jetty.filter.dos.config.delayMs=-1
top.jetty.filter.dos.config.throttledRequests=5
top.jetty.filter.dos.config.maxRequestsPerSec=20
top.jetty.filter.dos.config.remotePort=true
top.jetty.filter.dos.config.maxWaitMs=50
top.jetty.filter.dos.config.maxRequestMs=30000
top.jetty.filter.dos.config.maxIdleTrackerMs=30000


#-----------------------------------DOS FILTER  END---------------------------------#
#--------------------------------Login Type----------------------------#
#参数验证开启加密码校验:默认true; 填写值为true/false,所有请求参数按a-z模式排序,作用是防篡改；
top.server.param.ccheckParamSign=true
#切面日志实现开关
top.server.param.logAop=false
#-----------------------------------END---------------------------------#
```


### 2.数据库基础配置文件说明： application-db.properties：
````
 #-----------------------------------datasource--------------------------------------
 #数据库统计规范的配置文件

 # 1.动态数据库全局配置是否开启开关,默认为true
top.datasource.druid.config.enabled=true
 # 2.动态数据库全局从数据库是否开启开关,默认为true,可以配置为false,配置的从数据库配置,也会失效;
top.datasource.druid.slave.enabled=true

 # 3.动态数据库模块master或slave开关,默认为true,该配置项在application.properties中进行个性项目开始配置

 # top.datasource.druid.user.master.enabled=true
 # top.datasource.druid.user.slave.enabled=true
 #top.datasource.druid.url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=utf8&amp;emptyStringsConvertToZero=true
 #top.datasource.druid.username: root
 #top.datasource.druid.password: root


top.datasource.druid.initialize=true 
top.datasource.druid.dbType= mysql
top.datasource.druid.type = com.alibaba.druid.pool.DruidDataSource
top.datasource.druid.driver-class-name =com.mysql.cj.jdbc.Driver 


 # 初始化连接池的连接数量 大小，最小，最大
#(建议值1200)
top.datasource.druid.initial-size = 1
#(建议值60)
top.datasource.druid.min-idle = 3
#(建议值5000)
top.datasource.druid.max-active = 20
 # 配置获取连接等待超时的时间 #(建议值60000)
top.datasource.druid.max-wait = 600
 # 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 (建议值60000)
top.datasource.druid.time-between-eviction-runs-millis = 60000
 # 配置一个连接在池中最小生存的时间，单位是毫秒 (建议值300000)
top.datasource.druid.min-evictable-idle-time-millis = 300000
top.datasource.druid.validationQuery = select 'x';
top.datasource.druid.test-while-idle = true
top.datasource.druid.test-on-borrow = false
top.datasource.druid.test-on-return = false
 # 是否缓存preparedStatement，也就是PSCache  官方建议MySQL下建议关闭   个人建议如果想用SQL防火墙 建议打开
top.datasource.druid.pool-prepared-statements = true
 # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
top.datasource.druid.filters = stat
 # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
spring.datasource.druid.connection-properties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000



spring.datasource.druid.stat-view-servlet.enabled=false
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
 # 白名单,可以为空,允许所有
 #spring.datasource.druid.statViewServlet.allow=
 #  IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的提示:Sorry, you are not permitted to view this page.
 #spring.datasource.druid.statViewServlet.deny=
 # druid数据库监控账号密码
spring.datasource.druid.stat-view-servlet.login-username=admin
spring.datasource.druid.stat-view-servlet.login-password=123456
 # 是否能够重置数据.默认为true
spring.datasource.druid.stat-view-servlet.reset-enable=false


 # WebStatFilter配置，说明请参考Druid Wiki，配置_配置WebStatFilter
 #是否启用StatFilter默认值true
spring.datasource.druid.web-stat-filter.enabled=false
spring.datasource.druid.webStatFilter.sessionStatEnable=false
spring.datasource.druid.webStatFilter.profileEnable=false
spring.datasource.druid.webStatFilter.urlPattern=/*
spring.datasource.druid.webStatFilter.exclusions="*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,/server/druid/*



 #-------------------------------------END--------------------------------------



````


### 3.redis配置文件说明： application-redis.properties 

````
 # ---------------------------------REDIS START ---------------------------------#
 # 数据表对象是否缓存到redis 开关,默认为true
top.redis.cluster.client.daoOpen=true

 # 数据表对象是否写入缓存到redis 开关,
top.redis.cluster.client.write=true
 # 数据表对象是否读取缓存到redis 开关,
top.redis.cluster.client.read=true


 #redis 连接池配置参数文件
top.redis.cluster.enabled=true
top.redis.cluster.timeout = 10000
top.redis.cluster.pool.maxTotal= 1000
top.redis.cluster.pool.maxIdle= 100
top.redis.cluster.pool.minIdle= 10
top.redis.cluster.pool.maxWaitMillis= 1000
top.redis.cluster.pool.timeout= 10000
top.redis.cluster.pool.maxRedirections= 2
top.redis.cluster.pool.testOnBorrow= true
top.redis.cluster.pool.testOnReturn= true


	# redis 多聚群配置实现;参考 RedisClusterEnum 配置取值范围为(0-89),例子如下:
top.redis.cluster.servers=127.0.0.1:7001;127.0.0.1:7002;127.0.0.1:7003;127.0.0.1:7004;127.0.0.1:7005;127.0.0.1:7006
	#top.redis.cluster.password=8Er^_QrO
top.redis.cluster.servers.one=127.0.0.1:7001;127.0.0.1:7002;127.0.0.1:7003;127.0.0.1:7004;127.0.0.1:7005;127.0.0.1:7006
	#top.redis.cluster.password.one=8Er^_QrO
	
	#top.redis.cluster.servers.eighty=127.0.0.1:7001;127.0.0.1:7002;127.0.0.1:7003;127.0.0.1:7004;127.0.0.1:7005;127.0.0.1:7006
	#top.redis.cluster.password.eighty=8Er^_QrO
   #top.redis.cluster.servers.eighty.nine=127.0.0.1:7001;127.0.0.1:7002;127.0.0.1:7003;127.0.0.1:7004;127.0.0.1:7005;127.0.0.1:7006
	#top.redis.cluster.password.eighty.nine=8Er^_QrO
#------------------------------------END----------------------------------#

````



### 4.异步消息mq配置文件说明：application-mq.properties 

````
 # 项目提供和聚群微服务的架构,包括日志服elk, 大数据分析采摘kafka, 服务间解籍服务rocketmq
 #-----------------------------------kafka--------------------------------------
 # kafka service config status kafka 服务是否启动配置,默认为false : true/false
top.kafka.config.enabled=false
 # kafka service producer status: true/false ; kafka 服务对就生产端是否开启开关;  
top.kafka.producer.enabled=true
 # top.kafka.producer (acks:0,1,-1(all))
 # acks=0,生产者成功写入消息之前不会等待来自任何服务器的响应，这种配置，提高吞吐量，但是消息存在丢失风险。
 # acks=1,只要集群的leader（master）收到了消息，生产者将会受到发送成功的一个响应。
 # acks=-1,所有参与复制的节点全部收到消息的时候，生产者才会收到来自服务器的一个响应，这种模式最安全，但是吞吐量受限制。
top.kafka.producer.acks=0
 # 生产者从服务器收到的错误消息有可能是临时的，当生产者收到服务器发来的错误消息，会启动重试机制，当充实了n（设置的值）次，还是收到错误消息，那么将会返回错误。生产者会在每次重试之间间隔100ms，不过可以通过retry.backoff.ms改变这个间隔。
top.kafka.producer.retries= 0
 # 当多个消息发往 同一个分区，生产者会将他们放进同一个批次，该参数指定了一个批次可以使用的内存大小
top.kafka.producer.batchSize= 6384
 # 该参数用来设置生产者内存缓冲区的大小，缓冲生产者发往服务器的消息，
top.kafka.producer.bufferMemory=33554432
 # 该参数指定了生产者在发送批次之前等待更多消息加入批次的时间
top.kafka.producer.lingerMs=1
 # 该参数指定了在调用send()方法或使用partitionFor() 方法获取元数据时生产者的阻塞时间。当生产者的发送缓冲区已满，或者没有可用的元数据时，这些方法就会阻塞。在阻塞时间max.block.ms 时，生产者会抛出超时异常
top.kafka.producer.maxBlockMs=1000
 # 传输对象序列化类型和 kafka 软件部署服务ip与端口
top.kafka.producer.keySerializer= org.apache.kafka.common.serialization.StringSerializer
top.kafka.producer.valueSerializer= org.apache.kafka.common.serialization.StringSerializer
top.kafka.producer.bootstrapServers=  127.0.0.1:9092

 # top.kafka.consumer kafka 消费端配置说明,kafka 消费端是否开启开关;默认为 false
 # kafka service consumer status : true/false
top.kafka.consumer.enabled=true
 # 每个消费者分配独立的组号 group name
top.kafka.consumer.groupId = top
 # 是否自动确认offset,如果value合法，则自动提交偏移量
top.kafka.consumer.enableAutoCommit =true
 # 自动确认offset的时间间隔,设置多久一次更新被消费消息的偏移量
top.kafka.consumer.autoCommitIntervalms =100
 # 设置会话响应的时间，超过这个时间kafka可以选择放弃消费或者消费下一条消息
top.kafka.consumer.sessionTimeoutMs= 30000
 #只限自动提交，
top.kafka.consumer.pollTimeout =100
 # 设置消费的线程数
top.kafka.consumer.concurrency=2
 # 自动重置offset
top.kafka.consumer.autoOffsetReset = earliest
 # 传输对象序列化类型和 kafka 软件部署服务ip与端口
top.kafka.consumer.keyDeserializer =org.apache.kafka.common.serialization.StringDeserializer
top.kafka.consumer.valueDeserializer = org.apache.kafka.common.serialization.StringDeserializer
top.kafka.consumer.bootstrapServers = 127.0.0.1:9092
#--------------------------------------END--------------------------------------

#-----------------------------ROCKER-MQ---------------------------------#
top.rocketmq.config.enabled=false
top.rocketmq.nameSrvAdds=127.0.0.01:9876
top.rocketmq.maxMessageSize=131072
top.rocketmq.sendMsgTimeout=3000
top.rocketmq.retryTimesWhenSendFailed=2
top.rocketmq.consumeThreadMin=20
top.rocketmq.consumeThreadMax=64

top.rocketmq.delayTimeLevel=2
top.rocketmq.client.logLevel=INFO
top.rocketmq.client.logRoot=/data/server/rocket-mq-log
top.rocketmq.client.logFileMaxIndex=10



spring.rocketmq.producer.enabled=false
spring.rocketmq.nameServer= 127.0.0.01:9876
spring.rocketmq.producer.retry-times-when-send-async-failed=0
spring.rocketmq.producer.send-msg-timeout=300000
spring.rocketmq.producer.compress-msg-body-over-howmuch=4096
spring.rocketmq.producer.max-message-size=4194304
spring.rocketmq.producer.retry-another-broker-when-not-store-ok=false
spring.rocketmq.producer.retry-times-when-send-failed=3
spring.rocketmq.producer.group=TEST_AUTH
spring.rocketmq.consumer.enabled=false
#----------------------------------END-----------------------------------#


````

### 5.业务模块个性配置文件说明 application.properties 

````
	#引入其它业务模块配置实现,如上面的业务编写如下:
spring.profiles.include=db,mq,redis,sms,base

#---------------------------------OAUTH--------------------------------#

#----------------------------------END-----------------------------------#

#--------------------------------DUBBO---------------------------------#
dubbo.application.name=rpc-oauth-provider
dubbo.protocol.name=dubbo
dubbo.protocol.port=28020
dubbo.application.qos-port=22020
top.dubbo.sentinel.count=100
top.dubbo.sentinel.apiPort=27021
top.dubbo.sentinel.projectName=rpc-oauth
top.dubbo.sentinel.gradle=1
#--------------------------------END-----------------------------------#
 #top.oauth.config.enabled=true
 #top.auth.accessToken.timeout=86400


 #mybatis-plus 对应项目中的*Mapper.xml 路径配置
mybatis-plus.mapper-locations: classpath*:top/suven/oauth/**/mapper/*Mapper.xml
 #实体扫描，多个package用逗号或者分号分隔
mybatis-plus.type-aliases-package=top.suven.video.sys.entity
 #主键类型  AUTO:"数据库ID自增", INPUT:"用户输入ID", ID_WORKER:"全局唯一ID (数字类型唯一ID)", UUID:"全局唯一ID UUID";
mybatis-plus.global-config.db-config.id-type=AUTO
 #默认驼峰转大写策略
mybatis-plus.global-config.db-config.column-underline=true
mybatis-plus.configuration.map-underscore-to-camel-case=true
 #开启缓存
mybatis-plus.configuration.cache-enabled= false
 #配置值为null
mybatis-plus.configuration.call-setters-on-nulls=true
mybatis-plus.configuration.jdbc-type-for-null=null





 #---------------------------------OAUTH--------------------------------#
 #数据库配置: 按规范编写,支持1主多从写法:从数年库按0-N编写就可以了;其它(oauth,user,为模块名称);也可以主写master
 #master
top.datasource.oauth.master.url = jdbc:mysql://127.0.0.1:3306/top_sys?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong&allowPublicKeyRetrieval=true
top.datasource.oauth.master.username = dev
top.datasource.oauth.master.password = dev123456

 #slave
top.datasource.oauth.slave.url = jdbc:mysql://127.0.0.1:3306/top_sys?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong&allowPublicKeyRetrieval=true
top.datasource.oauth.slave.username = dev
top.datasource.oauth.slave.password = dev123456

 #slave1
top.datasource.oauth.slave1.url = jdbc:mysql://127.0.0.1:3306/top_sys?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong&allowPublicKeyRetrieval=true
top.datasource.oauth.slave1.username = dev
top.datasource.oauth.slave1.password = dev123456


 ##master
 #top.datasource.user.master.url = jdbc:mysql://127.0.0.1:3306/top_sys?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong&allowPublicKeyRetrieval=true
 #top.datasource.user.master.username = dev
 #top.datasource.user.master.password = dev123456

 ##slave
 #top.datasource.user.slave.url = jdbc:mysql://127.0.0.1:3306/top_sys?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong&allowPublicKeyRetrieval=true
 #top.datasource.user.slave.username = dev
 #top.datasource.user.slave.password = dev123456

 ##slave1
 #top.datasource.user.slave1.url = jdbc:mysql://127.0.0.1:3306/top_sys?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong&allowPublicKeyRetrieval=true
 #top.datasource.user.slave1.username = dev
 #top.datasource.user.slave1.password = dev123456



 #elk 日志向报服务配置
 #----------------------------- ELK LOGSTASH ---------------------------#
 #elk 日志向报服务配置 应对的微服务名称
top.logstash.server.project.name=gc-rpc-oauth-service
 #接收 微服务日志的logstash 服务端ip:端口
top.logstash.server.ip.port=127.0.0.1:9601
 #--------------------------------END--------------------------------#

````

