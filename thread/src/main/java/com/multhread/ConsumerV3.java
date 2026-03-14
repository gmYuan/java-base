package com.multhread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ConsumerV3 extends Thread {
	private BlockingQueue<Integer> queue;
	private BlockingQueue<Integer> signalQueue;

	public ConsumerV3(BlockingQueue<Integer> queue, BlockingQueue<Integer> signalQueue) {
		this.queue = queue;
		this.signalQueue = signalQueue;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				System.out.println("Consuming ：" + queue.take());
				signalQueue.put(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}