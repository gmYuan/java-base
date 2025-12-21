package com.reflection.invoke;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstrInvoke {
	public static void main(String[] args) {
		// 动态调用 私有构造器, 来创建一个对象
		try {
			// S1 获取目标类的 class对象
			Class<MyPerson> clazz = MyPerson.class;

			// S2 获取目标构造器的 Constructor对象
			Constructor<MyPerson> constructor = clazz.getDeclaredConstructor(String.class, int.class);

			// S3 设置构造器的 可访问性（只有是 private类型构造器才需要）
			constructor.setAccessible(true);

			// S4 调用Constructor对象的 newInstance方法，传入参数==> 创建实例对象
			MyPerson myPersonObj = constructor.newInstance("张三", 100);

			// S5 打印验证结果
			System.out.println("myPersonObj2 反射设置后的值： " + myPersonObj.toString());

		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}

	}
}
