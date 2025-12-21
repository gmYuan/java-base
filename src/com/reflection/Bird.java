package com.reflection;


import com.reflection.obtainClazzIns.Animal;

import java.io.IOException;

@Markable("Main bird")
public class Bird extends Animal implements Flying, Comparable<Bird> {

	@Markable("我是Bird类上 private属性age的 注解")
	private int age;

	protected int size;

	public boolean canEat;

	// Constructors
	private Bird(int age) {
		this.age = age;
	}

	protected Bird(String name, int age, int size) {
		super(name);
		this.age = age;
		this.size = size;
	}

	Bird(String name, int age) {
		super(name);
		this.age = age;
	}

	public Bird() {
		this.age = 0;
	}

	public Bird(String name, int age, boolean canEat) {
		super(name);
		this.age = age;
		this.canEat = canEat;
	}

	@Markable("我是Bird类 private方法canEat的 注解")
	private boolean canEat(String food) throws IOException, NoSuchMethodException {
		if (food == null) {
			throw new IOException("food is null");
		}
		if (food.equals("wu")) {
			throw new NoSuchMethodException("Bird can not eat wu");
		}

		return true;

	}

	@Markable("我是Bird类 public方法walk的 注解")
	public void walk() {
		System.out.println("Bird is walking");
	}


	@Override
	public void fly() {
		System.out.println("Bird is flying");
	}

	@Override
	public int compareTo(Bird o) {
		return this.age - o.age;
	}
}
