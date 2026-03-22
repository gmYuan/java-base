package com.ygm;


import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyIoCContainer {
	public static void main(String[] args) throws IOException {
		// 加载Bean的定义
		Properties properties = new Properties();
		properties.load(MyIoCContainer.class.getResourceAsStream("/ioc.properties"));
		// System.out.println(properties);
     // 实例例化Bean
		 Map<String, Object> beans = new HashMap<>();
		 properties.forEach((beanName, beanClass) -> {
			 try {
				 Class<?> klass = Class.forName((String) beanClass);
				 Object beanIns = klass.getConstructor().newInstance();
				 beans.put((String) beanName, beanIns);
			 } catch (Exception e) {
				 throw new RuntimeException(e);
			 }
		 });
		// 查找依赖，实现依赖注⼊
		beans.forEach((beanName, beanIns) -> depInject(beanName, beanIns, beans));

		// 测试是使用 beans
		OrderDao orderDao = (OrderDao) beans.get("orderDao");
		OrderService orderService = (OrderService) beans.get("orderService");
		System.out.println(orderDao);
		System.out.println(orderService);
		orderService.doSomething();
	}

	private static void depInject(String beanName, Object beanIns, Map<String, Object> beans) {
		List<Field> fieldsWithAutoWired = Stream.of(beanIns.getClass().getDeclaredFields())
				.filter(field -> field.getAnnotation(Autowired.class) != null)
				.collect(Collectors.toList());
		fieldsWithAutoWired.forEach(field -> {
			Object filedDepIns = beans.get(field.getName());
			field.setAccessible(true);
			try {
				field.set(beanIns, filedDepIns);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		});
	}
}