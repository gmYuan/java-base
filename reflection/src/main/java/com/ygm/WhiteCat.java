package com.ygm;


public class WhiteCat extends  Cat {
	public String eat() {
		return "WhiteCat eat ";
	}

	public static void main(String[] args) {
		WhiteCat cat = new WhiteCat();
		System.out.println(cat.eat());

		// 通过 RPC，还能获取到 调用其他非本地类
		// RPC rpc = new RPC();
		// Item item = rpc.getItemById("331");
		// System.out.println(item.getId());
		// 通过 obj.class，能获取到该实例对象的 实现类
		// System.out.println(item.getClass()); // class com.ygm.Goods


	}
	
}