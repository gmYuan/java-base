package com.ygm;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SoluByJUC {

	//c1: JUC-1 ConcurrentHashMap
  private static final Map<Integer, Integer> map = new ConcurrentHashMap<>();

	// 2: JUC-2 AutomicXXX
	private static int a = 0;
	private static final AtomicInteger b = new AtomicInteger(0);

	public static void main(String[] args) {
		for (int j = 0; j < 100; j++) {
			// 1: JUC-1 ConcurrentHashMap
			// new Thread(SoluByJUC::concurrentlyAccessV1).start();

			// 2: JUC-2 AutomicXXX
			new Thread(SoluByJUC::concurrentlyAccessV2).start();
		}
	}

	// 1 JUC-1 ConcurrentHashMap的 具体使用
	public static void concurrentlyAccessV1 () {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer r = new Random().nextInt();
		// 这里由于是ConcurrentHashMap，所以是线程安全的
		map.put(r,r);
		// 这里也是 线程安全的
		for (Integer i: map.keySet()) {
			System.out.println("当前 i 的值是：" + i);
		}
	}

	// 2: JUC-2 AutomicXXX 的具体使用
	public static void concurrentlyAccessV2 () {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 普通变量 不能保证操作是 原子性的
		// a++;
		// System.out.println("当前 a 的值是：" + a);

		// 通过 AtomicInteger，从而保证 操作是原子性的
		b.addAndGet(1);
		System.out.println("当前 b 的值是：" + b.get());

	}



}