#!/bin/bash -e

# 1. 下载代码
echo "正在下载代码..."
mkdir -p tmpdir
cd tmpdir
git clone https://gitee.com/tjk1898/reggie-class-demo.git
cd reggie-class-demo
echo "代码下载完毕."

# 2. 本地npm打包vue程序
echo "正在打包程序..."
mvn clean compile package
JAR_NAME=$(mvn help:evaluate -Dexpression=project.build.finalName -q -DforceStdout).jar
echo ${JAR_NAME}
echo "打包完成."

# 2. ssh连接远程服务器，清空目标目录
REMOTE_USER="root"
REMOTE_HOST="49.232.144.133"
REMOTE_PATH="/root/reggie"
LOCAL_JAR_FILE="target/${JAR_NAME}"
LOCAL_START_SHELL="script/start.sh"
echo "正在连接远程服务器并清空目标目录..."
#ssh ${REMOTE_USER}@${REMOTE_HOST} 'if [ -f ${REMOTE_PATH}/${JAR_NAME} ]; then rm -f ${REMOTE_PATH}/${JAR_NAME}; fi'
ssh ${REMOTE_USER}@${REMOTE_HOST} "rm -rf ${REMOTE_PATH}/*"
echo "远程目录清空完成."

## 3. 将jar和启动文件，发送到远端目录
echo "将Jar和start.sh发送到远端目录..."
scp -r ${LOCAL_JAR_FILE} ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}/
scp -r ${LOCAL_START_SHELL} ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}/
echo "内容已发送到远端目录."

# 4. 运行jar
echo "正在启动远程服务..."
ssh ${REMOTE_USER}@${REMOTE_HOST} "/root/reggie/start.sh ${JAR_NAME}"
echo "远程服务启动完毕."


# 5. 清理本地环境
echo "正在清理本地文件..."
cd ../../
rm -rf tmpdir
echo "脚本执行完毕."
