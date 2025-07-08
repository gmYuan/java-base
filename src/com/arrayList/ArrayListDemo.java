package com.arrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayListDemo {

	public static void main(String[] args) {
		// 如何构造声明 ArrayList
		// 1. 不带类型的
		ArrayList list = new ArrayList();

		// 2 带类型的: 泛型传入的必须是 引用类型
		ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
		// 会有类型限制提示
		// integerArrayList.add("2")

		// 3 ⭐⭐️ 【推荐写法：List + 钻石修饰符】
		List<String> list2 = new ArrayList<>();
		list2.add("2");

		List<Integer> list3 = new ArrayList<>();
		list3.add(4);

		// 4 内部类写法
		class User {
			private String name;
			private int age;
		}
		List<User> list4 = new ArrayList<>();
		list4.add(new User());

		// 5 可以传入一个Collection类型的参数
		Collection<Integer> c = new ArrayList<>(200);
		c.add(1);
		c.add(2);
		c.add(3);
		List<Integer> list5 = new ArrayList<>(c);


		// 6 size /isEmpty /contains



	}
}
