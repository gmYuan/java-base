package com.multhread;


import java.util.concurrent.LinkedBlockingQueue;


public class Boss {


	public static void main(String[] args) {
		// 请实现一个生产者/消费者模型，
		// 其中:生产者生产 10个随机的整数供消费者使用(随机数可以通过new Random().nextInt()获得)
		// 使得标准输出依次输出它们
		// 例如:
		// Producing 42
		// Consuming 42
		// Producing -1
		// Consuming-1
		// ...

		/*
		// 方法 1：wait/notify/notifyAll
		ContainerV1  container = new ContainerV1();
		Object lock = new Object();
		ProducerV1 producer1 = new ProducerV1(container,  lock);
		ConsumerV1 consumer1 = new ConsumerV1(container, lock);
		producer1.start();
		consumer1.start();
		// producer.join();
		// producer.join();

		 */


		/*
		// 方法 2：Lock/Condition
		ReentrantLock lock = new ReentrantLock();
		ContainerV2  container = new ContainerV2(lock);

		ProducerV2 producer2 = new ProducerV2(container, lock);
		ConsumerV2 consumer2 = new ConsumerV2(container, lock);
		producer2.start();
		consumer2.start();
		*/

		// 方法 3：BlockingQueue
		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);
		LinkedBlockingQueue<Integer> signalQueue = new LinkedBlockingQueue<>(1);

		ProducerV3 producer3 = new ProducerV3(queue, signalQueue);
		ConsumerV3 consumer3 = new ConsumerV3(queue, signalQueue);
		producer3.start();
		consumer3.start();

	}

}