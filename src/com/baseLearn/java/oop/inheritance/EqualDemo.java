package com.baseLearn.java.oop.inheritance;

public class EqualDemo  {
	public static void main(String[] args) {
		Car car1 = new Car("a", "red", 1222, 23);
		Car car2 = new Car("a", "red", 2222, 23);
		boolean r3 =  car1.equals(car2);
		System.out.println("r3是" + r3);
	}
	
}
