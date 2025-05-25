package com.baseLearn.java.oop.encapsulation;

public class User {
	private String userName;
	private String password;
	private boolean login;
	
	public boolean isLogin() {
		return login;
	}
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	 
	 public void login(String userName, String password) {
		 if(userName.equals(this.userName) && password.equals(this.password)) {
			 System.out.println("登录成功");
			 this.login  = true;
		 }else {
			 System.out.println("登录失败");
			 this.login  = false;
		 }
	 }
}
