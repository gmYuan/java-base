package com.ygm;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		System.out.println(method.getName() + "is startV2 with: " + Arrays.toString(objects));
		// invokeSuper：走父类真实实现，不再进 intercept；method.invoke(o,…) 会再次进拦截器导致死循环
		Object retVal = methodProxy.invokeSuper(o, objects);
		System.out.println(method.getName() + "is finishedV2 with: " + retVal);
		return retVal;
	}

}
