#!/bin/bash

SERVER_MAIN=com.suven.framework.FileMain

SERVER_NAME=FileMain

SERVER_PORT=9030
# prod ,dev ,test, stage
SERVER_ENV=test

COMMAND="$1"

if [[ "$COMMAND" != "start" ]] && [[ "$COMMAND" != "stop" ]] && [[ "$COMMAND" != "restart" ]]; then
	echo "Usage: $0 | start | stop | restart"
	exit 0
fi

SERVER_PATH=$(cd `dirname $0`; pwd)

echo " ${SERVER_PATH}"

if [ ! -d "${SERVER_PATH}/logs" ]; then
  mkdir ${SERVER_PATH}/logs
fi

echo " ${SERVER_PATH}"

# check the start.out log output file
if [ ! -f "${SERVER_PATH}/app.log" ]; then
  touch "${SERVER_PATH}/app.log"
fi

# ------------ start 配置 ------------------
# prod ,dev ,test, stage
function start()
{

 echo "--------JAVA ${SERVER_MAIN} 服务启动开始 --------"

#defuat 环境使用
JAVA_OPTS='-Xms256m -Xmx256m -Xmn128m -Xss1m'
if [[ "$SERVER_ENV" == "prod" ]]; then
  # 生产环境
	JAVA_OPTS='-Xms2048m -Xmx2048m -Xmn128m -Xss1m'
elif [[ "$SERVER_ENV" == "stage" ]]; then
	 #灰度环境
  JAVA_OPTS='-Xms1024m -Xmx1024m -Xmn128m -Xss1m'
fi
 #参考 RocketMQ
JAVA_OPTS_EXT=' -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+CMSClassUnloadingEnabled -XX:SurvivorRatio=8 -XX:-UseParNewGC -verbose:gc  -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=30m -XX:-OmitStackTraceInFastThrow -XX:-UseLargePages'

## JAVACMD="$JAVA_HOME/bin/java"
JAVACMD="$JAVA_HOME/bin/java"

# Reset the SERVER_LIB variable. If you need to influence this use the environment setup file.
SERVER_LIB="${SERVER_PATH}/lib/*"
#应用扩展包或模块包
SERVER_LIB_EXT="${SERVER_PATH}/lib_ext/*"
# Reset the SERVER_LIB variable. If you need to influence this use the environment setup file.
SERVER_ETC="${SERVER_PATH}/etc/"

CLASSPATH="$SERVER_ETC:$SERVER_LIB:$SERVER_LIB_EXT"

 exec -a  "$SERVER_NAME" "$JAVACMD" -server $JAVA_OPTS $JAVA_OPTS_EXT $ARGS -classpath "$CLASSPATH" $SERVER_MAIN  $SERVER_PORT \
   -Dapp.pid="$$" \
   -Dapp.repo="$SERVER_LIB" \
   -Dapp.runing="java" \
   -Dbasedir="$SERVER_PATH" \
   --spring.profiles.active=${SERVER_ENV}  > /dev/null  2>&1 &

    echo "--------JAVA ${SERVER_MAIN} 服务启动成功--------"
    echo "--------欢迎使用 ${SERVER_MAIN}  广州团队微服 CRM ^_^--------"
    sleep 1
    tail -f ${SERVER_PATH}/app.log

}
# ------------ stop 配置 ------------------
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
# ------------ restart 配置 ------------------
if [[ "$COMMAND" == "start" ]]; then
	start
elif [[ "$COMMAND" == "stop" ]]; then
  stop
else
    stop
    start
fi
