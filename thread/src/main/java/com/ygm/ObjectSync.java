package com.ygm;

public class ObjectSync {
	private static final Object lock = new Object();

	// 会报错 Exception in thread "main" java.lang.IllegalMonitorStateException
	// 因为 lock.wait 必须要 监视了 monitor 才能调用 wait
	// public static void main(String[] args) throws InterruptedException {
		// lock.wait();
	// }

	public static void main(String[] args) throws InterruptedException {
		synchronized (lock) {
			lock.wait();
		}
	}


}