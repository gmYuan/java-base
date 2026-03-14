package com.ygm;


public class Goods implements  Item {
	private String id;
	private String name;

	public Goods(String id) {
		this.id = id;
	}

	public Goods(String id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

}