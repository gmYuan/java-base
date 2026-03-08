package com.ygm;

import java.util.*;

public class SoluBySynchronized {

	private static int i = 0;
	// 1 synchronized(obj): 把这个对象obj 当成锁
	private static final Object lock1 = new Object();

	// 2 synchronized 静态方法： 把Class对象 当成锁

	// 3: 实例对象的 synchronized: 把该实例对象 当成锁
	private int v = 0;

	//4: Collections.synchronized
  private static final Map<Integer, Integer> map = 	Collections.synchronizedMap(new HashMap<>());



	//1：synchronized(obj): 把这个对象obj 当成锁 具体实现
	public static void addValsByLockSync() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (lock1) {
			i++;
			//  在这里打印, i 是按序增加的，且会保证总值正确
			// System.out.println("当前 i 的值是：" + i);
		}
		// 在这里打印，就能看出 i 是跳跃增加的，但是会保证总值正确
		// System.out.println("当前 i 的值是：" + i);
	}

	// 2 synchronized 静态方法： 把Class对象 当成锁 具体实现
	public synchronized static void addValsByClassSync() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		i++;
		//  在这里打印, i 是按序增加的，且会保证总值正确
		System.out.println("当前 i 的值是：" + i);

	}

	// 3-1 实例对象的 synchronized: 把该实例对象 当成锁 具体实现
	private synchronized void addValsByObjIns() {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		v++;
		//  在这里打印, v 是按序增加的，且会保证总值正确
		System.out.println("当前 v 的值是：" + v);
	}

	// 3-2 以上 实例对象的实现，其实等价于
	private synchronized void addValsByObjInsV2() {
		synchronized (this) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			v++;
			//  在这里打印, v 是按序增加的，且会保证总值正确
			System.out.println("当前 v 的值是：" + v);
		}
	}


	// 4 Collections.synchronized + synchronized 静态方法
	public synchronized static void concurrentlyAccess () {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Integer r = new Random().nextInt();
		// 虽然这里 map是 Collections.synchronizedMap，但是它只能保证 map的操作是线程安全的
		map.put(r,r);
		// 所以这里由于 for循环里的查找 不是原子性操作，所以需要 synchronized 来修饰 静态方法
		for (Integer i: map.keySet()) {
			System.out.println("当前 i 的值是：" + i);
		}
	}


	public static void main(String[] args) {
		// 1：synchronized(obj): 把这个对象obj 当成锁
		// for (int j = 0; j < 1000; j++) {
			// new Thread(SoluBySynchronized::addValsByLockSync).start();
		// }

		// 2：synchronized 静态方法： 把Class对象 当成锁
		// for (int j = 0; j < 1000; j++) {
			// new Thread(SoluBySynchronized::addValsByClassSync).start();
		// }

		// 3-1 实例对象的 synchronized: 把该实例对象 当成锁
		// SoluBySynchronized soluClazzIns = new SoluBySynchronized();
		// for (int j = 0; j < 1000; j++) {
			// new Thread(soluClazzIns::addValsByObjIns).start();
		// }

		// 3-2 实例对象的 synchronized: 把该实例对象 当成锁 的等价写法
		// SoluBySynchronized soluClazzIns = new SoluBySynchronized();
		// for (int j = 0; j < 100; j++) {
			// new Thread(soluClazzIns::addValsByObjInsV2).start();
		// }

		// 4 Collections.synchronized + synchronized 静态方法
		for (int j = 0; j < 100; j++) {
		  new Thread(SoluBySynchronized::concurrentlyAccess).start();
		}

	}


}