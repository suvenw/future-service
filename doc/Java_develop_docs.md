# future-service

#### 介绍
### future-service Java开发规范

#### 前言

这份文档是 Java编程风格规范的定义。有且只有一个Java源文件符合此文档中的规则， 我们才认为它符合Java编程风格
制定者 : 服务组成员
适用范围 : Java开发人员
术语说明





class 可表示一个普通类，枚举类，接口或是注解类型
comment只用来指代实现的注释
javadoc是文档的说明文档
项目命名规范

#### 项目前缀

http 对外提供短链接服务返回结果对象类
rpc 内部调用的SOA的服务返回结果对象类


#### 项目命名规范

##### 服务


小写+-
mps-soa-cli, xxx-logic,xxx-logic
类库

小写+-
top-lib
开发基础规范

开发构建工具为gradle
基础类库为top-lib,根据需要引入对应的类库
如有需要添加插件，邮件申请，具体由XXX负责审核
源文件

##### 文件名

源文件以其最顶层的类名.java来命名
文件编码

源文件编码格式为UTF-8。
特殊字符

特殊字符都要进行转义
具有特殊转义序列的任何字符(\b, \t, \n, \f, \r, \“, \‘及)，我们使用它的转义序列
尽量使用Unicode,并加上注释，包括中文
源文件结构

一个源文件包含(按顺序)：

package语句
import语句
有且只有一个顶级类
以上每个部分之间用一个空行隔开。
package语句

package top.suven.base.http..*

import语句


import不要使用通配符*
import 必须使用量到类名;方便排查重名类
import语句可分为以下几组，按照这个顺序，每组由一个空行分隔：
第三方的包。每个顶级包为一组，字典序。例如：android, com, junit, org, sun
java imports
javax imports
组内不空行，按字典序排列
类声明

有且只有一个顶级类声明

顶级类放在他同名的文件中(UserUtil.java)
类成员顺序

每个类应该以某种逻辑去排序它的成员，维护者应该要能解释这种排序逻辑
按逻辑来排序，而不是按时间顺序排序
方法重载，这些函数/方法应该按顺序出现在一起，中间不要放进其它函数/方法。


##### 代码结构:(后期执行)
1. 类名与头部规范
	a、作者1、类名一定要有标头注释，应该包含以下几个属性：
	b、创建时间
	c、该类的作用和一些概要的说明
类的命名规范
DAO的实现类命名为：XxxDao
Service层接口实现类命名为：XxxService
访问层controller的命名为：XxxController
单元测试类的命名为：XxxDaoImplTest、XxxControllerTest
      注：Xxx 为对象名称

2、方法一定要有注释、应该包含以下几个内容：
	a、方法的作用，实现什么功能或解决什么问题
	b、必要的参数解释
	c、返回值的解释，例如：@return null ：失败，非null：成功

3、包文件类按模块分类存放，遵循MVC设计模式，例如：

	a、单个项目
	top.suven.future	                           // 顶级包名 
	    |__ future-http                            // 统一请求规范入口实现模块
		|    |__ http                              // 架构 http 模块名称
		|    |__ config     		               // InterceptorConfiguration,实现动态加载按规范编写的拦截处理类,和返回结果业务对象后的业务处理实现
		|    |__ data.vo		                   // 所有http 返回封装对象类,int code, String msg, T data , long times;
		|    |__ exception                         // 接收或返回前端口对象包目录(View Object)
		|    |__ filter                            // CatFilterConfigure(大众点评cat的拦截器),HttpDoSFilter(jetty防Dos的拦截器)
		|    |__ handler                           // HandlerMethodArgumentResolver 业务数据分类转换实现业务逻辑类
		|    |__ interceptor                       // 各业务分类实现拦截器,包括全局日志LogbackTraceId,URL检查,JSON转换,Parameter校验,White白名单,Black黑名单,Token验证,Version软件版本验证,Redis CDN缓存;
        |    |  |__ jetty 			               // http应用服务统一启动实现类，
		|    |__ message				   		   // http 请求接收基本参数实现类
        |    |    |__ processor.url                // 解释http 请求的url相关标签配置,包括白名单,是否强制登陆验证,CDN缓存时间
        |    |    |__ rpc                          // RPC 请求的基础类
        |    |    |__ validator                    // 验证业务实现逻辑
        |__ future__global                         // 架构 全局参数
        |    |__ common                            // 公共包目录
        |    |    |__ api                          // api EntityBean 实现接口,接口文档实现标签
        |    |    |__ cat                          // cat 监控类实现标签
        |    |    |__ constants                    // constants
        |    |    |__ data                         // BaseAipEntity 实现bean包目录
        |    |    |__ enums                        // 系统已定义的枚举类
        |    |__ http                              // http 模块名称
        |    |    |__ data.vo     		           // 构架基础的请求参数对象bean, 通过id,page,List实现查找类对象
        |    |    |__ exception                    // 业务运行异常类,和业务处理异常BusinessLogicException
        |    |    |__ inters                       // 启动服务动态规范接口
        |    |    |__ validator                    // 参数校验接口,文字说明类
        |    |    |__ JsonParse                    // 表单提交转换java bean 实现处理转类  
        |__ future-utils                           // 架构 通过工具类
        |     |__ common                           // 公共包目录
        |     |   |__ aop                          // 项目切面实现
        |     |   |__ excel                        // excel 文档导入导出
        |     |   |__ constants                    // constants
        |     |   |__ platform                     // 第三方登陆
        |     |   |__ util                         // 第三方配置参数工具类
        |     |__ core                             // http 模块名称
        |     |    |__ aiyun.sms     		       // 阿里云api,包括短信验证
        |     |    |__ baidu                       // baidu api
        |     |    |__ config                      // 参数配置类
        |     |    |__ db                          // 动态多数据库,主-从(1-N)加载实现逻辑实现
        |     |    |__ es                          // es搜索实现 api 
        |     |    |__ jetty                       // jetty 服务启动与 Abstract抽象实现
        |     |    |__ lock                        // redis 锁和并发销实现
        |     |    |__ mybatis                     // mybatis 实现与api base实现代码
        |     |    |__ redis                       // 扩写redis对就的jedis api,支持多组多聚群api,  
        |     |    |__ spring                      // spring 项目的扩张类
        |     |    |__ rocketmq.starter            // rocketmq api 管理业务 
        |     |__ util                             // 业务工具类
        |     |    |__ bean                        // Bean 处理业务,包括 类拷贝,类泛型类获取,枚举转换工具类,简体字与繁体字相互转换
        |     |    |__ constants                   // 启动服务动态规范接口
        |     |    |__ createcode                  // 生成api 文档目录
        |     |    |  |__ swagger                  // 生成api 文档实现
        |     |    |__ crypt                       // 加密实现类
        |     |    |__ date                        // 时间处理类
        |     |    |__ gif                         // git 图片处理类
        |     |    |__ http                        // http ok3 实现api
        |     |    |__ ids                         // id生成类,uuid, workerId花算法实现全局唯一ID
        |     |    |__ io                          // 文件读写
        |     |    |__ ips                         // ip相关业务类
        |     |    |__ json                        // json 扩写类
        |     |    |__ location                    // 地理位置处理类
        |     |    |__ metric                      // Metric 监控实现类
        |     |    |__ proxy                       // 代理实现类
        |     |    |__ qrcode                      // 二维码实现类
        |     |    |__ random                      // 随机生成实现类
        |     |    |__ tool                        // 工具类 
        |     |    |__ validate                    // 验证实现类
        |     |    |__ zip                         // 压缩实现类 
        |     |__ test                             // 测试架构类
        |           |__ junit                      // 实现api
        |           |__ rule                       // 测试规则实现
		|__ 业务模块名	
        |    |__ controller		                   // 控制层，请求总入口。处理请求分发，参数校验等
        |    |__ facade		                       // service组装层，提供给controller使用
        |    |__ vo                                // 接收或返回前端口对象包目录(View Object)
        |    |    |__ request                      // 接收前端接口对象包目录(request)
        |    |        |__ BeanRequestVo            // 接收前端接口对象Bean(BeanRequestVo)
        |    |    |__ response                     // 返回前端接口对象包目录(response)
        |    |        |__ BeanResponseVo           // 返回前端接口对象Bean(BeanResponseVo)
        |    |__ service/serviceImpl			   // 服务层接口与实现类，
        |    |__ dto						       // 接收或返回RPC微服务接口对象包目录(View Object)
        |    |    |__ request                      // 接收业务接口请求对象包目录(request)
        |    |    |   |__ BeanRequestDto           // 接收业务接口请求对象Bean(BeanRequestVo)
        |    |    |__ response                     // 返回业务接口返回对象包目录(response)
        |    |         |__ BeanResponseDto         // 返回业务接口返回对象Bean(DepartResponseDto)
        |    |__ dao						       // 数据库业务编写实现包类,或jdbc_dao.java 或mybatis_dao.java
        |    |__ cache						       // 一个实体类对应redis缓存实现或扩写实现
        |    |__ mapper						       // 一个实体类对应mybatis的实现和xml配置
        |    |__ entity	                           // 数据库表对应的实体类,一个数据库一张表对应一个实体对象，
		|__ future-http-system			       // 后台管理系统
		|	 |__ controller
		|	 |__ service
		|	 |__ dao
		|    |....(如上)
		|__ future-vue                             //基于jeecg 架构改写的前端架构
		    
		


大括号

使用大括号

即使只有一条语句(或是空)，也应该把大括号写上
非空块：K & R 风格

对于非空块和块状结构，大括号遵循Kernighan和Ritchie风格 (Egyptian brackets):
左大括号前不换行
左大括号后换行
右大括号前换行
如果右大括号是一个语句、函数体或类的终止，则右大括号后换行; 否则不换行。例如，如果右大括号后面是else或逗号，则不换行。
示例：
```
return new MyClass() {
 @Override 
public void method() {
  if (condition()) {
    try {
      something();
    } catch (ProblemException e) {
      recover();
    }
  }
}
};
```
空块：可以用简洁版本

一个空的块状结构里什么也不包含，大括号可以简洁地写成{}，不需要换行
void doNothing() {}
if(true){ // 这种格式重量写上大括号,方便下面维护和增加方法的可请性;
   function();
   //方便维护人再增加方法,或类型操作;
   //funcationB()
}
块缩进

使用IDEA的默认格式化代码 , 加上tab+space来缩进代码
符号与属性间,增加空格,增加代码的可读性
eg:  null  != info 代码 info!=null
 a + b  = c  代码  a+b=c; 
一行一个语句

每个语句后要换行
列限制：100

任何一行如果超过这个字符数限制，必须自动换行
合理的变量方法命名
例外：
不可能满足列限制的行(例如，Javadoc中的一个长URL，或是一个长的JSNI方法参考)
package和import语句
注释中那些可能被剪切并粘贴到shell中的命令行
自动换行

从哪里断开

自动换行的基本准则是：更倾向于在更高的语法级别处断开
如果在非赋值运算符处断开，那么在该符号前断开(比如+，它将位于下一行),这条规则也适用于以下“类运算符”符号
如果在赋值运算符处断开，通常的做法是在该符号后断开(比如=，它与前面的内容留在同一行)。这条规则也适用于foreach语句中的分号
方法名或构造函数名与左括号留在同一行
逗号(,)与其前面的内容留在同一行
自动换行时缩进至少+4个空格

自动换行时，第一行后的每一行至少比第一行多缩进4个空格,或2个Tab
两个连续行使用相同的缩进当且仅当它们开始于同级语法元素
用小括号来限定组：推荐

去掉小括号也不会使代码被误解，或是去掉小括号能让代码更易于阅读，否则我们不应该去掉小括号
命名规范

##### 枚举类

枚举常量间用逗号隔开，换行可选。
没有方法和文档的枚举类可写成数组初始化的格式：
private enum Suit { CLUBS, HEARTS, SPADES, DIAMONDS }
由于枚举类也是一个类，因此所有适用于其它类的格式规则也适用于枚举类
变量声明

每次只声明一个变量

不要使用组合声明，比如int a, b, 常用变化量名,尽量具体有意义,方法内允许简写;
需要时才声明，并尽快进行初始化

在第一次需要使用它时才声明,声明后尽快进行初始化
class中的成员变量的命名

camel命名法
int userAge; //camel命名
##### 数组

数组初始化：可写成块状结构

数组初始化可以写成块状结构
[]是类型的一部分
int[] objArr
switch语句

##### 缩进

每个switch标签后新起一行，再缩进，写下一条或多条语句
Fall-through：注释

在一个switch块内，每个语句组要么通过break, continue, return或抛出异常来终止，要么通过一条注释来说明程序将继续执行到下一个语句组
```
switch (input) {
case 1:
case 2:
  prepareOneOrTwo();
  // fall through
case 3:
  handleOneTwoOrThree();
  break;
default:
  handleLargeNumber(input);
}
```
default的情况要写出来

每个switch语句都包含一个default语句组，即使它什么代码也不包含
注解(Annotations)

注解紧跟在文档块后面，应用于类、方法和构造函数，一个注解独占一行
@Override
@Nullable
public String getNameIfPresent() { ... }
##### 注释

块注释风格

块注释与其周围的代码在同一缩进级别。它们可以是/* ... */风格，也可以是// ...风格。对于多行的/* ... */注释，后续行必须从*开始， 并且与前一行的*对齐
/*
 * This is          // And so           /* Or you can
 * okay.            // is this.          * even do this. */
 */
注释不要封闭在由星号或其它字符绘制的框架里
Modifiers

类和成员的modifiers如果存在，则按Java语言规范中推荐的顺序出现(逻辑顺序)
命名约定

对所有标识符都通用的规则

标识符只能使用ASCII字母和数字，因此每个有效的标识符名称都能匹配正则表达式\w+
标识符类型的规则

##### 包名

package的名字由小写组成
自动构建的类也要遵循规则
package top.suven.xxx.thrift;
package top.suven.xxx.soa;

##### 类名

class命名采用Pascal命名法
```
public class UserUtil
{
}
```
类名通常是名词或名词短语，接口名称有时可能是形容词或形容词短语
测试类的命名以它要测试的类的名称开始，以Test结束
##### 方法名

camel命名法
方法名通常是动词或动词短语
public void setNum(){};
##### 常量名

全大写，同时要包含完整定义
public static final String NSQ_CONFIG_PATH = "xxx";
这些名字通常是名词或名词短语
##### 非常量字段名

非常量字段名以CamelCase风格编写
这些名字通常是名词或名词短语
##### 参数名

参数名要有意义
camel命名
入参不要超过3个，太多就用object或map
public void setAllInfo(int age,String name){};
##### 局部变量名

camel命名
需要的时候创建并尽快初始化
##### 类型变量名

单个的大写字母，后面可以跟一个数字(如：E, T, X, T2)
以类命名方式(5.2.2节)，后面加个大写的T(如：RequestT, FooBarT)
@Override：能用则用

只要是合法的，就把@Override注解给用上
捕获的异常：不能忽视

所有捕获的异常都要处理
如果它确实是不需要在catch块中做任何响应，需要做注释加以说明
静态成员：使用类进行调用

使用类名调用静态的类成员，而不是具体某个对象或表达式。
```
Soa soa = ...;
Soa.dosth(); // good
soa.dosth(); // bad
getSoa().dosth(); // very bad
Finalizers: 禁用
```
极少会去重载Object.finalize。
Javadoc

#### 格式

一般形式

Javadoc块的基本格式如下所示：
```
/**
* Multiple lines of Javadoc text are written here,
* wrapped normally...
*/
public int method(String p1) { ... }
```
或者是以下单行形式：
```
/** An especially short bit of Javadoc. */
```

基本格式总是OK的。当整个Javadoc块能容纳于一行时(且没有Javadoc标记@XXX)，可以使用单行形式
Javadoc标记

标准的Javadoc标记按以下顺序出现：@param, @return, @throws, @deprecated, 前面这4种标记如果出现，描述都不能为空
#### 分页

每页数量 pageSize
nextId nextId
NOTE

foreach中不能干的事

创建连接
数据库查询
io操作
kv存储
memcached
redis


 
技术架构：
-----------------------------------
#### 开发环境

- 语言：Java 8

- IDE(JAVA)： IDEA / Eclipse

- IDE(前端)： WebStorm,Visual Studio Code 或者 IDEA 

- 依赖管理：Gradle

- 数据库：MySQL8.x+  &  Oracle 11g & Sqlserver2017

- 缓存：Redis


#### 后端
- 框架管理： gradle

- 基础框架：spring mvc 5.1.8.RELEASE, Spring Boot 2.1.1.RELEASE,jetty-9.4.12.v20180830,

- 微服务架构: 注册配置中心: [nacos 服务注册与发现](https://nacos.io/zh-cn/), [dubbo 微服务(可选)](http://dubbo.apache.org/en-us/index.html) [Sentinel 服务限流降级]

- 持久层框架：Mybatis-plus_3.1.2, jdbc

- 安全框架：Apache Shiro 1.4.0，Jwt_3.7.0

- 数据库连接池：阿里巴巴Druid 1.1.10

- 缓存框架：redis

- 日志打印：logback

- 基于独创的基础架构,实现自动化成业务模块;实现前后台端分享,

- 其他：RocketMQ,Kafka, Jetty, Cat(大众点评监控), ELK(elasticsearch,logstash,Kibana),全局日志唯一跟踪ID,动态多数库源(主-从分离)，结合Swagger-ui,自创生成api文档 等。

## 主要功能
    
* **服务限流降级**：默认支持 WebServlet、WebFlux, OpenFeign、RestTemplate、Spring Cloud Gateway, Zuul, Dubbo 和 RocketMQ 限流降级功能的接入，可以在运行时通过控制台实时修改限流降级规则，还支持查看限流降级 Metrics 监控。
* **服务注册与发现**：适配 Spring Cloud 服务注册与发现标准，默认集成了 Ribbon 的支持。
* **分布式配置管理**：支持分布式系统中的外部化配置，配置更改时自动刷新。
* **消息驱动能力**：基于 Spring Cloud Stream 为微服务应用构建消息驱动能力。
* **分布式事务**：使用 @GlobalTransactional 注解， 高效并且对业务零侵入地解决分布式事务问题。。
* **阿里云对象存储**：阿里云提供的海量、安全、低成本、高可靠的云存储服务。支持在任何应用、任何时间、任何地点存储和访问任意类型的数据。
* **分布式任务调度**：提供秒级、精准、高可靠、高可用的定时（基于 Cron 表达式）任务调度服务。同时提供分布式的任务执行模型，如网格任务。网格任务支持海量子任务均匀分配到所有 Worker（schedulerx-client）上执行。
* **阿里云短信服务**：覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道。


更多功能请参考 [Roadmap](https://github.com/alibaba/spring-cloud-alibaba/blob/master/Roadmap-zh.md)。

## 组件

**[Sentinel](https://github.com/alibaba/Sentinel)**：把流量作为切入点，从流量控制、熔断降级、系统负载保护等多个维度保护服务的稳定性。

**[Nacos](https://nacos.io/zh-cn/)**：一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。

**[RocketMQ](https://rocketmq.apache.org/)**：一款开源的分布式消息系统，基于高可用分布式集群技术，提供低延时的、高可靠的消息发布与订阅服务。

**[Dubbo](https://github.com/apache/dubbo)**：Apache Dubbo™ 是一款高性能 Java RPC 框架。

**[Seata](https://github.com/seata/seata)**：阿里巴巴开源产品，一个易于使用的高性能微服务分布式事务解决方案。

**[Alibaba Cloud OSS](https://www.aliyun.com/product/oss)**: 阿里云对象存储服务（Object Storage Service，简称 OSS），是阿里云提供的海量、安全、低成本、高可靠的云存储服务。您可以在任何应用、任何时间、任何地点存储和访问任意类型的数据。

**[Alibaba Cloud SMS](https://www.aliyun.com/product/sms)**: 覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道。

更多组件请参考 [Roadmap](https://github.com/alibaba/spring-cloud-alibaba/blob/master/Roadmap-zh.md)。


