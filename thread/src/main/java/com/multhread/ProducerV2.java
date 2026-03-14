package com.multhread;


import java.util.Optional;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerV2 extends Thread {
	private ContainerV2 container;
	private ReentrantLock lock;

	public ProducerV2(ContainerV2 container, ReentrantLock lock) {
		this.container = container;
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				lock.lock();
				// 只要容器内还有值，就让 还未被消费的 等待
				while (container.getValue().isPresent()) {
					try {
						container.getNotConsumedYet().await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 运行到此处，说明允许生产
				int r = new Random().nextInt();
				System.out.println("Producing ：" + r);
				container.setValue(Optional.of(r));
				container.getNotProducedYet().signal();
			} finally {
				lock.unlock();
			}
		}
	}
}