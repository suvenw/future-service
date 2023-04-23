(2)、配置数据源相关信息

### 下面为连接池的补充设置，应用到上面所有数据源中

### 初始化大小，最小，最大

gc.datasource.initialSize=5

gc.datasource.minIdle=5

gc.datasource.maxActive=20

### 配置获取连接等待超时的时间

gc.datasource.maxWait=60000

#### 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒

gc.datasource.timeBetweenEvictionRunsMillis=60000

#### 配置一个连接在池中最小生存的时间，单位是毫秒

gc.datasource.minEvictableIdleTimeMillis=300000

gc.datasource.validationQuery=SELECT 1 FROMDUAL

gc.datasource.testWhileIdle=true

gc.datasource.testOnBorrow=false

gc.datasource.testOnReturn=false

#### 打开PSCache，并且指定每个连接上PSCache的大小

gc.datasource.poolPreparedStatements=true

gc.datasource.maxPoolPreparedStatementPerConnectionSize=20

#### 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙

gc.datasource.filters=stat,wall,log4j

#### 通过connectProperties属性来打开mergeSql功能；慢SQL记录

gc.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500

#### 合并多个DruidDataSource的监控数据

#### gc.datasource.useGlobalDataSourceStat=true

####  监控服务器开始或关闭
spring.datasource.druid.stat-view-servlet.enabled=true

#### 服务器的拦截前缀名称:eg: http://ip:post/hsz/druid
spring.datasource.druid.statViewServlet.urlPattern=/druid/*

#### IP白名单(没有配置或者为空，则允许所有访问)
spring.datasource.druid.statViewServlet.allow=127.0.0.1,192.168.169.72

#### IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
spring.datasource.druid.statViewServlet.deny=

#### 登录查看信息的账号密码.
spring.datasource.druid.statViewServlet.loginUsername=admin
spring.datasource.druid.statViewServlet.loginPassword=123456

####   是否能够重置数据.
server.datasource.druid.statViewServlet.resetEnable=false

#### 监控服务器开始或关闭
spring.datasource.druid.web-stat-filter.enabled=true

#### 服务器的拦截前缀名称:eg: http://ip:post/hsz/druid
spring.datasource.druid.webStatFilter.urlPattern=/*

#### 排除一些不必要的url，比如.js,/jslib/等等
spring.datasource.druid.webStatFilter.exclusions="*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,/server/druid/* 

#### 数据库访问配置

#### 主数据源，默认的

gc.datasource.type=com.alibaba.druid.pool.DruidDataSource

gc.datasource.driver-class-name=com.mysql.jdbc.Driver

gc.datasource.url=jdbc:mysql://localhost:3306/test

gc.datasource.username=root

gc.datasource.password=123456



需要注意的是：gc.datasource.type旧的gc boot版本是不能识别的。

这时候启动应用就可以看到看到打印信息就是使用我们配置的数据源了：
[main]com.alibaba.druid.pool.DruidDataSource  : {dataSource-1} inited