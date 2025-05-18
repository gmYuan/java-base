package com.baseLearn.java.access;

public class AccessBestPracticeDemo {
	 private int id;
	 private String name;
	 private int age;
	 
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public AccessBestPracticeDemo(int id, String name) {
		this.name = name;
		this.id = id;
		this.age = 100;
	}
	
	private void showRealName() {
		System.out.printf("当前真是年龄是 \n", this.age);
	}
	
	 public void PrintBestInfo() {
		System.out.printf("id2是%d, name2是%s \n", this.id, this.name);
	}


}
