package com.multhread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ProducerV3 extends Thread {
	private BlockingQueue<Integer> queue;
	private BlockingQueue<Integer> signalQueue;


	public ProducerV3(BlockingQueue<Integer> queue, BlockingQueue<Integer> signalQueue) {
		this.queue = queue;
		this.signalQueue = signalQueue;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				int r = new Random().nextInt();
				System.out.println("Producing ：" + r);
				queue.put(r);
				signalQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}