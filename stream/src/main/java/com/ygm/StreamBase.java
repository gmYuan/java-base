package com.ygm;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class User {
	private String name;
	private int age;
	private String department;

	public User(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public User(String name, int age, String department) {
		this.name = name;
		this.age = age;
		this.department = department;
	}

	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}

	public String getDepartment() {
		return department;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", age=" + age +
				", department='" + department + '\'' +
				'}';
	}

}

public class StreamBase {

	// 实现方法 1： 传统方法
	static List<String> getNewUsersV1(List<User> users) {
		List<User> newUsers = new ArrayList<>();
		for (User user : users) {
			if (user.getName().startsWith("张")) {
				newUsers.add(user);
			}
		}
		// 按照年龄升序排序
		Collections.sort(newUsers, new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				if (o1.getAge() < o2.getAge()) return -1;
				if (o1.getAge() > o2.getAge()) return 1;
				return 0;
			}
		});

		List<String> res = new ArrayList<>();
		for (User user : newUsers) {
			res.add(user.getName());
		}
		return res;
	}

	// 实现方法 2： stream 方式
	static List<String> getNewUsersV2(List<User> users) {
		List<String> res = users.stream()
				.filter(user -> user.getName().startsWith("张"))
				.sorted(Comparator.comparing(User::getAge))
				.map(User::getName)
				.collect(Collectors.toList());
		return res;
	}


	public static void main(String[] args) {
		List<User> users = new ArrayList<>();
		users.add(new User("李四", 28));
		users.add(new User("张五", 38));
		users.add(new User("张七", 90));
		users.add(new User("张三", 18));

		// 请把姓张的用户挑出来，把他们按照年龄排序，然后把他们的名单报告给我

		// 实现方法 1： 传统方法实现
		List<String> newUsersV1 = getNewUsersV1(users);
		System.out.println("传统实现方式：" + newUsersV1);

		// 实现方法 2： stream 方法实现
		List<String> newUsersV2 = getNewUsersV2(users);
		System.out.println("stream实现方式：" + newUsersV2);

	}


}
