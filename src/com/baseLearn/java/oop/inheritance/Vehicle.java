package com.baseLearn.java.oop.inheritance;

import java.util.Objects;

public class Vehicle {
	public String getName() {
		return name;
	}
	
	private String name;
	 private String color;
	 private int year;
	
	public Vehicle(String name, String color, int year) {
		System.out.println("我是 Vehicle 的构造函数 被执行了");
		this.name = name;
		this.color = color;
		this.year = year;
		System.out.println("----------------------------");
	}
	
	public Vehicle() {
	}
	
	void showInfo() {
		System.out.println("Vehicle name:" + this.name);
		System.out.println("Vehicle color:" + this.color);
		System.out.println("Vehicle year:" + this.year);
		System.out.println("----------------------------");
	}
	
	//	final Vehicle getInstance() {
	//		return new Vehicle();
	//	}
	
	Vehicle getInstance() {
		return new Vehicle();
	}
	
	@Override
	public String toString() {
		return "Vehicle{" +
				"name='" + name + '\'' +
				", color='" + color + '\'' +
				", year=" + year +
				'}';
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Vehicle)) return false;
		Vehicle vehicle = (Vehicle) o;
		return year == vehicle.year && Objects.equals(name, vehicle.name) && Objects.equals(color, vehicle.color);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, color, year);
	}
}
