package com.baseLearn.java.oop;

public class VariableScopeDemo {
	// 1 类变量的作用域: 整个类到处都能用，只有一个，类加载的时候
	static int num = 10;
	
	// 2 实例变量的作用域: 类里面都能用(除static context外)，但是实例没有了就不行了
	private String name = "n1";
	
	private static void showInfo(){
		System.out.println(num);
		// 无法从 static 上下文引用非 static 字段 'name'
		// System.out.println(name);
	}
	
	public static void printCount() {
		// 3 局部变量的作用域:就是花括号内
		{
			int b = 30;
			// 无法解析符号 'i'
			// System.out.println("i = " + i);
		}
		// 无法解析符号 'b'
		// System.out.println("b = " + b);
		
		System.out.println("----------------------------------------------------");
		int i = 0;
		while(i < 10) {
			i++;
		}
		System.out.println("i = " + i);
	
		
	}
	
	protected void showName() {
		System.out.println(num);
		System.out.println(name);
	}
	
	public static void main(String[] args) {
		VariableScopeDemo.printCount();
	}
	
	
}
