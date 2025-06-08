package com.baseLearn.java.oop.abstraction;

public class Airplane implements Flyable {
	
	@Override
	public void fly() {
	
	}
	
	
	// 这样写也是可以的，即 接口的默认方法可以被类重写
	//	@Override
	//	public void printName() {
	//		Flyable.super.printName();
	//	}
	
	public static void main(String[] args) {
		Airplane airplane1 = new Airplane();
		airplane1.printName();
		Flyable.prepare();
	}
}
