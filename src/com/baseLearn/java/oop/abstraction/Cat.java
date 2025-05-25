package com.baseLearn.java.oop.abstraction;

public class Cat extends Animal {
	
	@Override
	public void makeSound() {
		System.out.println("Cat Sound: miao~");
	}
	
	@Override
	void move() {
		System.out.println("Cat move: walk");
	}
	
	// 也可重写正常的 实例方法
	@Override
	public String getName() {
		return super.getName() + ": lalala";
	}
	
	public static void main(String[] args) {
		Animal cat = new Cat();
		cat.move();
		cat.makeSound();
		System.out.println(cat.getName());
	}
}
