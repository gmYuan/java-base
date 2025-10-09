package com.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.sort.Student;


public class ObjectSortDemo {
	public static void main(String[] args) {
		Student[] arr1 = new Student[4];
		arr1[0] = new Student("张三", 18, 78);
		arr1[1] = new Student("李四", 19, 94);
		arr1[2] = new Student("ww", 20, 23);
		arr1[3] = new Student("赵六", 20, 67);
		arr1[3] = new Student("aa", 29, 23);

    // System.out.println("arr1[0]: " + arr1[0]);

		// 排序前
		System.out.println("排序前：" + Arrays.toString(arr1));
		// 排序
		Arrays.sort(arr1);
		// 排序后
		System.out.println("排序后：" + Arrays.toString(arr1));

	}
}