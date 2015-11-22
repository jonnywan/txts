
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

// 添加  file://， 以读取本地文件路径  "/home/hadoop/tmp/customers.txt" 为单机本地路径
// val textFile = sc.textFile("file:///home/hadoop/soft/spark-1.4.0-bin-hadoop2.6/README.md")

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


// 从hdfs读取数据  /in/customers.txt 是hdfs上面的数据
val customer = sc.textFile("/in/customers.txt").map(_.split('|')).map(r=>Row(r(0),r(1).trim.toInt,r(2),r(3))) 
val dataFrame1 = sqlContext.createDataFrame(customer, schema1)
dataFrame1.printSchema

dataFrame1.registerTempTable("customer")
sqlContext.sql("select name from customer").collect.foreach(println)
sqlContext.sql("select * from customer where gender='M' and age < 30").collect().foreach(println)

// 五 Apache Spark上跑Logistic Regression算法
// 数据集可以从UCI机器学习库https://archive.ics.uci.edu/ml/datasets/qualitative_bankruptcy下载。
// 在Spark的安装文件夹中，创建一个新的文件夹命名为playground。复制 qualitative_bankruptcy.data.txt文件到这里面。

// 1、导入所需要的库
import org.apache.spark.mllib.classification.{LogisticRegressionWithLBFGS, LogisticRegressionModel}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.{Vector, Vectors}

// 2.接下来我们将创建一个Scala函数，将数据集中的qualitative数据转换为Double型数值。
def getDoubleValue( input:String ) : Double = {
    var result:Double = 0.0
    if (input == "P")  result = 3.0 
    if (input == "A")  result = 2.0
    if (input == "N")  result = 1.0
    if (input == "NB") result = 1.0
    if (input == "B")  result = 0.0
    return result
   }

// 3、我们可以读取到qualitative_bankruptcy.data.txt文件中的数据。
// 从Spark的角度来看，这是一个Transformation操作。在这个阶段，数据实际上不被读入内存。
// 如前所述，这是一个lazy的方式执行。实际的读取操作是由count()引发，这是一个Action操作。
val data = sc.textFile("playground/Qualitative_Bankruptcy.data.txt")
data.count()

// 4、为逻辑回归算法准备数据，将字符串转换为数值型。
val parsedData = data.map{line =
    val parts = line.split(",")
    LabeledPoint(getDoubleValue(parts(6)), Vectors.dense(parts.slice(0,6).map(x =getDoubleValue(x))))
}

// 使用“，”拆分字符串，并获得一个向量，命名为parts
// 创建并返回一个LabeledPoint对象。每个LabeledPoint包含标签和值的向量。
// 在我们的训练数据，标签或类别（破产或非破产）放在最后一列，数组下标0到6。
// 这是我们使用的parts(6)。在保存标签之前，我们将用getDoubleValue()函数将字符串转换为Double型。
// 其余的值也被转换为Double型数值，并保存在一个名为稠密矢量的数据结构。
// 这也是Spark的逻辑回归算法所需要的数据结构。
// Spark支持map()转换操作，Action动作执行时，第一个执行的就是map()

parsedData.take(10)

// 5、接着我们划分一下训练数据和测试数据，将parsedData的60%分为训练数据，40%分为测试数据。
val splits = parsedData.randomSplit(Array(0.6, 0.4), seed = 11L)
val trainingData = splits(0)
val testData = splits(1)

// 训练数据和测试数据也可以像上面一样，使用take()者count()查看。

// 6、我们现在开始使用Spark的LogisticRegressioinWithLBFGS()来训练模型。设置好分类个数，这里是2个（破产和非破产）	
val model = new LogisticRegressionWithLBFGS().setNumClasses(2).run(trainingData)

// 7、当模型训练完，我们可以使用testData来检验一下模型的出错率。
val labelAndPreds = testData.map { point =
  val prediction = model.predict(point.features)
  (point.label, prediction)
}
val trainErr = labelAndPreds.filter(r = r._1 != r._2).count.toDouble / testData.count

// 变量labelAndPreds保存了map()转换操作，map()将每一个行转换成二元组。
// 二元组包含了testData的标签数据(point.label，分类数据)和预测出来的分类数据(prediction)。
// 模型使用point.features作为输入数据。
// 最后一行代码，我们使用filter()转换操作和count()动作操作来计算模型出错率。
// filter()中，保留预测分类和所属分类不一致的元组。在Scala中_1和_2可以用来访问元组的第一个元素和第二个元素。
// 最后用预测出错的数量除以testData训练集的数量，我们可以得到模型出错率。


// 六、spark运行hdfsWordCount例子
./bin/run-example org.apache.spark.examples.streaming.HdfsWordCount hfds://hadoop1:9000/in/sherlock_homles7.txt


