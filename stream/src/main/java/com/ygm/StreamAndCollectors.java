package com.ygm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StreamAndCollectors {

	// 2 Stream.collect() + Collectors.groupingBy的使用
	public static Map<String, List<User>> Collect(List<User> users) {
		return users.stream()
				.sorted(Comparator.comparing(User::getAge))
				.collect(Collectors.groupingBy(User::getDepartment));
	}

	public static void main(String[] args) {
		// 1 Collectors-toCollection 使用
		List<User> users = Arrays.asList(
				new User("张三", 20),
				new User("张三疯", 15),
				new User("李四", 100),
				new User("张三", 200)
		);
		TreeSet<String> usersSet = users.stream()
				.filter(user -> user.getName().startsWith("张"))
				.sorted(Comparator.comparing(User::getAge))
				.map(User::getName)
				.collect(Collectors.toCollection(TreeSet::new));

		System.out.println("usersSet的值是：" + usersSet);

		// 2 Stream.collect() + Collectors.groupingBy的使用
		List<User> users2 = Arrays.asList(
				new User("张三", 20, "技术部"),
				new User("张三疯", 15, "市场部"),
				new User("李四", 100, "技术部"),
				new User("王五", 200, "售后部")
		);
		System.out.println("users的值是：" + Collect(users2));

		// 3 stream.parallel()的 使用- 高性能
		long t0 = System.currentTimeMillis();
		IntStream.range(1, 5000_0000).filter(i -> i % 5 == 0).count();
		System.out.println("串行耗时：" + (System.currentTimeMillis() - t0));

		long t1 = System.currentTimeMillis();
		IntStream.range(1, 100_0000).parallel().filter(i -> i % 3 == 0).count();
		System.out.println("并行耗时：" + (System.currentTimeMillis() - t1));

	}
}
