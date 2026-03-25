package com.ygm;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LogProxy implements InvocationHandler {
	DataService delegate;

	public LogProxy(DataService delegate) {
		this.delegate = delegate;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(method.getName() + "is start with: " + Arrays.toString(args));
		Object retVal = method.invoke(delegate, args);
		System.out.println(method.getName() + "is finished with: " + retVal);
		return retVal;
	}
}