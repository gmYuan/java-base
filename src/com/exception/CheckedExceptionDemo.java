package com.exception;

import java.io.IOException;

public class CheckedExceptionDemo {
	// 1 方法1：用throws关键字声明受检异常，抛出异常让调用方自己处理
	public static void doHomeWork(boolean complete) throws IOException {
		System.out.println("准备开始 doHomeWork");
		if (!complete) {
			throw new IOException("HomeWork 无法被 complete");
		}
		System.out.println("End doHomeWork");
	}

	public static void StudentDoWork(boolean complete) throws IOException {
		doHomeWork(complete);
	}


	public static void main(String[] args) throws IOException {
		boolean complete = Boolean.parseBoolean(args[0]);
		System.out.println("cli的 complete = " + complete);
		StudentDoWork(complete);
	}

}
