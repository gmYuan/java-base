package com.reflection;


import com.reflection.obtainClazzIns.Animal;

@Markable("Main bird")
public class Bird extends Animal implements Flying, Comparable<Bird> {

	@Markable("Age of the bird")
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


	private boolean canEat(String food) {
		return true;
	}

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
