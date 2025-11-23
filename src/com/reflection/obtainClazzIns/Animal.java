package com.reflection.obtainClazzIns;

public class Animal {

	public String name;

	// 定义一个无参构造方法
	public Animal() {
		System.out.println("Animal内部-- Animal is created");
	}

	public Animal(String name) {
		this.name = name;
	}

	public void makeSound() {
		System.out.println("Animal内部-- Animal is making sound");
	}
}
