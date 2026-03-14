package com.ygm;


public class RPC {

	Item getItemById(String id) {
		return new Goods(id);
	}


}