package com.baseLearn.java.oop.polymorphism;

public class Circle extends Shape {
	private double radius;

	
	public Circle(double radius) {
		this.radius = radius;
	}
	
	@Override
	public double calcArea() {
		return this.radius * this.radius * 3.1415;
	}
}
