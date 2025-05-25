package com.baseLearn.java.oop.polymorphism;

public class ClassCaseDemo {
	public static void main(String[] args) {
		// 向上转型：是自动的
		Shape rect = new Rectangle(10, 20);
		Square  square = new Square(10);
		Shape circle1 = new Circle(10.5);
		
		System.out.println("Rectangle area:" + rect.calcArea());
		System.out.println("Square area:"+ square.calcArea());
		System.out.println("------------------------------------------");
		
		// 向下转型：需要强制声明
		Circle circle2 = (Circle)circle1;
		System.out.println("circle area:"+ circle2.calcArea());
		System.out.println("------------------------------------------");
		
		// 向下转型：非法转型
		Rectangle rect2 = (Rectangle)circle1;
    System.out.println("Rectangle2 area:" + rect2.calcArea());
		
	
	}
}
