package com.baseLearn.java.oop.abstraction;

public abstract class Animal {
	// 1 abstract前 不能用final,static, private，因为它 需要被继承，从而具体实现
	// private final/static abstract void makeSound();
	public abstract void makeSound();
	
	abstract void  move();
	
	
	// 2 抽象类 可以定义 类的静态方法
	private static void walk() {
		System.out.println("Animate walking");
	}
	
	public String getName() {
		return getClass().getName();
	}

	
	public static void main(String[] args) {
		// Animal animal = new Animal();
		Animal.walk();
	}

}
