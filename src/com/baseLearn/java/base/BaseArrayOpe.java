package com.baseLearn.java.base;

public class BaseArrayOpe {
	public static void main(String[] args) {
		// 创建/声明 数组
		int[] arr1 = new int[10];
		
		double[] arr2 = new double[20];
		
		boolean[] arr3 = new boolean[30];
		
		String[] arr4 = new String[40];
		
		BaseArrayOpe[] arr5 = new BaseArrayOpe[5];
		
		
		// 数组赋值
		// 静态初始化
		int[] scores = {1, 2, 3};
		
		Boolean[] test2 = {true, false};
		
		// 数组下标赋值, 注意下标idx不能越界
		arr1[0] = 1;
		arr1[9] = 9;
		
		// 访问数组元素
		System.out.println(arr1[0]);
		System.out.println(scores[2]);
		System.out.println(test2[1]);
		
		
		// 获取数组长度 + 遍历数组==> 方法1: for循环
		for (int i = 0; i < arr1.length; i++) {
			arr1[i] = i;
			System.out.println("当前arr1的值是：" + arr1[i]);
		}
		
		// for-each 循环
		System.out.println("---------------------------------" );
		for(int num : arr1) {
			System.out.println("for-each 当前num的值是：" + num);
		}
		
		
	}
	
}
