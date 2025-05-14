package com.baseLearn.java.oop;

public class OOP_03_overLoad {
	static final int MAX_SPEED = 500;
	static String carName;
	
	// 静态代码块会用于类的初始化
	static  {
		carName = "初始化小车";
		System.out.println("静态代码块:" + carName);
		
	}
	
	void upSpeed() {
		System.out.println("upSpeed");
	}
	void upSpeed(String time, int speed) {
		getName("奔驰");
		System.out.printf("当前时间是：%s, 当前加速是：%d \n", time, speed);
	
	}
	
	// ❌ 注意1： 方法重载和 传参类型有关，不能重复 定义相同的传参类型
	//	void upSpeed(int speed, String time2) {
	//		System.out.printf("当前加速是：%d \n", speed);
	//	}
	
	// ❌ 注意2： 方法重载和 传参类型有关，不能重复 定义相同的传参类型，哪怕他们的返回值类型不同
	// 因为 Java无法识别出，再调用相同传参方法时，到底是该返回void，还是返回String
	//	String upSpeed(int speed,  String time) {
	//		System.out.printf("当前时间是：%s, 当前加速是：%d \n", time, speed);
	//		return " up done!";
	//	}
	
	static void getName(String name) {
		carName = name;
		System.out.println("carName:" + carName);
	}
	
	
	public static void main(String[] args) {
		System.out.println("------------------------------");
		OOP_03_overLoad ex1 = new OOP_03_overLoad();
		ex1.upSpeed("2025-05.12", 100);
		System.out.println("当前最大限速是" + MAX_SPEED);
		
		System.out.println("------------------------------");
		final int MAX_SPEED = 200;
		System.out.println("main内部 最大限速是" + MAX_SPEED);
		
		// ❌ 常量的值不可被 重复赋值
		//  System.out.println("------------------------------");
		//  MAX_SPEED = 500;
		//  System.out.println("main内部 最大限速是" + MAX_SPEED);
		
		System.out.println("------------------------------");
		getName("宝马");
		OOP_03_overLoad.getName("奥迪");
	}
}


class Car {
	void show() {
		System.out.println("OOP_03_overLoad的 Car show");
	}
}