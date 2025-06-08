package com.baseLearn.java.oop.abstraction;

public abstract class Bird implements Flyable, CanSleep {
	public Bird() {}
	
	public Bird(String name) {}
	
	
	
	public void eat() {
		System.out.println("Bird is eating");
	}
	
	public void WrapFly() {
		System.out.println("--------------------");
		System.out.println("这个是Bird的 WrapFly");
		fly();
	}
}
