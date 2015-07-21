
// 一、常用的Transformations：
1.filter(func) 
// Purpose: new RDD by selecting those data elements on which func returns true
val rdd = sc.parallelize(List("ABC","BCD","DEF")) 
val filtered = rdd.filter(_.contains("C")) 
filtered.collect() 
// Result: 
Array[String] = Array(ABC, BCD)

2.map(func) 
// Purpose: return new RDD by applying func on each data element
val rdd = sc.parallelize(List(1,2,3,4,5)) 
val times2 = rdd.map(_2) 
times2.collect() 
// Result: 
Array[Int] = Array(2, 4, 6, 8, 10)

3.flatMap(func) 
// Purpose: Similar to map but func returns a Seq instead of a value. 
// 		For example, mapping a sentence into a Seq of words
val rdd=sc.parallelize(List("Spark is awesome","It is fun")) 
val fm=rdd.flatMap(str=>str.split(" ")) 
fm.collect() 
// Result: 
Array[String] = Array(Spark, is, awesome, It, is, fun)

4.reduceByKey(func,[numTasks]) 
// Purpose: To aggregate values of a key using a function. 
// 		“numTasks” is an optional parameter to specify number of reduce tasks
val word1=fm.map(word=>(word,1)) 
val wrdCnt=word1.reduceByKey(_+_) 
wrdCnt.collect() 
// Result: 
Array[(String, Int)] = Array((is,2), (It,1), (awesome,1), (Spark,1), (fun,1))

5.groupByKey([numTasks]) 
// Purpose: To convert (K,V) to (K,Iterable<V>)
val cntWrd = wrdCnt.map{case (word, count) => (count, word)} 
cntWrd.groupByKey().collect() 
// Result: 
Array[(Int, Iterable[String])] = Array((1,ArrayBuffer(It, awesome, Spark, fun)), (2,ArrayBuffer(is)))

6.distinct([numTasks]) 
// Purpose: Eliminate duplicates from RDD
fm.distinct().collect() 
// Result: 
Array[String] = Array(is, It, awesome, Spark, fun)


// 二、常用的集合操作：
1.union() 
// Purpose: new RDD containing all elements from source RDD and argument.
val rdd1=sc.parallelize(List('A','B')) 
val rdd2=sc.parallelize(List('B','C')) 
rdd1.union(rdd2).collect() 
// Result: 
Array[Char] = Array(A, B, B, C)

2.intersection() 
// Purpose: new RDD containing only common elements from source RDD and argument.
rdd1.intersection(rdd2).collect() 
// Result: 
Array[Char] = Array(B)

3.cartesian() 
// Purpose: new RDD cross product of all elements from source RDD and argument
rdd1.cartesian(rdd2).collect() 
// Result: 
Array[(Char, Char)] = Array((A,B), (A,C), (B,B), (B,C))

4.subtract() 
// Purpose: new RDD created by removing data elements in source RDD in common with argument
rdd1.subtract(rdd2).collect() 
// Result: 
Array[Char] = Array(A)

5.join(RDD,[numTasks]) 
// Purpose: When invoked on (K,V) and (K,W), this operation creates a new RDD of (K, (V,W))
val personFruit = sc.parallelize(Seq((“Andy”, “Apple”), (“Bob”, “Banana”), (“Charlie”, “Cherry”), (“Andy”,”Apricot”))) 
val personSE = sc.parallelize(Seq((“Andy”, “Google”), (“Bob”, “Bing”), (“Charlie”, “Yahoo”), (“Bob”,”AltaVista”))) 
personFruit.join(personSE).collect() 
// Result: 
Array[(String, (String, String))] = Array((Andy,(Apple,Google)), (Andy,(Apricot,Google)), (Charlie,(Cherry,Yahoo)), (Bob,(Banana,Bing)), (Bob,(Banana,AltaVista)))

6.cogroup(RDD,[numTasks]) 
// Purpose: To convert (K,V) to (K,Iterable<V>)
personFruit.cogroup(personSE).collect() 
// Result: 
Array[(String, (Iterable[String], Iterable[String]))] = Array((Andy,(ArrayBuffer(Apple, Apricot),ArrayBuffer(google))), (Charlie,(ArrayBuffer(Cherry),ArrayBuffer(Yahoo))), (Bob,(ArrayBuffer(Banana),ArrayBuffer(Bing, AltaVista))))


// 三、常用的actions
1.count() 
// Purpose: get the number of data elements in the RDD
val rdd = sc.parallelize(List('A','B','c'))
rdd.count() 
// Result: 
long = 3

2.collect() 
// Purpose: get all the data elements in an RDD as an array
val rdd = sc.parallelize(List('A','B','c'))
rdd.collect() 
// Result: 
Array[char] = Array(A, B, c)

3.reduce(func) 
// Purpose: Aggregate the data elements in an RDD using this function which takes two arguments and returns one
val rdd = sc.parallelize(List(1,2,3,4))
rdd.reduce(_+_) 
// Result: 
Int = 10

4.take(n) 
// Purpose: : fetch first n data elements in an RDD. computed by driver program.
val rdd = sc.parallelize(List(1,2,3,4))
rdd.take(2) 
// Result: 
Array[Int] = Array(1, 2)

5.foreach(func) 
// Purpose: execute function for each data element in RDD. 
// 		usually used to update an accumulator(discussed later) or interacting with external systems.
val rdd = sc.parallelize(List(1,2,3,4))
rdd.foreach(x=>println("%s10=%s". format(x,x10))) 
// Result: 
110=10 410=40 310=30 210=20

6.first() 
// Purpose: retrieves the first data element in RDD. Similar to take(1)
val rdd = sc.parallelize(list(1,2,3,4))
rdd.first() 
// Result: 
Int = 1

7.saveAsTextFile(path) 
// Purpose: Writes the content of RDD to a text file or a set of text files to local file system/ HDFS
val hamlet = sc.textFile("/home/hadoop/test.txt") 
hamlet.filter(_.contains("hello")).saveAsTextFile("/home/hadoop") 
// Result: 
akuntamukkala@localhost~/temp/filtered$ ls 
[OUTPUT]_SUCCESS part-00000 part-00001


// 四、Sparksql
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext


// 
import org.apache.spark.sql._
import org.apache.spark.sql.types._
val sqlContext = new org.apache.spark.sql.SQLContext(sc)

val schema1 =
  StructType(
 StructField("name", StringType, false) ::
 StructField("age", IntegerType, true) :: 
  StructField("gender", StringType, false) ::
 StructField("address", StringType, false) ::Nil)

val customer = sc.textFile("/home/hadoop/tmp/customers.txt").map(_.split('|')).map(r=>Row(r(0),r(1).trim.toInt,r(2),r(3))) 
val dataFrame1 = sqlContext.createDataFrame(customer, schema1)
dataFrame1.printSchema

dataFrame1.registerTempTable("customer")
sqlContext.sql("select name from customer").collect.foreach(println)
sqlContext.sql("select * from customer where gender='M' and age < 30").collect().foreach(println)



// registerTempTable
import org.apache.spark.sql._
import org.apache.spark.sql.types._
val sqlContext = new org.apache.spark.sql.SQLContext(sc)

val schema =
  StructType(
 StructField("name", StringType, false) ::
 StructField("age", IntegerType, true) :: Nil)

val people =
  sc.textFile("examples/src/main/resources/people.txt").map(_.split(",")).map(p => Row(p(0), p(1).trim.toInt))
val dataFrame = sqlContext.createDataFrame(people, schema)
dataFrame.printSchema
// root
// |-- name: string (nullable = false)
// |-- age: integer (nullable = true)

dataFrame.registerTempTable("people")
sqlContext.sql("select name from people").collect.foreach(println)


