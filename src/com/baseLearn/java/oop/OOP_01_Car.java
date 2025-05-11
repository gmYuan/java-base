package com.baseLearn.java.oop;

public class OOP_01_Car {
	// 知识点1.1 状态/属性
	int topSpeed;
	String color;
	int totalSeat;
	
	// 知识点3.1 构造方法可以有多个，名字必须和类名完全一样
	public OOP_01_Car() {
		System.out.println("OOP_01_Car的构造方法-无参数...");
		this.topSpeed = 20;
		this.color = "red";
		this.totalSeat = 2;
	}
	
	// 知识点3.2 如果一个类定义了构造器并且这些构造器都有参数，那么不会生成默认无参构造器
	public OOP_01_Car(int topSpeed, String color, int totalSeat) {
		System.out.println("OOP_01_Car的构造方法-有3个参数...");
		// this.topSpeed = 510;
		// color = "blue";
		// totalSeat = 7;
		
		this.topSpeed = topSpeed;
		this.color = color;
		this.totalSeat = totalSeat;
		
	}
	
	
	// 知识点1.2 行为/方法
	void drive() {
		System.out.println("正在驾驶...");
	}
	
	String display(String content) {
		return content;
	}
	
	public static void main(String[] args) {
		// 创建对象(最常用，最简单): 通过new
		OOP_01_Car car1 = new OOP_01_Car();
		System.out.println("car1的速度：" + car1.topSpeed);
		System.out.println("car1的颜色：" + car1.color);
		System.out.println("car1的座位：" + car1.totalSeat);
		car1.drive();
		System.out.println("car1的文本显示：" + car1.display("我是文本内容"));

		//		System.out.println("innerCar1---------------------");
		//		com.baseLearn.java.oop.InnerCar iCar1 = new com.baseLearn.java.oop.InnerCar();
		//		System.out.println("iCar1的引擎是：" + iCar1.ShowEngine("V8"));
		
		
		System.out.println("--------Car2- 有参数---------------------");
		OOP_01_Car car2 = new OOP_01_Car(800, "green", 12);
		System.out.println("car2的速度：" + car2.topSpeed);
		System.out.println("car2的颜色：" + car2.color);
		System.out.println("car2的座位：" + car2.totalSeat);

		//		System.out.println("innerCar2--------------------");
		//		com.baseLearn.java.oop.InnerCar iCar2 = new com.baseLearn.java.oop.InnerCar();
		//		System.out.println("iCar2的引擎是：" + iCar2.ShowEngine("V10"));
	}
	
}

// 知识点2
// 1个.java文件中可以有很多类，
// 不过 有且仅能有一个public class，并且类名要和文件名相同
class InnerCar {
	String engine;
	
	String ShowEngine(String engineName) {
		return engineName;
	}
}