package com.ygm;


public class Cat extends  Animal {
	public String sound = "喵喵～";

	public String getName() {
		return "Cat";
	}

	public String eat() {
		System.out.println("Cat can eat !");
		return "Cat can eat ! ";
	}
	
}