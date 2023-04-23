### 一、监控端点的作用：
根据端点的作用，可以将端点分为四大类：

应用配置类：获取应用程序中加载的应用配置，环境变量，自动化配置报告等与应用相关的信息。

度量指标类：获取应用程序运行过程中用于监控的度量指标，比如内存信息，线程信息，http请求等。

操作控制类：提供了对应用的关闭等操作类功能。
下面的端点都是可用的：


ID	 | 描述  |	敏感（Sensitive）
 ----                |  ----      |  ---- 
autoconfig |	显示一个auto-configuration的报告，该报告展示所有auto-configuration候选者及它们被应用或未被应用的原因	| true
beans	| 显示一个应用中所有Spring Beans的完整列表	| true
configprops |	显示一个所有@ConfigurationProperties的整理列表 |	true
dump	| 执行一个线程转储	true
env	| 暴露来自Spring　ConfigurableEnvironment的属性	 |true
health	| 展示应用的健康信息（当使用一个未认证连接访问时显示一个简单的’status’，使用认证连接访问则显示全部信息详情）|	false
info |	显示任意的应用信息	| false
metrics	| 展示当前应用的’指标’信息	| true
mappings |	显示一个所有@RequestMapping路径的整理列表	 |true
shutdown | 	允许应用以优雅的方式关闭（默认情况下不启用）|	true
trace	| 显示trace信息（默认为最新的一些HTTP请求）|	true


#### 1.应用配置类：
（1）/autoconfig:获取应用的自动化配置使用情况，包含所有自动化配置的候选项。同时列出了每个候选项是否匹配成功以及没有匹配成功的原因。positiveMatches返回的是匹配成功的自动化配置，negativeMatches返回的是匹配不成功的自动化配置。

（2）/configprops:获取应用配置所有的配置属性。prefix代表属性前缀，properties表示属性名称和id等。

（3）/beans：获取应用程序中创建的所有Bean。每个Bean包含bean、scope、type、resource、dependencies。

（4）/env：获取应用的环境信息。包含环境变量、JVM属性、应用配置属性、参数、端口等。

（5）/mappings：返回所有控制器映射关系报告，包括业务接口和监控接口。

（6）/info:获取应用自定义信息，默认为空。可自己在application.properties里面配置。

#### 2.度量指标类：
（7）/metrics:返回当前各类重要指标信息，比如内存概要信息、堆内存信息、非堆内存信息、线程使用情况、应用加载和卸载的类统计、垃圾收集器详细信息、tomcat容器使用情况、http请求性能指标等。

（8）/health：获取应用的各类指标信息。也可以自己自定义Health监控。

（9）/dump:用来生成当前线程活动的快照。

（10）/trace：用来返回基本的http跟踪信息。保留最近的100条http请求记录。

#### 3.操作控制类：
(11)/shutdown:它是直接关闭应用程序的端点，它与前面的端点不一样，前面的端点都是默认启用的，而它需要通过属性来配置开启操作。可以在application.properties中配置开启：

endpoints.shutdown.enabled=true

#### 4.应用监控类

（12）/heapdump:springmvc的端点，用来返回Gzip压缩hprof堆转储文件（以hprof.gz结尾）

（13）/loggers:能够查看所有包类的日志级别，并且能够对其进行修改。

（14）/actuator:所有endpoints的列表，即应用监控的接口列表。

（15）/auditevents:公开当前应用程序的审核事件信息。

 

 `` ：其中/shutdown是post请求，其他都是get请求。``

 `` 
   ：如果对日志级别进行修改，需要post请求，并且需要传送JSON（application/json）的数据格式。对其进行测试，发现如果是通过springboot主程序入口main启动程序，能够动态修改logback日志级别；如果是在linux下打包运行后，动态修改日志级别无效。
``

### 二、监控端点的管理
#### 1.如果上面的监控端点都不能访问，可能是你的管理端口经常被防火墙保护，不对外暴露也就不需要保护管理端点。这时候你需要在application.properties里加上：

management.security.enabled=false

也可以单个设置，比如endpoints.mappings.sensitive=false，关闭/mappings的安全限制。

#### 2.自定义端点访问路径和访问端口

默认情况下，监控的接口端口和业务的端口一致。比如
###### url 拦截前缀名称
management.contextPath=/jvm
###### 缺省使用原http 端口
management.port=20001

那么这时候我们访问/info监控接口的url是：http://127.0.0.1:9020/hsz/jvm/autoconfig。如果刚好你在业务接口里面也有一个/info接口，访问路径刚好就是跟http://xxx.xxx.xxx.xxx:20000/student/info一样，那么得到的信息是监控端点info的信息，这个业务接口无效。如果想让它有效呢？那你可以禁用info监控端点，但是如果info这个端点需要返回一些信息不能禁用，而你的业务info接口别的程序已经调用了，你也不想更改，那怎么才能让它有效呢？这时候我们可以通过在application.properties中进行以下设置：

