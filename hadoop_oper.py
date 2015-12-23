
# 启动cluster集群
sbin/hadoop-daemon.sh start namenode
sbin/hadoop-daemons.sh start datanode
sbin/yarn-daemon.sh start resourcemanager
sbin/yarn-daemons.sh start nodemanager
sbin/mr-jobhistory-daemon.sh start historyserver


# wordcount  例子，用来检查集群是否配置成功
bin/hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-examples-2.6.0.jar wordcount /in /out
bin/hadoop jar ./share/hadoop/mapreduce/hadoop-mapreduce-examples-2.6.0.jar wordcount /in/sherlock_homles7.txt /out



bin/hdfs dfs -lsr /

bin/hdfs dfs -rmr /

bin/hdfs dfs -cat /out/*
bin/hdfs dfs -cat /ouu1/*

# 关闭集群
sbin/stop-yarn.sh
sbin/stop-dfs.sh






# 1. test script for hadoop streaming:

1.1
hadoop dfs -rmr /out1

1.2
hadoop jar /home/hadoop/hadoop/share/hadoop/tools/lib/hadoop-streaming-2.6.0.jar -mapper /home/hadoop/python_script/mapper.py -reducer /home/hadoop/python_script/reducer.py -input /in/file -output /out1 -file *.py

1.3 
hadoop dfs -text /out1/*

------------
# 2. test hive

2.1 
hadoop dfs mkdir /hmbbs_logs

2.2
CREATE EXTERNAL TABLE hmbbs(ip string, atime string, url string) PARTITIONED BY (logdate string) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LOCATION '/hmbbs_logs';

2.3
hadoop dfs -copyFromLocal /home/hadoop/Downloads/access_2013_05_30.log /hmbbs_logs

hadoop dfs -copyFromLocal /home/hadoop/Downloads/access_2015 /hmbbs_logs


2.4 
ALTER TABLE hmbbs ADD PARTITION(logdate='2013_05_30') LOCATION '/hmbbs_logs/2013_05_30.log';
ALTER TABLE hmbbs ADD PARTITION(logdate='2013_06_01') LOCATION '/hmbbs_logs/access_2015';

2.5 

