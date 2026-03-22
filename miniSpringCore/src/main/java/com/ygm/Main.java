package com.ygm;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {
		BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:config.xml");

		OrderService orderService = (OrderService) beanFactory.getBean("orderService");
		OrderDao orderDao = (OrderDao) beanFactory.getBean("orderDao");

		System.out.println(orderService);
		System.out.println(orderDao);

		orderService.doSomething();



	}
}