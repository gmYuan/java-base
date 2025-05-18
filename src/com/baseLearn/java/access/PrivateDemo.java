package com.baseLearn.java.access;

public class PrivateDemo {
	private int id;
	private String name;
	
	public PrivateDemo( int id, String name) {
		this.name = name;
		this.id = id;
	}
	
	private void printInfo() {
		System.out.printf("id是%d, name是%s \n", this.id, this.name);
	}


}
