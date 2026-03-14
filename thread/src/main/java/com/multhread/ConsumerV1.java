package com.multhread;


import java.util.Optional;
import java.util.Random;

public class ConsumerV1 extends Thread {
	private ContainerV1 container;
	private Object lock;

	public ConsumerV1(ContainerV1 container, Object lock) {
		this.container = container;
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			synchronized (lock) {
				// 只要容器内没有值（可消费），就保持等待
				while (!container.getValue().isPresent()) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 到此处说明 允许进行消费
				int r = container.getValue().get();
				System.out.println("Consuming ：" + r);
				container.setValue(Optional.empty());
				// 此外 还要发送通知，让 wait的 Producer 线程继续执行
				lock.notify();
			}
		}
	}
}