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

	}
}
