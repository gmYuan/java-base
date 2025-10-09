package com.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class CollectionSortDemo {
	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<>();
		list1.add(14);
		list1.add(10);
		list1.add(3);
		list1.add(12);
		list1.add(1);
		// 排序前
		System.out.println("排序前：" + list1);
		// 排序
		Collections.sort(list1);
		// 排序后
		System.out.println("排序后：" + list1);

		// 例2
		System.out.println("-------Student---------");
		List<Student> list2 = new ArrayList<>();
		list2.add(new Student("an1", 45, 78));
		list2.add(new Student("gn2", 19, 94));
		list2.add(new Student("dn3", 22, 23));
		list2.add(new Student("cn4", 20, 67));
		list2.add(new Student("an5", 22, 94));
		// 排序前
		System.out.println("排序前：" + list2);
		// 排序
		Collections.sort(list2);
		// 排序后
		System.out.println("排序后：" + list2);

	}
}
