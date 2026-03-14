package com.ygm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErasureProblem1 {
	static class Animal{};

	static class Cat extends Animal{};

	public static void foo1(Animal animal) {};

	//public static void foo2(List animals) {};
	public static void foo2(List<Animal> animals) {};

  // 这 2 个的区别是：在 java里数组是 真泛型; 列表是假泛型(存在类型擦除)
	public static void testArray(Animal[] animals) {};
	public static void testList(ArrayList<Animal> animals) {};


	// 3.1 由于存在真泛型，所以在执行阶段还是会报错的
	public static void testArraySafety(Object[] array) {
		array[0] = 1;
	}
	// 3.2 由于不是真泛型，所以在通过类型强转 绕过编译报错后，在执行阶段是不会报错的
	public static void testListSafety(List<Object> list) {
		list.add(1);
	}

	public static void main(String[] args) {
    // 3.1 由于存在真泛型，所以在执行阶段还是会报错的，因为类型不一致
		String[] stringArray = new String[2];
		testArraySafety(stringArray);
		// 3.2 由于不是真泛型，所以在通过类型强转 绕过编译报错后
		// 在执行阶段是不会报错的，因为类型被擦除了
		List<String> StringList = new ArrayList<>();
		testListSafety((List) StringList);




		// 1 这样是可行的，因为Cat是 Animal 的子类
		foo1(new Cat());

		// 2 比较 数组和列表的 真假泛型表现
		// 2.1 数组在运行阶段可以识别出 Cat数组是 Animal数组的子类，所以编译阶段不报错
		testArray(new Cat[2]);

		// 2.2 列表由于存在擦除，在运行阶段无法识别父子类关系，所以在编辑阶段就会更严格报错
		// testList(new ArrayList<Cat>());

		// 2.3 但是我们可以通过 一个原始类型的中间变量，来绕过编译器的检查，照样可以正常执行
		ArrayList<Animal> ani1 = new ArrayList<>();
		ArrayList temp = ani1;
		temp.add(1);
		testList(temp);






	}
}