package com.reflection.obtainClazzIns;

public class ObtainClazzInsDemo {
	public static void main(String[] args) {
		// 以 String 类为例

		// 方法1： 通过类的实例调用 getClass() 方法
		System.out.println("----------getClass()--------------");
		Integer int1 = 100;
		Class<? extends Integer> integerClazz1 = int1.getClass();

		Animal animal1 = new Animal();
		Class<? extends Animal> animalClazz1 = animal1.getClass();

		System.out.println("通过类的实例调用 getClass() 方法获取 IntegerClazz1 对象--" + integerClazz1);
		System.out.println("通过类的实例调用 getClass() 方法获取 AnimalClazz1 对象--" + animalClazz1);

		// 方法2： 通过类名.class 获取 Class 对象
		System.out.println("----------类名.class --------------");

		Class<String> stringClazz2 = String.class;
		Class<Animal> animalClazz2 = Animal.class;

		System.out.println("通过类名.class 获取 StringClazz2 对象--" + stringClazz2);
		System.out.println("通过类名.class 获取 AnimalClazz2 对象--" + animalClazz2);

		// 方法3： 通过 Class.forName() 方法获取 Class 对象
		System.out.println("---------Class.forName()---------------");
		try {
			Class<?> stringClazz3 = Class.forName("java.lang.String");
			Class<?> animalClazz3 = Class.forName("com.reflection.obtainClazzIns.Animal");
			System.out.println("通过 Class.forName() 方法获取 StringClazz3 对象--" + stringClazz3);
			System.out.println("通过 Class.forName() 方法获取 AnimalClazz3 对象--" + animalClazz3);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		// 了解即可 方法4： 通过类加载器加载类
		System.out.println("-----------classLoader-------------");
		ClassLoader classLoader = ObtainClazzInsDemo.class.getClassLoader();
		try {
			Class<?> stringClazz4 = classLoader.loadClass("java.lang.String");
			Class<?> animalClazz4 = classLoader.loadClass("com.reflection.obtainClazzIns.Animal");
			System.out.println("通过类加载器加载类获取 StringClazz4 对象--" + stringClazz4);
			System.out.println("通过类加载器加载类获取 AnimalClazz4 对象--" + animalClazz4);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		// 方法5： 特殊获取 Class 对象，只能用于基本类型的包装类
		System.out.println("----------基本类型的包装类--------------");
		Class<Integer> integerClazz5 = Integer.TYPE;
		Class<Boolean> booleanClazz5 = Boolean.TYPE;
		System.out.println("通过 Integer.TYPE 获取 IntegerClazz5 对象--" + integerClazz5);
		System.out.println("通过 Boolean.TYPE 获取 BooleanClazz5 对象--" + booleanClazz5);


		// 知识点6： 一个类在 JVM 中只有一个 Clazz 对象
		System.out.println("------------equals------------");
		Integer a = 2;
		String b = "abg";
		Integer c = 455;
		Class<?> aClazz = a.getClass();
		Class<?> bClazz = b.getClass();
		Class<?> cClazz = c.getClass();

		// always false
		System.out.println(aClazz == bClazz);
		System.out.println(aClazz.equals(bClazz));

		// always true
		System.out.println(aClazz == cClazz);
		System.out.println(aClazz.equals(cClazz));




	}
}
