package com.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectInActField {

	public static void main(String[] args) {
		// 4 获取类的 属性
		Class<Bird> birdClazz = Bird.class;

		// 4.1 获取自身类的 public属性 + 父类的 public属性
		System.out.println("--------  获取public属性 ---------- " );
		Field[] pubFields = birdClazz.getFields();
		for (Field f : pubFields) {
			System.out.println("public field是--- " + f);
			// 获取属性的 变量名称
			System.out.println("public field的名称是--- " + f.getName());
			// 获取属性的 访问修饰符
			System.out.println("public field的访问修饰符是--- " + Modifier.toString(f.getModifiers()));
			// 获取属性的 数据类型
			System.out.println("public field的数据类型是--- " + f.getType());
		}

		// 4.2 获取自身类的所有属性（包含private、protected、default、public）+ 不包含父类的属性
		System.out.println("----------------  获取所有属性 ------------------ " );
		Field[] allFields = birdClazz.getDeclaredFields();
		for (Field f : allFields) {
			System.out.println("all field是--- " + f);
			// 获取属性的 变量名称
			System.out.println("all field的名称是--- " + f.getName());
			// 获取属性的 访问修饰符
			System.out.println("all field的访问修饰符是--- " + Modifier.toString(f.getModifiers()));
			// 获取属性的 数据类型
			System.out.println("all field的数据类型是--- " + f.getType());
		}

		// 4.3 获取单个属性
		System.out.println("----------------  获取单个属性 ------------------ " );
		try {
			Field canEatField = birdClazz.getField("canEat");
			Field ageField = birdClazz.getDeclaredField("age");

			System.out.println("canEat field是--- " + canEatField);
			System.out.println("age field是--- " + ageField);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}



	}
}
