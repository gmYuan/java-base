package com.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ReflectInActMethod {

	public static void main(String[] args) {
		// 5 获取类的 方法
		Class<Bird> birdClazz = Bird.class;

		// 5.1 获取该类 + 及其父类的 所有public 方法
		Method[] methods = birdClazz.getMethods();
		for (Method method : methods) {
			System.out.println("-------------新的 public method-----------------");
			System.out.println("public method: " + method);
			// 5.1.2 获取方法的 注解
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.println("marked with: " + annotation);
			}

			// 5.1.3 获取方法的 方法名称、返回类型等
			System.out.println("method name: " + method.getName());
			System.out.println("return type: " + method.getReturnType());

			// 5.1.4 获取方法的 参数
			System.out.println("parameters是 " + Arrays.toString(method.getParameters()));

			// 5.1.5 获取方法的 修饰符
			System.out.println("modifiers是 " + Modifier.toString(method.getModifiers()));

			// 5.1.6 获取方法的 异常
			Class<?>[] exceptions = method.getExceptionTypes();
			for (Class<?> exception : exceptions) {
				System.out.println("方法有throws 的是--" + exception);
			}
		}

		// 5.2 获取该类 所有 方法（包含private）
		System.out.println("-------------------- 5.2 ------------------------");
		Method[] allMethods = birdClazz.getDeclaredMethods();
		for (Method method : allMethods) {
			System.out.println("-------------------- 只是该类的所有 methods ------------------------");
			System.out.println("method: " + method);
			// 5.2.1 获取方法的 注解
			Annotation[] annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.println("注解是: " + annotation);
			}
		}
	}



}
