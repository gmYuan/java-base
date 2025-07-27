package com.arrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayListBulkDemo {

	public static void main(String[] args) {
		// bulkOptions:  containsAll/ removeAll/ retainAll/ addAll
		System.out.println("-------containsAll------------");
		List<String> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		list1.add("a");
		list1.add("a");
		list1.add("b");
		list1.add("A");

		list2.add("A");
		list2.add("B");
		list2.add("C");
		//containsAll: 在AbstractCollection实现，并不是在ArrayList里面实现
		boolean containsRes = list1.containsAll(list2);
		System.out.println("containsRes是：" + containsRes);

		// addAll
		System.out.println("--------------addAll----------------");
		// 方法1：直接调用, 把list2的元素添加到list1的末尾
		list1.addAll(list2);
		// [a, a, b, A, A, B, C]
		System.out.println("list1是：" + list1);

		// 方法2：传入一个索引，把list2的元素添加到list1的指定位置
		list1.addAll(1, list2);
		// [a, A, B, C, a, b, A, A, B, C]
		System.out.println("指定索引添加后，list1是：" + list1);

		// removeAll
		System.out.println("--------------removeAll----------------");
		// 方法1：直接调用, 把list2的元素从list1中移除
		Collection<String> list3 = new ArrayList<>();
		// 可以有多个a，无影响
		list3.add("a");
		list3.add("a");
		list3.add("Y");
		list3.add("A");
		// [a, A, B, C, a, b, A, A, B, C]--> [B, C, b, B, C]
		boolean removeRes = list1.removeAll(list3);
		System.out.println("removeRes是：" + removeRes);
		System.out.println("list1是：" + list1);

		// retainAll: 类似于交集操作，返回的是list1中包含list2的元素
		// 但是允许list1返回的包含重复元素，所以不完全是交集
		System.out.println("--------------retainAll----------------");
		List<String> list5 = new ArrayList<>();
		List<String> list6 = new ArrayList<>();
		list5.add("a");
		list5.add("a");
		list5.add("b");
		list5.add("b");
		list5.add("c");

		list6.add("A");
		list6.add("a");
		list6.add("z");
		boolean retainRes = list5.retainAll(list6);
		System.out.println("retainRes是：" + retainRes);
		System.out.println("list5是：" + list5);









	}
}
