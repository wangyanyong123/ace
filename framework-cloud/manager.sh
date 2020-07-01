#!/bin/sh

insert() {
    eval "$1=\"$2\""
}
get_value() {
    eval "echo \$$1"
}
insert center "Started CenterBootstrap "
insert auth "Started AuthBootstrap "
insert admin " 完成初始化加载用户"
insert dict " 完成初始化加载用户"
insert gate "Started GateBootstrap "
insert jinmao " 完成初始化加载用户"
insert app " 完成初始化加载用户"
insert tool " 完成初始化加载用户"
insert im " 完成初始化加载用户"
insert external " 完成初始化加载用户"
insert schedule " 完成初始化加载用户"

curPath=`pwd`

startAll() {
    start center
    start auth
    start admin
    start dict
    start gate
    start jinmao
    start app
    start tool
    start im
    start external
    start schedule
    echo "all started!!!"
}
start() {
    module=$1
    flag=`get_value $module`
    if [[ "$module" == "auth" ]]
    then
        module="$module-server"
    fi
    if [[ "$module" == "gate" ]]
    then
        module="$module-server"
    fi
    if [[ "$module" == "external" ]]
    then
        module="$module-service"
    fi
    if [[ "$module" == "schedule" ]]
    then
        module="$module-wo"
    fi
    module="ace-$module"
    modulePath=`find $curPath -d -name $module`
    if [[ "$modulePath" == "" ]]
    then
        return
    fi

    logDir=$curPath/logs
    if [ ! -d $logDir ]
    then
        mkdir $logDir
    fi

    logFile=$logDir/$module.log
    echo > $logFile

    cd $modulePath
    nohup mvn spring-boot:run >> $logFile >&1 &
    echo $module" starting..."
    count=1
    while [ -f $logFile ]
    do
        result=`grep "$flag" $logFile`
        if [[ "$result" != "" ]]
        then
            echo $module" started"
            break
        else
            count=$[$count+1]
            if [ $count -gt 90 ]
            then
                echo $module" start failed!!!"
                break
            fi
            sleep 1
        fi
    done
    echo $module","$! >> $curPath/ace.file
}

stopAll() {
    stop center
    stop auth
    stop admin
    stop dict
    stop gate
    stop jinmao
    stop app
    stop tool
    stop im
    stop external
    stop schedule

    rm -f `pwd`/ace.file
    echo "all stopped!!!"
}

stop() {
    curPath=`pwd`
    module=$1
    if [[ "$module" == "auth" ]]
    then
        module="$module-server"
    fi
    if [[ "$module" == "gate" ]]
    then
        module="$module-server"
    fi
    if [[ "$module" == "external" ]]
    then
        module="$module-service"
    fi
    if [[ "$module" == "schedule" ]]
    then
        module="$module-wo"
    fi
    module="ace-$module"

    pid_file=$curPath/ace.file
    cat $pid_file | while read pid
    do
        array=(${pid//,/ })
        if [[ ${array[0]} == $module ]]
        then
            kill -9 ${array[1]}
        fi
    done
    echo $module" stopped"
}

clean() {
    mvn clean
    echo 'done!'
}

case "$1" in
'start')
if [ ! -n "$2" ] ;then
    startAll
else
    start $2
fi
;;
'stop')
if [ ! -n "$2" ] ;then
    stopAll
else
    stop $2
fi
;;
'clean')
if [ ! -n "$2" ] ;then
    clean
else
    clean $2
fi
;;
*)
echo "Usage: $0 {start|stop|clean}"
exit 1
esac
exit 0