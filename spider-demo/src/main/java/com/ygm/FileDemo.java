package com.ygm;


import java.io.*;

public class FileDemo {

	// 1-1 创建文件
	public static void creteMyFile() throws IOException {
		// 使用 File 创建一个文件，File表示的是一个路径
		File fi = new File("/Users/ygm/Documents/learn/spider-demo/src/main/java/com/ygm/IODemo2.java");
		fi.createNewFile();
	}

	// 2-1 检查文件信息
	public static void checkFileInfo() throws IOException {
		// 使用 File 创建一个文件，File表示的是一个路径
		File fi = new File("/Users/ygm/Documents/learn/spider-demo/src/main/java/com/ygm/IODemo.java");
		System.out.println(fi.isFile());
	}

	// 2-2 查询子文件
	public static void queryChildFile() throws IOException {
		// 使用 File 创建一个文件，File表示的是一个路径
		File dir = new File("/Users/ygm/Documents/learn/spider-demo/src/main/java/com/ygm");
		// 绝对路径
		File child1 = new File(dir, "child1.txt");
		System.out.println("child1是：" + child1.getPath());
		// 相对路径
		System.out.println("未处理地址是：" + new File(dir, "././../"));
		System.out.println("处理地址是：" + new File(dir, "././../").getCanonicalPath());
	}


	public static void main(String[] args) throws IOException {
		// creteMyFile();

		// checkFileInfo();

		queryChildFile();
	}
}