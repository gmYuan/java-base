package com.ygm;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.nio.charset.Charset;

public class NIODemo {

	// 读取文件1-旧方法
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

	// 读取文件1-新方法
	public static void readFileIoByUtils() throws IOException {
		File fin = new File("/Users/ygm/Documents/learn/spider-demo/src/main/java/com/ygm/BlogMain.java");
		String txt = FileUtils.readFileToString(fin, Charset.defaultCharset());
		System.out.println(txt);
	}

	// 读取网络流数据2-新方法
	public static void readNetStreamIoByUtils() throws IOException {
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://baidu.com");
		HttpResponse response = httpClient.execute(httpGet);
		String html = IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset());
		System.out.println(html);
	}


	public static void main(String[] args) throws IOException {
		// readFileIo();
		// readFileIoByUtils();

		readNetStreamIoByUtils();



	}
}