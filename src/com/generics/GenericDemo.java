package com.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericDemo<T> {
	// 类型擦除2： 无法创建泛型数组
	// T[] items = new T[100];

	// 类型擦除3： 无法创建泛型对象
	// T t2 = new T();

	public void doSomething(Object element) {
		// 类型擦除1： 无法对泛型进行实例判断
//		if (element instanceof T) {
//			System.out.println("element is instanceof T");
//		}
	}


	public static void main(String[] args) {
		// 类型擦除 type erasure
		//   改变javac编译器，不需要改字节码，也不需要改虚拟机
		List list1 = new ArrayList();
		List<String> list2 = new ArrayList<>();


	}

}
