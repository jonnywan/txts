# spark-cluster
一、环境准备
0. 启动 hdfs
	－－ 参见hadoop_oper.py

1. 启动spark集群   sbin/start-all.sh
	-- jps -- master/worker

2. 页面查看集群状况
	--192.168.2.30:8080/

3. 进入spark的bin目录，启动spark-shell控制台   bin/spark-shell
4. 访问 master:4040  看到spark WEBUI页面

二、环境测试
1. 读取hdfs数据，统计数量
val readmefile = sc.textFile("hdfs://hadoop1:9000/in/sherlock_homles7.txt")
readmefile.count()
