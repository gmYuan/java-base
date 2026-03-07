package com.ygm;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class SharedDataProblem {
	private static int i = 0;

	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			new Thread(SharedDataProblem::modifySharedData).start();
		}

		long t1 = System.currentTimeMillis();
		System.out.println("总耗时是：" + (t1 - t0) + "ms");
	}

	public static void modifySharedData() {
		try {
			// 加上sleep后才更容易看出问题
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// i++不是一个 原子性操作！！
		i++;
		System.out.println("i的值是:" + i);

	}
}