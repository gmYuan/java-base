package com.baseLearn.java.oop.inheritance;

public class SUVCar extends Car {
	 int seatCount;
	
	public SUVCar(String name, String color, int year, int fuelCount, int seatCount) {
		super(name, color, year, fuelCount);
		this.seatCount = seatCount;
	}
	
	void showSUVInfo() {
		System.out.println("SUV Car Info:");
		showInfo();
	}
	
	public static void main(String[] args) {
		SUVCar mySUV = new SUVCar("SUV", "红色", 2018, 100, 5);
		mySUV.showSUVInfo();
	}
}
