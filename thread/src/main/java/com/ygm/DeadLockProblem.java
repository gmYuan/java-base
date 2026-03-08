package com.ygm;

public class DeadLockProblem {
	private static final Object lock1 = new Object();
	private static final Object lock2 = new Object();

	static class Thread1 extends Thread {
		@Override
		public void run() {
			synchronized (lock1) {
				try {
					System.out.println("线程1里的 synchronized-1");
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock2) {
					System.out.println("线程1里的 synchronized-1-2");
				}

			}
		}
	}

	static class Thread2 extends Thread {
		@Override
		public void run() {
			synchronized (lock2) {
				try {
					System.out.println("线程2里的 synchronized-2");
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock1) {
					System.out.println("线程2里的 synchronized-2-1");
				}
			}
		}
	}


	public static void main(String[] args) {
		new Thread1().start();
		new Thread2().start();
	}


}