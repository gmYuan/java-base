package com.baseLearn.java.oop.abstraction;

public class XiaoNiao extends Bird  {
	
	@Override
	public void fly() {
		System.out.println("小鸟正在飞...");
	}


	@Override
	public void sleep() {
		System.out.println("小鸟正在睡觉...");
	}

	public static void main(String[] args) {
		XiaoNiao xiaoNiao = new XiaoNiao();
		xiaoNiao.fly();
		xiaoNiao.sleep();
		xiaoNiao.WrapFly();
	}
	

}
