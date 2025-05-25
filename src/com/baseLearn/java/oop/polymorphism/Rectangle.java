package com.baseLearn.java.oop.polymorphism;

public class Rectangle extends Shape {
	private double width;
	private double height;
	
	public Rectangle(double height, double width) {
		this.height = height;
		this.width = width;
	}
	
	@Override
	public double calcArea() {
		return this.width * this.height;
	}
}
