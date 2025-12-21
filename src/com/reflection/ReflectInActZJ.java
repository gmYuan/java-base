package com.reflection;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectInActZJ {

	public static void main(String[] args) {
		// 6 获取类的 注解
		Class<Bird> birdClazz = Bird.class;

		// 6.1 获取类的 特点类型(如Markable类型)的 注解
		System.out.println("-------------------- 6.1 ------------------------");
		Markable markableAnn1 = birdClazz.getAnnotation(Markable.class);
		System.out.println("markableAnn1 是： " + markableAnn1);
		System.out.println("Bird类上 的Markable类型 的注解 value()是： " + markableAnn1.value());

		// 6.2 获取该类 所有 方法（包含private）
		System.out.println("-------------------- 6.2 ------------------------");

		Method[] allMethods = birdClazz.getDeclaredMethods();
		for (Method method : allMethods) {
			// 6.1 获取方法的 注解
			Annotation[] annotations = method.getAnnotations();
			for (Annotation ann2 : annotations) {
				if (ann2 instanceof Markable) {
					Markable markableAnnotation = (Markable) ann2;
					System.out.println("方法上 Markable类型的注解 value()是： " + markableAnnotation.value());
				}
			}
		}

		// 6.3 获取该类 age属性上 Markable类型 的注解
		System.out.println("-------------------- 6.3 ------------------------");
		try {
			Field ageField = birdClazz.getDeclaredField("age");
			Markable markableAnn3 = ageField.getAnnotation(Markable.class);
			System.out.println("ageField 是： " + ageField);
			System.out.println("ageField上 Markable类型的注解 value()是： " + markableAnn3.value());
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}

		// 6.4 获取 原生注解上的 元注解信息
		System.out.println("-------------------- 6.4 ------------------------");
		Class<Target> targetClazz = Target.class;
		Annotation[] targetAnns = targetClazz.getAnnotations();
		for (Annotation ann : targetAnns) {
			System.out.println("Target 上的 元注解 是： " + ann);
			if (ann instanceof Target) {
				Target targetAnn = (Target) ann;
				System.out.println("Target 上的 value() 是： " + Arrays.toString(targetAnn.value()));
			}
		}

	}

}
