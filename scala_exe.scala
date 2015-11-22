常量 val (建议)
变量 var



val xmax ymax = 100 //将xmax和ymax设为100

var greeting, message : String = null


// 数据类型
// Byte Char Short Int Long Float Double Boolean

import scala.math._

// sqrt pow min 


// chapter 2  控制结构和函数

// 2.1
if （ x>0) 1 else -1


if (n>0) {
	r = r * n
	n -=1
}

{} //块，一系列表达式
val distance = { val dx = x -x0; val dy = y -y0; sqrt(dx*da + dy*dy)}

// 输入输出
print("")
println()
printf("hello,s%! you are %d years old. \n", "fred",42)

//函数
def fac(c:Int) = {
	var r =1
	for (i <- 1 to n) r =r *1
	r
}

// 过程 函数体包含在花括号中但没有前面的＝号，返回类型为Unit
def box(s: String){  //没有＝号
	var border = "-" * s.length + "--\n"
	println(border + "|" + s + "|\n" + border)
}

// 过程 2
def box(s: String): Unit = {  //没有＝号
	...
}

// chapter 3  数组

// 3.1 定长数组
val nums = new Array[Int](10)

val a = new Array[String](10)

// 3.2 变长数组

ArrayBuffer
import scala.collection.mutable.ArrayBuffer

val b = ArrayBuffer[Int]()
b += 1
b ++ (12,3,3,4,5)

b.trimEnd(3) // 移除最后5个元素
b.insert() b.remove()
b.toArray()

// 遍历数组和数组缓冲
for (i <- 0 until a.length)
	println(i+":"+a(i))

//3.4 数组转化
val  a = Array(2,3,5,7,11)
val result = for (elem <-a) yield 2 * elem

//给定一个整数的数组缓冲，移除除第一个负数之外的所有负数
//solution 1
val first = true
var n =a.length
var i = 0
while (i<n){
	if(a(i) >= 0) i+=1
	else {
		if(first) {first = false;i +=1}
		else{ a.remove(i),n-=1 }
	}
}

//solution 2
//首先收集需要保留的下标
var first = true
val indexes = for (i <- 0 until a.length if first || a(i)>=0) yield {
	if (a(i) <0) first = false;i
}

<!-- 然后将元素移动到该去的位置，并截断尾端 -->
for (j <-0 until indexes.length) a(j) = a(indexes(j)) 
a.trimEnd(a.length - indexes.length)


//chapter 4
// 映射和元组

//4.1.1  不可变Map
val scores = Map ("Alice" -> 10,"Bob" -> 3, "cindy" -> 8)
//4.1.2  不可变的Map
val scores = scala.collection.mutable.Map("alice" -> 10,"Bob" -> 3, "cindy" -> 8)


//访问
scores("Bob")
//更新映射中的值
scores("Bob") = 11

scores += （"Bob" -> 10, "Fred" -> 7）
scores -= "Alice"

//4.4 迭代映射
for ((k,v) <- 映射) yield (v,k)


// chapter 5  class

class Counter{
	private var value = 0
	def increment(){value =+1}
	def current() = value
}

val myCounter = new Counter
myCounter.increment()
println(myCounter.current)

//5.2 getter setter













