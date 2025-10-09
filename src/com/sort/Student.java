package com.sort;

import java.util.Comparator;
import java.util.ArrayList;

public class Student implements Comparable<Student> {
	private String name;
	private int age;
	private int score;


	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

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
				'}' + '\n';
	}

	@Override
	public int compareTo(Student o) {
		// 想升序
		// 即 this < o时，返回负数，从而让其在前面
		// this > o时，返回正数，从而让其在后面
		if (this.score > o.score) {
			return 1;
		} else if (this.score < o.score) {
			return -1;
		}
		// 用 this.name - o.name 来比较, 此时是升序
		// 因为如果 this字符值 < o字符值，返回负数，所以this在前，即 升序
		return this.name.compareTo(o.name);
	}
}
