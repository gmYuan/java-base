package com.baseLearn.java.access;

public class MainDemo {
	public static void main(String[] args) {
		PrivateDemo ex1 = new PrivateDemo(1, "张三");
		// ex1.printInfo();
		
		DefaultDemo ex2 = new DefaultDemo(2, "李四");
		ex2.PrintDefaultInfo();
		
		AccessBestPracticeDemo ex3 = new AccessBestPracticeDemo(3, "王五");
		ex3.PrintBestInfo();
		System.out.println("ex3的id是：" + ex3.getId());
	}
}
