# encoding: utf-8

# bubble sort 0
# 每一个数值与它后面的每一个数值比较，如果大，则交换。
def BubbleSortOne(listIN):
	for i in range(len(listIN)):
		for j in range(i+1,len(listIN)):
			print listIN[i],listIN[j]
			if listIN[i] > listIN[j]:
				listIN[i],listIN[j] = listIN[j],listIN[i]
			print listIN

# 从后面往前循环 两两比较相邻记录
def BubbleSortTwo(listIN):
	for i in range(len(listIN)):
		# print listIN[i]
		for j in range(len(listIN)-1,i,-1):
			print listIN[j],listIN[j-1]
			if listIN[j] < listIN[j-1]:
				listIN[j],listIN[j-1] = listIN[j-1],listIN[j]
			print listIN

# 优化冒泡算法 添加标记变量flag 避免在已经有序的情况下的无意义循环判断
def BubbleSortThree(listIN):
	flag = True
	for i in range(len(listIN)):
		# print listIN[i]
		if flag == False:
			continue
		else:
			flag = False
			for j in range(len(listIN)-1,i,-1):
				print listIN[j],listIN[j-1]
				if listIN[j] < listIN[j-1]:
					listIN[j],listIN[j-1] = listIN[j-1],listIN[j]
					flag = True
				print listIN,flag

# 选择排序
# 通过n-i次关键字间的比较，从n-i+1个记录中选择出关键字最小的记录，并和第i个记录交换
def SelectSortOne(listIN):
	for i in range(len(listIN)):
		listIN_min = i
		for j in range(i+1,len(listIN)):
			if listIN[listIN_min] > listIN[j]:
				listIN_min = j
		if i != listIN_min:
			listIN[listIN_min], listIN[i] = listIN[i], listIN[listIN_min]
		print listIN

# 直接插入排序
# 将一个记录插入到已经排好序的有序表中，从而得到一个新的、记录数增1的有序表
def InsertSort(listIN):
	for i in range(2,len(listIN)):
		if listIN[i] < listIN[i-1]:
			listIN[0] = listIN[i]
			for j in range(i-1,0,-1):
				if listIN[j] > listIN[0]:
					listIN[j+1] = listIN[j]
			listIN[j+1] = listIN[0]

"""
    insertion_sort.py
    Implemenation of insertion sort on a list and returns a sorted list.
    Insertion Sort Overview:
    ------------------------
    Uses insertion of elements in to the list to sort the list.
    Time Complexity: O(n**2)
    Space Complexity: O(n) total
    Stable: Yes
    Psuedo Code: CLRS. Introduction to Algorithms. 3rd ed.
"""
def InsertSortTwo(seq):
    for n in range(1, len(seq)):
        item = seq[n]
        hole = n
        while hole > 0 and seq[hole - 1] > item:
            seq[hole] = seq[hole - 1]
            hole = hole - 1
        seq[hole] = item
    return seq

a = [22,3,25,34,6]
# 冒泡排序
# BubbleSortOne(a)
# BubbleSortTwo(a)
# BubbleSortThree(a)

# SelectSortOne(a)


print a[0]



































