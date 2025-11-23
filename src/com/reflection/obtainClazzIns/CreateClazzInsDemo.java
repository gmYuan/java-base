package com.reflection.obtainClazzIns;

import java.util.Random;

public class CreateClazzInsDemo {
	static public Object getIns() {
		Random random = new Random();
		// 生成一个0到2之间的随机整数: 0, 1, 2
		int seed = random.nextInt(3);
		String className = "";
		switch (seed) {
		case 0:
			className = "java.lang.String";
			break;
		case 1:
			className = "com.reflection.obtainClazzIns.Animal";
			break;
		case 2:
			className = "java.util.ArrayList";
			break;
		default:
			System.out.println("seed=" + seed);
		}
		try {
			Class<?> clazz = Class.forName(className);
			return clazz.newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Failed to create instance--", e);
		}
	}


	public static void main(String[] args) {
		// 举例1： 动态创建 实例对象

		// S1 获取Animal类的 Class对象
		Class<Animal> animalClazz = Animal.class;
		// S2 使用 Class对象 创建 实例对象
		// 前提条件1：Animal类必须有一个 无参构造器
		// 前提条件2：无参构造器 必须是可访问的（public/ 同包下的default）
		try {
			Animal animal = animalClazz.newInstance();
			System.out.println("aa--" + animal);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Failed to create instance of Animal", e);
		}

		// 举例2：
		System.out.println("------ 举例2： 动态创建 实例对象 ------");
		for (int i = 0; i < 10; i++) {
			Object ins = getIns();
			System.out.println("ins是--" + ins);
		}
	}
}
