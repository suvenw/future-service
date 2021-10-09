## [主营项目接口文档](http/http-index.md)


### 开发测试环境地址：
> 测试环境 127.0.0.1

> 开发环境 10.xxx.0.xxx

### supervisorctl 软件安装

###  supervisorctl 管理项目
> supervisorctl  restart/start/stop  http-xx/rpc-xxx/all



### 增加supervisorctl启动项目配置，
>  1）vi  /data/program/anaconda/etc/supervisord.conf 
> 
> 2） 配置内容如下： rpc-user为启动脚本的名称
>>  [program:rpc-user]
>>  command=/data/server/rpc-user-service/server.sh
>> directory=/data/server/rpc-user-service/

> 
> 3)项目启动脚 server.sh 文件如下： 备注(脚本文件必须是顶行文件)
```
#!/bin/bash

PATH_LIB=./lib

CLASSPATH=./etc

JVM_PARAM='-Xms4g -Xmx4g -Xmn1g -Xss1m'

#包名+启动类名
RUN_MAIN=top.suven.xxx.oauth.RpcOauthMain
#项目运行自定义名,用于supervisord启动或关闭
SERVER_NAME=rpc-oauth

SERVER_PORT=29020

for jar in `ls $PATH_LIB/*.jar`

do

      CLASSPATH="$CLASSPATH:""$jar"

done

exec -a $SERVER_NAME java -server $JVM_PARAM $ARGS -classpath "$CLASSPATH" $RUN_MAIN $SERVER_PORT >> work/stdout.log

```

#### supervisord 项目进程管理  
Supervisor重新加载配置启动新的进程
* 1、添加好配置文件后
* 2、更新新的配置到supervisord  supervisorctl update
* 3、重新启动配置中的所有程序  supervisorctl reload
* 4、启动某个进程(program_name=你配置中写的程序名称) supervisorctl start program_name
* 5、查看正在守候的进程  supervisorctl
* 6、停止某一进程 (program_name=你配置中写的程序名称) pervisorctl stop program_name
* 7、重启某一进程 (program_name=你配置中写的程序名称)  supervisorctl restart program_name
* 8、停止全部进程 supervisorctl stop all
* 注意：显示用stop停止掉的进程，用reload或者update都不会自动重启。

#### 关闭所有项目
> supervisorctl all 

> ps -ef | grep super
> root  23847     1  0 13:04 ?        00:00:01 /data/program/anaconda/bin/python /data/program/anaconda/bin/supervisord


### 重启：

> /data/program/anaconda/bin/python /data/program/anaconda/bin/supervisord

### 项目部署结构
> eg :/data/server/http-user-service

#### 项目根目录：
> /data/server/

##### 项目名称： http-user-service

##### 项目结构： 
> etc  ：项目文件
>
> lib  ： 应用包文件
>
> work ： 日志文件
>
> server.sh ： 启动脚本



