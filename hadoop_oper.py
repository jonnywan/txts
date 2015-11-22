
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


sbin/stop-yarn.sh
sbin/stop-dfs.sh
