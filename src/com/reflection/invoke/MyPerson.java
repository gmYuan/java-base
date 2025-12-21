package com.reflection.invoke;

public class MyPerson {
	public String name;
	private int age;

	public MyPerson() {

	}

	private MyPerson(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}


	@Override
	public String toString() {
		return "MyPerson{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
