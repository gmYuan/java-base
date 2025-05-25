package com.baseLearn.java.oop.encapsulation;

public class OtherUser {
	public static void main(String[] args) {
		User user = new User("张三", "123456");
		user.login("张三", "123456");
		System.out.println("登录状态：" + user.isLogin());
	}

}
