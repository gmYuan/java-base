package com.multhread;


import java.util.Optional;
import java.util.Random;

public class ProducerV1 extends Thread {
	private ContainerV1 container;
	private Object lock;

	public ProducerV1(ContainerV1 container, Object lock) {
		this.container = container;
		this.lock = lock;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			synchronized (lock) {
				// 只要容器内还有值，就保持等待
				while (container.getValue().isPresent()) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 到此处说明 允许进行生产
				int r = new Random().nextInt();
				System.out.println("Producing ：" + r);
				container.setValue(Optional.of(r));
				// 此外 还要发送通知，让 wait的 Consumer 线程继续执行
				lock.notify();
			}
		}
	}
}