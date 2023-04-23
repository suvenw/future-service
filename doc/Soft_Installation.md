## [主营项目接口文档](http/http-index.md)



##  中间组件配置

### 环境组件要求；
 >> java jdk version 1.8.0_162
 >> 
 
### zookeeper 安装和配置

###  kafka  kafka_2.12 安装与配置

###### 修改kafka 启动脚本：`增加加粗列`
 >> kafka-server-start.sh 

- if [ "x$KAFKA_HEAP_OPTS" = "x" ]; then
-    export KAFKA_HEAP_OPTS="-Xmx1G -Xms1G"
-    **_export JMX_PORT="9999"_**
- fi

 >> 
 
  - kafka 监听ip:port
  - listeners=PLAINTEXT://10.100.0.222:9092
  - advertised.listeners=PLAINTEXT://10.100.0.222:9092
  - kafka 日志目录
  - log.dirs=/tmp/kafka-logs
  -   zookeeper 聚群ip:端口
  - zookeeper.connect=10.100.0.222:2181,10.100.0.222:2182,10.100.0.222:2183
  -    zookeeper 超时时间
  -  zookeeper.connection.timeout.ms=6000
  
### kafka-admin kafka-manager-1.3.3.7 后台管理 安装和配置 

######  修改`application.conf` 和增加系统环境

  - #kafka-manager.zkhosts="10.100.0.222:2181"
  -  #kafka-manager.zkhosts=${?ZK_HOSTS}
  
###### kafka-manager 项目启动 和端口
   nohup bin/kafka-manager -Dconfig.file=/data/program/kafka-manager/conf/application.conf -Dhttp.port=9000 

### RocketMQ 安装和配置
###### 使用模式： 2m-2s-async

###### config 
- brokerClusterName=DefaultCluster
- brokerName=broker-a
- brokerId=1
- deleteWhen=04
- fileReservedTime=48
- brokerRole=SLAVE
- flushDiskType=ASYNC_FLUSH

###### 启动脚本命令：
#! /bin/sh

- APP_PATH=/data/program/rocketmq

- nohup sh mqnamesrv -n "10.100.0.222:9876" >$APP_PATH/logs/mqnamesrv.log 2>&1 &
- nohup sh mqbroker -n "10.100.0.222:9876" -c $APP_PATH/conf/broker.properties autoCreateTopicEnable=true >$APP_PATH/logs/mqbroker.log 2>&1 &

- 1.mqnamesrv
- 2.mqbroker

###### rocketmq-console 项目启动 和端口

- java -Xms64m -Xmx512m -jar $APP_PATH/lib/rocketmq-console-ng-1.0.0.jar --server.port=9888 --rocketmq.config.namesrvAddr=10.100.0.222:9876 2>&1  | /usr/sbin/rotatelogs $APP_PATH/logs/rocketmq-console_%Y%m%d.log 86400


### ACTIVEMQ 安装和配置
- 已上线，忽略；


### cat cat.2.0 安装和配置
### cat-home cat-home.2.0 安装和配置


### dubbo 安装和配置

### dubbo-admin 安装和配置







