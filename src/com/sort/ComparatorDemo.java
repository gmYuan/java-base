package com.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Arrays;


class StringComparator implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}

}

public class ComparatorDemo {
	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
		list1.add("ffg");
		list1.add("dde");
		list1.add("csa");
		list1.add("acd");
		list1.add("aaa");
		list1.add("ggg");

		// 方法1：写Comparator的实现类，实例化
		Comparator<String> strComparator = new StringComparator();
		// 等价于 Collections.sort(list1, strComparator);
		list1.sort(strComparator);

		// 遍历的 lambda 表达式
		// list1.forEach(System.out::println);
		 System.out.println("排序后的list1: " + list1);

		 // 方法2：匿名类--> 实现Comparator接口
		Student[] students = new Student[5];
		students[0] = new Student("dexd", 18, 90);
		students[1] = new Student("bcdd", 19, 80);
		students[2] = new Student("wwaa", 20, 70);
		students[3] = new Student("aase", 17, 60);
		students[4] = new Student("ddde", 16, 70);

		// 方法2：匿名类--> 实现Comparator接口
		Arrays.sort(students, new Comparator<Student>() {
			@Override
			public int compare(Student o1, Student o2) {
				return o1.getScore() - o2.getScore();
			}
		});
		System.out.println("-------方法2------------- ");
		System.out.println("排序后的students: " + Arrays.toString(students));


		// 方法3：lambda表达式
     //	等价于 Arrays.sort(students, (o1, o2) -> o1.getScore() - o2.getScore());
		Arrays.sort(students, Comparator.comparingInt(Student::getScore));
		System.out.println("-------方法3------------- ");
		System.out.println("排序后的students: " + Arrays.toString(students));

	}

}
