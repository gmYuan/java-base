package com.baseLearn.java.oop.polymorphism;

public class Main  {
	public static void main(String[] args) {
		// 方法1
		Shape rect = new Rectangle(10, 20);
		Square  square = new Square(10);
		Circle circle = new Circle(10.5);
		
		System.out.println("Rectangle area:" + rect.calcArea());
		System.out.println("Square area:"+ square.calcArea());
		System.out.println("circle area:"+ circle.calcArea());
		System.out.println("------------------------------------------");
		
		// 方法2: 使用多态
		Shape[] shapes = new Shape[3];
		shapes[0] = new Rectangle(10, 20);
		shapes[1] = new Square(10);
		shapes[2] = new Circle(10.5);
		for (Shape item : shapes) {
			System.out.println("shape area:" + item.calcArea());
		}
		
	
	}
}
