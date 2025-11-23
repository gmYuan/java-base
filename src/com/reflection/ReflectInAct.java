package com.reflection;

import java.lang.reflect.Constructor;

public class ReflectInAct {

	public static void main(String[] args) {
		// 1 获取类名
		Class<Bird> birdClazz = Bird.class;
		System.out.println("Class name是-- " + birdClazz.getName());
		System.out.println("Simple name是-- " + birdClazz.getSimpleName());

		// 2 获取包名
		System.out.println("Package name是-- " + birdClazz.getPackage());

		// 3 获取构造器
		// 3.1 获取public构造器
		System.out.println("--------  获取public构造器 ---------- " );
		Constructor<?>[] pubConstructors = birdClazz.getConstructors();
		for (Constructor<?> c : pubConstructors) {
			System.out.println("public constructor = " + c);
		}

		// 3.2 获取所有构造器
		System.out.println("--------  获取所有构造器 ---------- " );
		Constructor<?>[] allConstructors = birdClazz.getDeclaredConstructors();
		for (Constructor<?> c : allConstructors) {
			System.out.println("all constructor = " + c);
		}


	}
}
