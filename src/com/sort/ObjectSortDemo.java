package com.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObjectSortDemo {
	public static void main(String[] args) {
		Student[] arr1 = new Student[10];
		arr1[0] = new Student("张三", 18, 90);
		arr1[1] = new Student("李四", 19, 80);
		arr1[2] = new Student("王五", 20, 70);

		System.out.println("arr1[0]: " + arr1[0]);

		// 排序前
		System.out.println("排序前：" + Arrays.toString(arr1));
		// 排序
		Arrays.sort(arr1);
		// 排序后
		System.out.println("排序后：" + Arrays.toString(arr1));

	}
}

class Student implements Comparable<Student>  {
	private String name;
	private int age;
	private int score;
	public Student(String name, int age, int score) {
		this.name = name;
		this.age = age;
		this.score = score;
	}

	@Override
	public String toString() {
		return "Student{" +
				"name='" + name + '\'' +
				", age=" + age +  '\'' +
				", score=" + score + '\'' +
				'}';
	}

	@Override
	public int compareTo(Student o) {
		return 0;
	}
}
