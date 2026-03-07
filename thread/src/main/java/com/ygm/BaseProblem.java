package com.ygm;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class BaseProblem {
	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		new Thread(BaseProblem::SlowFileOpe).start();
		new Thread(BaseProblem::SlowFileOpe).start();
		new Thread(BaseProblem::SlowFileOpe).start();
		new Thread(BaseProblem::SlowFileOpe).start();
//		SlowFileOpe();
		long t1 = System.currentTimeMillis();
		// 不用 thread耗时约 900 ms；使用后是99ms
		System.out.println("总耗时是：" + (t1 - t0) + "ms");
	}

	public static void SlowFileOpe() {
		try {
			File tempFile = File.createTempFile("tmp", "");
			System.out.println("临时文件路径: " + tempFile.getAbsolutePath());

			for (int i = 0; i < 5000; i++) {
				try (FileOutputStream fos = new FileOutputStream(tempFile)) {
					fos.write(i);
				}
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

	}
}