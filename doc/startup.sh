#!/bin/bash

#运行java启动的包类
SERVER_MAIN=com.suven.framework.FileMain
#运行项目自定义名称
SERVER_NAME=FileMain
#运行项目 http 端口号
SERVER_PORT=9030
# 运行项目环境类型: prod ,dev ,test, stage
SERVER_ENV=test
# 执行项目操作类型: 启动,停止,重启:
COMMAND="$1"

if [[ "$COMMAND" != "start" ]] && [[ "$COMMAND" != "stop" ]] && [[ "$COMMAND" != "restart" ]]; then
	echo "Usage: $0 | start | stop | restart"
	exit 0
fi
# 运行项目 部署根目录
SERVER_BASE_DIR=$(cd `dirname $0`; pwd)

echo " ${SERVER_BASE_DIR}"
# 运行项目 日志目录判断
if [ ! -d "${SERVER_BASE_DIR}/logs" ]; then
  mkdir ${SERVER_BASE_DIR}/logs
fi

# check the app.log log output file
if [ ! -f "${SERVER_BASE_DIR}/app.log" ]; then
  touch "${SERVER_BASE_DIR}/app.log"
fi

# ------------ start 配置 ------------------
# 项目启动脚本 prod ,dev ,test, stage
function start()
{

 echo "--------JAVA ${SERVER_MAIN} 服务启动开始 --------"

#defuat 环境使用
JAVA_OPTS='-Xms256m -Xmx256m -Xmn128m -Xss1m'
if [[ "$SERVER_ENV" == "stage" ]]; then
	 #灰度环境
  JAVA_OPTS='-Xms1024m -Xmx1024m -Xmn128m -Xss1m'
elif [[ "$SERVER_ENV" == "prod" ]]; then
  # 生产环境
  JAVA_OPTS='-Xms2048m -Xmx2048m -Xmn128m -Xss1m'
fi
 #参考 RocketMQ
JAVA_OPTS_EXT=' -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSClassUnloadingEnabled -XX:SurvivorRatio=8 -XX:-UseParNewGC -verbose:gc  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30m -XX:-OmitStackTraceInFastThrow -XX:-UseLargePages'

## JAVACMD="$JAVA_HOME/bin/java"
JAVACMD="${JAVA_HOME}/bin/java"

# 项目运行依赖包目录
SERVER_LIB="${SERVER_BASE_DIR}/lib/*"
#应用扩展包或模块包依赖包目录
SERVER_LIB_EXT="${SERVER_BASE_DIR}/lib_ext/*"
# 项目运行依赖配置文件目录
SERVER_ETC="${SERVER_BASE_DIR}/etc/"
# 项目运行依赖 环境配置
CLASSPATH="${SERVER_ETC}:${SERVER_LIB}:${SERVER_LIB_EXT}"
# 项目运行启动命令
 exec -a  "$SERVER_NAME" $JAVACMD -server $JAVA_OPTS $JAVA_OPTS_EXT $ARGS -classpath "$CLASSPATH" $SERVER_MAIN  $SERVER_PORT \
   -Dapp.pid="$$" \
   -Dapp.repo="${SERVER_LIB}" \
   -Dapp.runing="java" \
   -Dbasedir="$SERVER_BASE_DIR" \
   --spring.profiles.active=${SERVER_ENV}  > /dev/null  2>&1 &

    echo "--------JAVA ${SERVER_MAIN} 服务启动成功--------"
    echo "--------欢迎使用 ${SERVER_MAIN}  广州团队微服 CRM ^_^--------"
    sleep 1
    tail -f ${SERVER_BASE_DIR}/app.log

}
# ------------ 项目停止脚本 stop 配置 ------------------
function stop()
{
	PID=`ps -ef | grep "${SERVER_MAIN}" | grep -v "grep" | grep "java"| awk '{print $2}'`

	echo "PDI =[ $PID ] ---------------"

	if [ $? -eq 0 ]; then
			echo "process id:$PID"
	else
			echo "process  ${SERVER_MAIN} not exit"
			exit
	fi

	kill -9 ${PID}

	if [ $? -eq 0 ];then
			echo "kill  ${SERVER_MAIN} success"
	else
			echo "kill  ${SERVER_MAIN} fail"
	fi

	echo "PDI =[ $PID ] --------${SERVER_MAIN}  服务已关闭 end --------------"
	sleep 3

}
# ------------项目重启脚本 restart 配置 ------------------
if [[ "$COMMAND" == "start" ]]; then
	start
elif [[ "$COMMAND" == "stop" ]]; then
  stop
else
    stop
    start
fi
