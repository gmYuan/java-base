package com.baseLearn.java.oop.abstraction;

// 接口代表某种能力，某种功能行为
public interface InterfaceDemo {
	String NAME = "interfaceDemo";
	// 其实相当于是 public static final NAME : 静态常量
	public static final String NAME2 = "name2";
	
	public void showInfo();
	// 其实相当于是 public abstract void showInfo： 抽象方法
	public abstract void showInfo2();
	
}

interface InterfaceDemo2 {

}