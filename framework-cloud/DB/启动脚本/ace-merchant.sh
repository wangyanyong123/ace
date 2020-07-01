#!/bin/sh 
#该脚本为Linux下启动java程序的通用脚本。即可以作为开机自启动service脚本被调用， 
#也可以作为启动java程序的独立脚本来使用。 
# 
# 
#警告!!!：该脚本stop部分使用系统kill命令来强制终止指定的java程序进程。 
#在杀死进程前，未作任何条件检查。在某些情况下，如程序正在进行文件或数据库写操作， 
#可能会造成数据丢失或数据不完整。如果必须要考虑到这类情况，则需要改写此脚本， 
#增加在执行kill命令前的一系列检查。 
# 
# 
################################### 
#环境变量及程序执行参数 
#需要根据实际环境以及Java程序名称来修改这些参数 
################################### 

#JDK所在路径 
echo $JAVA_HOME 
#执行程序启动所使用的系统用户，考虑到安全，推荐不使用root帐号 
RUNNING_USER=root 
## java env
export JRE_HOME=$JAVA_HOME/jre

## you just need to change this param name
#1.svn路径
SVN_DIR=/mnt/svn/platform/jinmao/01_interface

#2.应用名称
SERVICE_NAME=ace-merchant
JAR_NAME=$SERVICE_NAME\.jar
#3.应用部署路径
SERVICE_DIR=/opt/jmdeploy/service

#4.maven打包路径
MAVEN_BUILD_DIR=$SVN_DIR/framework-cloud/ace-modules/ace-merchant
#5.maven打包目标jar
BUILD_SERVICE_DIR=$MAVEN_BUILD_DIR/target/$JAR_NAME


#6.调试端口
DEBUG_PORT='-Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=7888'
#7.java虚拟机启动参数 
JAVA_OPTS="-server -Xms128m -Xmx128m -XX:PermSize=128m -XX:MaxPermSize=128m -XX:MaxNewSize=128m -Xdebug -Xnoagent $DEBUG_PORT -Denv=FAT" 

cd $SERVICE_DIR 

################################### 
#(函数)判断程序是否已启动 
# 
#说明： 
#使用JDK自带的JPS命令及grep命令组合，准确查找pid 
#jps 加 l 参数，表示显示java的完整包路径 
#使用awk，分割出pid ($1部分)，及Java程序名称($2部分) 
################################### 
#初始化psid变量（全局） 
psid=0 

checkpid() { 
javaps=`$JAVA_HOME/bin/jps -l | grep -w $SERVICE_NAME | grep -v 'grep'`

if [ -n "$javaps" ]; then 
psid=`echo $javaps | awk '{print $1}'` 
else 
psid=0 
fi 
} 

################################### 
#(函数)启动程序 
# 
#说明： 
#1. 首先调用checkpid函数，刷新$psid全局变量 
#2. 如果程序已经启动（$psid不等于0），则提示程序已启动 
#3. 如果程序没有被启动，则执行启动命令行 
#4. 启动命令执行后，再次调用checkpid函数 
#5. 如果步骤4的结果能够确认程序的pid,则打印[OK]，否则打印[Failed] 
#注意：echo -n 表示打印字符后，不换行 
#注意: "nohup 某命令 >/dev/null 2>&1 &" 的用法 
################################### 
start() { 
checkpid 

if [ $psid -ne 0 ]; then 
echo "================================" 
echo "warn: $SERVICE_NAME already started! (pid=$psid)" 
echo "================================" 
else 
echo -n "Starting $SERVICE_NAME ...\n" 
echo -n "nohup $JRE_HOME/bin/java $JAVA_OPTS -jar $JAR_NAME --spring.profiles.active=prod >/mnt/logs/ace-merchant.log 2>&1 &...\n" 
nohup $JRE_HOME/bin/java $JAVA_OPTS -jar $JAR_NAME --spring.profiles.active=prod >/mnt/logs/ace-merchant.log 2>&1 &

checkpid 
if [ $psid -ne 0 ]; then 
echo "(pid=$psid) [OK]" 
else 
echo "[Failed]" 
fi 
fi 
} 

################################### 
#(函数)停止程序 
# 
#说明： 
#1. 首先调用checkpid函数，刷新$psid全局变量 
#2. 如果程序已经启动（$psid不等于0），则开始执行停止，否则，提示程序未运行 
#3. 使用kill -9 pid命令进行强制杀死进程 
#4. 执行kill命令行紧接其后，马上查看上一句命令的返回值: $? 
#5. 如果步骤4的结果$?等于0,则打印[OK]，否则打印[Failed] 
#6. 为了防止java程序被启动多次，这里增加反复检查进程，反复杀死的处理（递归调用stop）。 
#注意：echo -n 表示打印字符后，不换行 
#注意: 在shell编程中，"$?" 表示上一句命令或者一个函数的返回值 
################################### 
stop() { 
checkpid 

if [ $psid -ne 0 ]; then 
echo -n "Stopping $SERVICE_NAME ...(pid=$psid) " 
su - $RUNNING_USER -c "kill -9 $psid" 
if [ $? -eq 0 ]; then 
echo "[OK]" 
else 
echo "[Failed]" 
fi 

checkpid 
if [ $psid -ne 0 ]; then 
stop 
fi 
else 
echo "================================" 
echo "warn: $SERVICE_NAME is not running" 
echo "================================" 
fi 
} 

################################### 
#(函数)检查程序运行状态 
# 
#说明： 
#1. 首先调用checkpid函数，刷新$psid全局变量 
#2. 如果程序已经启动（$psid不等于0），则提示正在运行并表示出pid 
#3. 否则，提示程序未运行 
################################### 
status() { 
checkpid 

if [ $psid -ne 0 ]; then 
echo "$SERVICE_NAME is running! (pid=$psid)" 
else 
echo "$SERVICE_NAME is not running" 
fi 
} 

################################### 
#(函数)打印系统环境参数 
################################### 
info() { 
echo "System Information:" 
echo "****************************" 
echo `head -n 1 /etc/issue` 
echo `uname -a` 
echo 
echo "JAVA_HOME=$JAVA_HOME" 
echo `$JAVA_HOME/bin/java -version` 
 
echo "SERVICE_DIR=$SERVICE_DIR" 
echo "SERVICE_NAME=$SERVICE_NAME" 
echo "****************************" 
} 

################################### 
#(函数)重启服务代码
################################### 
restart(){
stop 
sleep 1
start 
}

################################### 
#(函数)更新代码
################################### 
update(){
echo "update code..."
su - $RUNNING_USER -c "cd $SVN_DIR && svn update" 
echo "update code done!"
}

################################### 
#更新代码&打包&重启服务 
################################### 
updateAndRestart(){
echo "updateAndRestart..."
update
su - $RUNNING_USER -c "cd $MAVEN_BUILD_DIR && mvn clean install -Dmven.test.skip=true" 
echo "build done!"
su - $RUNNING_USER -c "cp $BUILD_SERVICE_DIR $SERVICE_DIR/"
restart
echo "build done!"
}

################################### 
#读取脚本的第一个参数($1)，进行判断 
#参数取值范围：{start|stop|restart|status|info} 
#如参数不在指定范围之内，则打印帮助信息 
################################### 
case "$1" in 
'start') 
start 
;; 
'stop') 
stop 
;; 
'restart') 
restart
;; 
'status') 
status 
;; 
'info') 
info 
;; 
'updateAndRestart') 
updateAndRestart 
;; 
*) 
echo "Usage: $0 {start|stop|restart|status|info|updateAndRestart}" 
exit 1 
esac 
exit 0
