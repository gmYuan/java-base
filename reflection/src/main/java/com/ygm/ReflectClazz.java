package com.ygm;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;

public class ReflectClazz extends  Cat {
	public String eat() {
		return "WhiteCat eat ";
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
		String inputClazz = args[0];
		Class<?> aClass = Class.forName(inputClazz);
		System.out.println("clazz是" + aClass);
		// 反射得到的是 Object，需要强转为 List<Integer> 才能按 List 使用
		List<Integer> ins = (List<Integer>) aClass.getConstructor().newInstance();
		ins.add(1);
		System.out.println("ins是：" + ins);
	}
	
}