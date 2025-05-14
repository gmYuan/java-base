package com.baseLearn.java.oop;

public class OOP_02_Driver {
	String name;
	int age;
	String sex;
	
	public OOP_02_Driver() {
		this.name = "momo";
		this.age = 1;
	}
	
	public OOP_02_Driver(String name, int age, String sex) {
		this.name = name;
		this.age = age;
		this.sex = sex;
	}
	
	
	void show() {
		System.out.println("OOP_02_Driver的show方法被调用了");
	}
	
	public static void main(String[] args) {
		OOP_02_Driver driver1 = new OOP_02_Driver();
		System.out.println("司机1的名字是：" + driver1.name);
		System.out.println("司机1的年龄是：" + driver1.age);
		System.out.println("司机1的性别是：" + driver1.sex);
		
		System.out.println("----------司机2----------");
		OOP_02_Driver driver2 = new OOP_02_Driver("haha", 18, "男");
		System.out.println("司机2的名字是：" + driver2.name);
		System.out.println("司机2的年龄是：" + driver2.age);
		System.out.println("司机2的性别是：" + driver2.sex);
		
	}
}

