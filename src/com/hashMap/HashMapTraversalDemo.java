package com.hashMap;

import java.util.*;

public class HashMapTraversalDemo {
	public static void main(String[] args) {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 100);
		map.put(2, 200);
		map.put(3, 300);
		map.put(4, 400);
		map.put(5, 500);

		// 遍历 HashMap

		// 方法1: Map.Entry 遍历
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			Integer key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key + " -> " + value);
		}

		// 方法2: keySet 遍历来获取key + 通过get(key) 获取value
		System.out.println("--------遍历方法2------------");
		for (Integer key : map.keySet()) {
			Integer value = map.get(key);
			System.out.println(key + " -> " + value);
		}

		// 方法3： 利用迭代器- Set的迭代器（一般工作中 较少使用）
		// 3.1 利用 map.entrySet()的 迭代器
		System.out.println("--------遍历方法3.1------------");
		Iterator<Map.Entry<Integer, Integer>> iter1 = map.entrySet().iterator();
		while (iter1.hasNext()) {
			Map.Entry<Integer, Integer> entry = iter1.next();
			Integer key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key + " -> " + value);
		}

		// 3.2 利用 map.keySet()的 迭代器
		System.out.println("--------遍历方法3.2------------");
		Iterator<Integer> iter2 = map.keySet().iterator();
		while (iter2.hasNext()) {
			Integer key = iter2.next();
			Integer value = map.get(key);
			System.out.println(key + " -> " + value);
		}


		// 方法4：lambda-  利用 forEach 遍历（JDK 1.8 新增）
		System.out.println("--------遍历方法4------------");
		map.forEach((key, value) -> {
			System.out.println(key + " -> " + value);
		});

		// 方法4.2：lambda- keySet.forEach
		// System.out.println("--------遍历方法4.1------------");
		// map.keySet().forEach(key -> {
		// 	System.out.println(key + " -> " + map.get(key));
		// });



	}
}
