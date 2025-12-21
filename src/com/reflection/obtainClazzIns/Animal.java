package com.reflection.obtainClazzIns;

import com.reflection.Markable;

public class Animal {

	public String name;

	// 定义一个无参构造方法
	public Animal() {
		System.out.println("Animal内部-- Animal is created");
	}

	public Animal(String name) {
		this.name = name;
	}

	@Markable("我是Animal类的 makeSound method的 注解")
	public void makeSound() {
		System.out.println("Animal内部-- Animal is making sound");
	}
}
