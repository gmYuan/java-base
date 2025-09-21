package com.sort;

import java.util.Arrays;

public class ArraySortDemo {
	public static void main(String[] args) {
		int[] arr1 = {14, 10, 3, 12, 1, 5, 20};
		// 排序前
		System.out.println("排序前：" + Arrays.toString(arr1));

		/*
		 k1: 对基本类型数组都提供了两种sort方法:
		   - 1.对整个数组排序
		   - 2.对数组中一段区间排序[fromIndex，toIndex)
		 */
		// 整体排序
    // Arrays.sort(arr1);
		// 对数组中一段区间排序[fromIndex，toIndex)
		Arrays.sort(arr1, 0, 3);
		System.out.println("排序后：" + Arrays.toString(arr1));
	}
}
