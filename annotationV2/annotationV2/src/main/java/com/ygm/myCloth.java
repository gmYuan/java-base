package com.ygm;

class Cloth { }

@洗涤手段(value = {"手洗", "水洗"}, 成分 = "纯棉")
public class myCloth extends Cloth{

	private String name;

	public myCloth(String name) {
		this.name = name;
	}

	void foo() {}


	void testDeprecated() throws InstantiationException, IllegalAccessException {
		Cloth.class.newInstance();
	}






}