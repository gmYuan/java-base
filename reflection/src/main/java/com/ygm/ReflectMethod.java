package com.ygm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectMethod extends  Cat {
	public String eat() {
		return "WhiteCat eat ";
	}
	
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
		String inputClazz = args[0];
		String inputFiled = args[1];
		Class<Cat> aClass = (Class<Cat>) Class.forName(inputClazz);
		System.out.println("clazz是" + aClass);
		Cat catIns = aClass.newInstance();
		// 调用 aClass 的 eat 方法
		Method method = aClass.getMethod("eat");
		method.invoke(catIns);
		// 调用 aClass 的 sound 属性
		System.out.println(aClass.getField(inputFiled).get(catIns));

	}
	
}