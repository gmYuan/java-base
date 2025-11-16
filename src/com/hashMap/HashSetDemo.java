package com.hashMap;

import java.util.*;

public class HashSetDemo {
	public static void main(String[] args) {
		// 构造HashSet
		Collection<Integer> c1 = new ArrayList<>();
		c1.add(1);
		c1.add(1);
		c1.add(2);
		c1.add(3);
		c1.add(3);
		Set<Integer> set1 = new HashSet<>(c1);
		System.out.println(set1);

    // add/ contains/ remove
		System.out.println("-----add/contains/remove-----");
		Set<String> set2 = new HashSet<>();
		set2.add("a");
		set2.add("b");
		set2.add("b");
		set2.add("c");
		set2.add("d");
		System.out.println("contains d: " + set2.contains("d"));
		set2.remove("d");
		System.out.println("contains d after remove: " + set2.contains("d"));
		System.out.println("set2: " + set2);

		// bulk operations
		System.out.println("-----bulk operations-----");
		Set<String> set3 = new HashSet<>();
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("d");
		list.add("f");
		set3.addAll(list);
		System.out.println("addAll list to set3: " + set3);
		set3.removeAll(set2);
		System.out.println("removeAll set2 from set3: " + set3);

		// 遍历方法： Iterator/foreach/lambda
		System.out.println("-----traversal methods-----");
		// Iterator
		Iterator<String> it = set3.iterator();
		while (it.hasNext()) {
			System.out.println("Iterator: " + it.next());
		}
		// foreach
		System.out.println("-----foreach-----");
		for (String s : set3) {
			System.out.println("foreach: " + s);
		}
		// lambda
		System.out.println("-----lambda-----");
		set3.forEach(System.out::println);
		set3.forEach(s -> System.out.println("lambda2: " + s));






	}
}
