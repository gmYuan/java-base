package com.ygm;

public class LogDecorator implements DataService {
	DataService delegate;

	public LogDecorator(DataService delegate) {
		this.delegate = delegate;
	}

	@Override
	public String a(int i) {
		System.out.println("---- LogDecorator.a() started");
		String val = delegate.a(i);
		System.out.println("---- LogDecorator.a() end " + val);
		return val;
	}

	@Override
	public String b(int i) {
		System.out.println("---- LogDecorator.b() started");
		String val = delegate.b(i);
		System.out.println("---- LogDecorator.b() end " + val);
		return val;
	}
}