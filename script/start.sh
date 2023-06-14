#!/bin/sh
export PATH='/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/usr/jdk/bin:/root/bin'

cd /root/reggie/

PORT=8080 # 需要检查的端口号

# 检查端口是否被占用，如果被占用，找到对应的进程 PID
PID=$(lsof -ti :$PORT)

if [ -n "$PID" ]; then
  # 检查进程是否是 Java 程序
  if ps -p $PID -o comm= | grep -q "java"; then
    echo "Java 进程 (PID: $PID) 正在占用端口 $PORT，正在终止进程..."
    kill $PID

    # 循环检查进程是否被终止
    while ps -p $PID > /dev/null; do
      echo "等待 Java 进程终止..."
      sleep 3

      # 如果进程仍在运行，尝试使用 kill -9 强制终止
      if ps -p $PID > /dev/null; then
        echo "Java 进程仍在运行，尝试使用 kill -9 强制终止..."
        kill -9 $PID
      fi
    done

    echo "已终止 Java 进程。"
  else
    echo "端口 $PORT 被非 Java 进程 (PID: $PID) 占用。"
  fi
else
  echo "端口 $PORT 未被占用。"
fi

echo "启动reggie服务端程序"
nohup java -jar /root/jar/reggie-0.0.1-SNAPSHOT.jar --spring.profiles.active=pro  > /root/jar/mylog.log 2>&1 &
echo "执行完成"
