package com.baseLearn.java.oop.polymorphism;

public class Square extends Shape {
	private double width;

	
	public Square( double width) {
		this.width = width;
	}
	
	@Override
	public double calcArea() {
		return this.width * this.width;
	}
}
