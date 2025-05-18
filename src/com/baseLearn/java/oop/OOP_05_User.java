package com.baseLearn.java.oop;

public class OOP_05_User {
	
	private String name;
	private int age;
	
	public static void updateInsInfo(OOP_05_User user) {
		user.name = "new Name";
		user.age = 28;
	}
	
	public static void main(String[] args) {
		OOP_05_User user1 = new OOP_05_User();
		user1.name = "user1";
		user1.age = 18;
		System.out.println("user1信息是：" + user1.name);
		System.out.println("user1信息是：" + user1.age);
		updateInsInfo(user1);
		System.out.println("user1的新信息是：" + user1.name);
		System.out.println("user1的新信息是：" + user1.age);
	}
}
