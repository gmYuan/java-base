package com.reflection.invoke;

import java.lang.reflect.Field;

public class FieldInvoke {
	public static void main(String[] args) {
		// 获取私有属性age, 把它的值设置为100
		try {
			// S1 获取目标类的 class对象
			MyPerson myPersonObj1 = new MyPerson();
			System.out.println("myPersonObj1 初始值： " + myPersonObj1.toString());
			Class<?> clazz = myPersonObj1.getClass();

			// S2 获取目标属性的 Field对象
			Field ageField = clazz.getDeclaredField("age");
			Field nameField = clazz.getField("name");

			// S3 设置属性的 可访问性（只有是 private类型属性才需要）
			ageField.setAccessible(true);

			// S4 设置属性的值
			ageField.set(myPersonObj1, 100);
			nameField.set(myPersonObj1, "张三");

			// S5 打印验证结果
			System.out.println("myPersonObj1 反射设置后的值： " + myPersonObj1.toString());
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
