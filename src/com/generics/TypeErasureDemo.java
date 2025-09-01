package com.generics;

import java.util.ArrayList;
import java.util.List;

public class TypeErasureDemo<T> {

	// List list
	public void transform(List<String> list ) {}

	// List list
  // public Integer transform(List<Integer> list) {}



	public static void main(String[] args) {
		// 类型擦除 type erasure：改变javac编译器，不需要改字节码，也不需要改虚拟机
		ArrayList list1 = new ArrayList();

		// ArrayList<String> ==> ArrayList 裸类型(raw type)
		ArrayList<String> list2 = new ArrayList<>();

		// ArrayList<Integer> ==> ArrayList
		ArrayList<Integer> list3 = new ArrayList<>();
		list3.add(3);

		list1 = list3;
		list1.add("哈哈哈哈");
		list1.add(true);


	}

}
