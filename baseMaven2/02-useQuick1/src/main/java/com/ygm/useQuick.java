package com.ygm;

public class useQuick {
	public static void main(String[] args) {
		MyService myService1 = new MyService();
		String res = myService1.grade(39);
		System.out.println("res: " + res);
	}
}
