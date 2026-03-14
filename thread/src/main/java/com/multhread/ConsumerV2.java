package com.multhread;


import java.util.Optional;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerV2 extends Thread {
	private ContainerV2 container;
	private ReentrantLock lock;

	public ConsumerV2(ContainerV2 container, ReentrantLock lock) {
		this.container = container;
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				lock.lock();
				// 只要容器内 没有值，就让 还没被生产的 等待
				while (!container.getValue().isPresent()) {
					try {
						container.getNotProducedYet().await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 运行到此处，说明允许消费
				Integer r = container.getValue().get();
				System.out.println("Consuming ：" + r);
				container.setValue(Optional.empty());
				container.getNotConsumedYet().signal();
			} finally {
				lock.unlock();
			}
		}
	}
}