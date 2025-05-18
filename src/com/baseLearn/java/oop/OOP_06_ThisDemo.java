package com.baseLearn.java.oop;

public class OOP_06_ThisDemo {
	
	private String name;
	private int age;
	
	public OOP_06_ThisDemo() {
		System.out.println("参构造方法的this" + this);
	}
	
	public OOP_06_ThisDemo(String name) {
		this();
		System.out.println("我是666");
		this.name = name;
		System.out.println("1个参构造方法的this" + this);
		System.out.println("1个参构造方法的name " + this.name);
	}
	
	public OOP_06_ThisDemo(String name, int age) {
		this();
		this.name = name;
		this.age = age;
		System.out.println("2个参构造方法的this" + this);
		System.out.println("2个参构造方法的name" + this.name);
	}
	
	public void showInfo() {
		System.out.println("show方法中的this" + this);
		System.out.println("show方法中的name" + this.name);
	}
	
	public static void main(String[] args) {
		OOP_06_ThisDemo ex1 = new OOP_06_ThisDemo();
		ex1.showInfo();
		System.out.println("---------------------------------");
		
		OOP_06_ThisDemo ex2 = new OOP_06_ThisDemo("张三");
		ex2.showInfo();
		System.out.println("---------------------------------");
		
		OOP_06_ThisDemo ex3 = new OOP_06_ThisDemo("李四", 18);
		ex3.showInfo();
		
	
	}
}
