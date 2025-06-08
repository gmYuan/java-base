package com.baseLearn.java.oop.abstraction;

// 接口代表某种能力，某种功能行为
public interface Flyable {
	String NAME = "接口名称是：Flyable";
	
	void fly();
	
	// 默认方法
	default void printName() {
		System.out.println("我是一个默认方法，我要飞的更高！" + NAME);
	}
	
	// 在接口中的静态方法 不能被实现 类override
	static void prepare() {
		System.out.println("正在准备中...芜湖！");
	}
	
	
}

interface CanSleep {
	void sleep();
}


@FunctionalInterface
interface TestI {
	void test();
}