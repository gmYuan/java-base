package com.ygm;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamFn {

	// 1 打印出 [start, end] 之间的偶数- IntStream.range
	public static void printOddNumbersBetween(int start, int end) {
		IntStream.range(start, end + 1)
				.filter(i -> i % 2 == 0)
				.mapToObj(i -> i + ",")
				.forEach(System.out::println);
	}

	// 2 打印出字符串中的 大写字母的个数- str.chars()
	public static int countUpperCaseLetters(String str) {
		return (int) str.chars()
				.filter(Character::isUpperCase)
				.count();
	}

	// 4 把 多个话里的 每个单词都合并到一个数组内- Stream::of
	public static List<String> mergeWords(String... words) {
		return Arrays.asList(words).stream()
				.map(word -> word.split(" "))
				.flatMap(Stream::of)
				.collect(Collectors.toList());
	}


	// 5 打印出任意一个姓为张的 User的姓名
	public static void findUserZhang(User... users) {
		User resUser =  Arrays.stream(users)
				.filter(user -> user.getName().startsWith("张"))
				.findAny()
				.orElseThrow(IllegalAccessError::new);
		System.out.println("resUser的姓名是：" + resUser.getName());

		// 以上实现 相当于
		// Optional<User> optionalUser = filteredUsers.findAny();
		// optionalUser.orElseThrow(IllegalStateException::new);
		// optionalUser.ifPresent(System.out::println);

		// 注意，Optional应该像上面这样，一般情况下 作为函数式的返回值来使用，而不是用if-else
		// if (optionalUser.isPresent()) System.out.println(optionalUser.get().getName());
		// else throw new IllegalStateException();
	}



	public static void main(String[] args) {
		// 1 打印出 [start, end] 之间的偶数
		// printOddNumbersBetween(1, 13);

		// 2 统计字符串中的大写字母的个数
		System.out.println(countUpperCaseLetters("Hello World"));

		// 3 去看看用户里面有没有人姓李- Arrays.asList
		List<User> users = Arrays.asList(
				new User("张三", 18),
				new User("李四", 28),
				new User("王五", 38)
		);
		System.out.println("是否有姓李的 User- " +
				users.stream().anyMatch(user -> user.getName().startsWith("李"))
		);

		// 4 把 多个话里的 每个单词都合并到一个数组内
		System.out.println("merge words结果是："
				+ mergeWords("I am a boy", "I have a dog")
		);

		// 5 findUserZhang
		findUserZhang(
				new User("李四", 28),
				new User("张五", 38),
				new User("张三", 18)
		);

	}
}
