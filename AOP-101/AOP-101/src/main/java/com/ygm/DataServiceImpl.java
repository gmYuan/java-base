package com.ygm;


import java.util.UUID;

public class DataServiceImpl implements DataService {

	@Override
	public String a(int i) {
		return UUID.randomUUID().toString();
	}

	@Override
	public String b(int i) {
		return UUID.randomUUID().toString();
	}
}