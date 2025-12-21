package com.reflection.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoke {
	public static void main(String[] args) {
		//1 动态调用：无参数无返回值的 public方法- doNothing
		try {
			// S1 获取目标类的 class对象
			MyClass myClassObj = new MyClass(10);
			Class<?> clazz = myClassObj.getClass();

			// S2 获取目标方法的 Method对象
			Method doNothingMethod = clazz.getMethod("doNothing");

			// S3 调用Method对象的 invoke方法，传入目标对象
			Object result = doNothingMethod.invoke(myClassObj);
			System.out.println("doNothingMethod.invoke(myClassObj) 的返回值是： " + result);

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}


		//2 动态调用：有参数有返回值的 public方法- calSum
		System.out.println("------------- calSum ------------------");
		try {
			// S1 获取目标类的 class对象
			MyClass myClassObj2 = new MyClass(10);
			Class<?> clazz = myClassObj2.getClass();

			// S2 获取目标方法的 Method对象
			Method calSumMethod = clazz.getMethod("calSum", int.class, String.class);

			// S3 调用Method对象的 invoke方法，传入目标对象和参数
			Object result = calSumMethod.invoke(myClassObj2, 10, "20");
			System.out.println("calSumMethod.invoke(myClassObj2, 10, \"20\") 的返回值是： " + result);

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}


		//3 动态调用：有参数有返回值的 private方法- calSum
		System.out.println("------------- 3 private calSum ------------------");
		try {
			// S1 获取目标类的 class对象
			MyClass myClassObj3 = new MyClass(100);
			Class<?> clazz = myClassObj3.getClass();

			// S2 获取目标方法的 Method对象
			Method calSumMethod = clazz.getDeclaredMethod("calSum", String.class, String.class);

			// S3 设置方法的 可访问性（只有是 private类型方法才需要）
			calSumMethod.setAccessible(true);

			// S4 调用Method对象的 invoke方法，传入目标对象和参数
			Object result = calSumMethod.invoke(myClassObj3, "10", "20");
			System.out.println("私有 calSumMethod.invoke(myClassObj3, \"10\", \"20\") 的返回值是： " + result);

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}


		//4 动态调用：有参数无返回值的 private静态方法- calSum
		System.out.println("------------- 4 private calSum 静态方法 ------------------");
		try {
			// S1 获取目标类的 class对象
			MyClass myClassObj4 = new MyClass(400);
			Class<?> clazz = myClassObj4.getClass();

			// S2 获取目标方法的 Method对象
			Method calSumMethod = clazz.getDeclaredMethod("calSum", String.class);

			// S3 设置方法的 可访问性（只有是 private类型方法才需要）
			calSumMethod.setAccessible(true);

			// S4 调用Method对象的 invoke方法，传入目标对象和参数
			// 这里传入的 obj 参数是 null，因为 calSum 是静态方法，不依赖于具体的实例对象
			Object result = calSumMethod.invoke(null, "15");
			System.out.println("私有 calSumMethod.invoke(null, \"15\") 的返回值是： " + result);

		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}


	}
}
