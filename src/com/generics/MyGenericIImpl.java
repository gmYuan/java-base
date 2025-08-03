package com.generics;

// 泛型能够用在 类、接口、类方法上 传入类型参数
public class MyGenericIImpl implements MyGenericI<String, Integer> {
	@Override
	public Integer getElement(String param) {
		return Integer.parseInt(param);
	}

	public <T> void setConfig(String configName, T config) {
		System.out.println("set config: " + configName + " = " + config);
	}

	public <B, A> B doSomething(A something) {
		System.out.println("do something: " + something);
		return null;
	}

	public static void main(String[] args) {
		// ❌ 错误：MyGenericI<Integer, String> 和 接口定义的 getElement 方法参数类型不一致
    // MyGenericI<Integer, String> myGenericIImpl1 = new MyGenericIImpl();
		// ✅ 正确：
    MyGenericI<String, Integer> myGenericIImpl2 = new MyGenericIImpl();
		System.out.println("get element是: " + myGenericIImpl2.getElement("12367"));

		// ❌ 错误：myGenericIImpl2当前的接口里，未定义doSomething方法
		// myGenericIImpl2.doSomething("hello");

		// ✅ 正确：
		// 在 Java 中，调用泛型方法时，类型参数要放在方法名前面
		MyGenericIImpl myGenericIImpl3 = new MyGenericIImpl();

		Boolean res1 = myGenericIImpl3.<Boolean, String>doSomething("hello");
		System.out.println("doSomething的res1是: " + res1);

		Integer res2 = myGenericIImpl3.<Integer, String>doSomething("world");
		System.out.println("doSomething的res2是: " + res2);

	}


}
