package com.baseLearn.java.access;

public class DefaultDemo {
	 int id;
	 String name;
	
	 DefaultDemo(int id, String name) {
		this.name = name;
		this.id = id;
	}
	
	 void PrintDefaultInfo() {
		System.out.printf("id2是%d, name2是%s \n", this.id, this.name);
	}


}
