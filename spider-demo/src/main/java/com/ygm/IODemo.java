package com.ygm;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class IODemo {

	// 读取文件1
	public static void readFileIo() throws IOException {
		InputStream fis = new FileInputStream("/Users/jiayin/Documents/learn-day/spider-demo/src/main/java/com/ygm/BlogMain.java");
		while (true) {
			int bs = fis.read();
			if (bs == -1) {
				System.out.println("--------文件读取完毕");
				break;
			}
			System.out.print((char) bs);
		}
	}

	// 写入文件
	public static void writeFileIo() throws IOException {
		OutputStream fos = new FileOutputStream("/Users/jiayin/Documents/learn-day/spider-demo/src/main/java/com/ygm/WriteChar.txt");
		fos.write("hello world".getBytes());
		fos.close();
	}

	// 读取文件- 使用HttpClient 读取网络流
	public static void readFileNet() throws IOException {
		 HttpClient httpClient = HttpClients.createDefault();
		 HttpGet httpGet = new HttpGet("https://baidu.com");
		 HttpResponse response = httpClient.execute(httpGet);

		System.out.println(response.getStatusLine());
		System.out.println((char) response.getEntity().getContent().read());
	}

	// 读取文件- 读取 fork进程的输出
	public static void readFileFork() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("ls");
		Process process = pb.start();
		System.out.println((char) process.getInputStream().read());
	}

	public static void main(String[] args) throws IOException {
		// readFileIo();

		// writeFileIo();

		// readFileNet();

		readFileFork();

	}
}