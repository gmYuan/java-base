package com.ygm;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

public class Main {

	// AOP模式方法2- CGLIB/ByteBuddy 字节码生成
	// 其 本质原理是 class DataServiceImpl$$EnhancerByCGLIB extends DataServiceImpl {}
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(DataServiceImpl.class);
		enhancer.setCallback(new LogInterceptor());

		DataServiceImpl enhanceService = (DataServiceImpl) enhancer.create();
		enhanceService.a(1);
		enhanceService.b(2);

	}



	/**
	// AOP模式方法1- JDK 动态代理
	static DataService service = new DataServiceImpl();

	public static void main(String[] args) {
		 DataService proxyService = (DataService) Proxy.newProxyInstance(
				service.getClass().getClassLoader(),
				new Class[]{DataService.class},
				new LogProxy(service)
		);
		 proxyService.a(1);
		 proxyService.b(2);
	}
	 */



	/** Decorator模式
	static DataService service = new LogDecorator(
			new DataServiceImpl()
	);

	public static void main(String[] args) {
		System.out.println(service.a(1));
		System.out.println(service.a(2));
	}
	 */
}