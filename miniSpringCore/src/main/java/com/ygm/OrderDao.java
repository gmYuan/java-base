package com.ygm;


import org.springframework.beans.factory.annotation.Autowired;

public class OrderDao {

	@Autowired
	private OrderService OrderService;

	public  void select() {
		System.out.println("OrderDao is selecting!");
	}
}