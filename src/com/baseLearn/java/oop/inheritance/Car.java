package com.baseLearn.java.oop.inheritance;

import java.util.Objects;

public class Car extends Vehicle {
	
	 int fuelCount;
	
	public Car(String name, String color, int year, int fuelCount) {
		super(name, color, year);
		this.fuelCount = fuelCount;
		System.out.println("我是 Car 的构造函数 被执行了");
		System.out.println("----------------------------");
	}
	
	void showCarInfo() {
		System.out.println("执行的是Car的打印方法，它的name是：" + super.getName());
		showInfo();
		System.out.println("fuelCount:" + fuelCount);
	}
	
	@Override
	Car getInstance() {
		return new Car("奔驰", "蓝色", 2022, 200);
	}
	
	@Override
	public String toString() {
		return "Car{" +
				"fuelCount=" + fuelCount +
				"} " + super.toString();
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Car)) return false;
		if (!super.equals(o)) return false;
		Car car = (Car) o;
		return fuelCount == car.fuelCount;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), fuelCount);
	}
	
	public static void main(String[] args) {
		Car c1 = new Car("保时捷", "黑色", 2019, 100);
//		System.out.println("c1的颜色是" + c1.color);
//		c1.showInfo();
//		c1.showCarInfo();

// ------------------------------------------
//		boolean r1 = c1 instanceof Car;
//		boolean r2 = c1 instanceof Vehicle;

// ------------------------------------------
//		Car c2 = c1.getInstance();
//		boolean r3 = c2.equals(c1);
//		System.out.println("r3是" +  r3);
	
	  System.out.println(c1);
	}
	
	
}
