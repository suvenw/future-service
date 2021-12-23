#!/bin/bash

PATH_LIB=./lib

CLASSPATH=./etc

JVM_PARAM='-Xms128m -Xmx512m -Xmn64m -Xss1m'

RUN_MAIN=com.sixeco.framework.FileMain

SERVER_NAME=FileMain

SERVER_PORT=9030

SERVER_ENV=test

for jar in `ls $PATH_LIB/*.jar`

do

      CLASSPATH="$CLASSPATH:""$jar"

done

exec -a $SERVER_NAME java -server $JVM_PARAM $ARGS -classpath "$CLASSPATH" $RUN_MAIN $SERVER_PORT --spring.profiles.active=${SERVER_ENV}
