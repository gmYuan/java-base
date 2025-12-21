package com.reflection.invoke;

public class MyClass {
	private int count;

	public MyClass() {

	}

	public MyClass(int count) {
		this.count = count;
	}

	public void doNothing() {
		System.out.println("Do Nothing 方法执行了---");
	}

	public int calSum(int num1, String strNum2) {
			return num1 + Integer.parseInt(strNum2) + this.count;
	}

	private String calSum(String strNum1, String strNum2) {
			return "" + Integer.parseInt(strNum1) + Integer.parseInt(strNum2);
	}

	private static void calSum(String str) {
		System.out.println("calSum 静态方法执行：" + str + "Hello");
	}

}
