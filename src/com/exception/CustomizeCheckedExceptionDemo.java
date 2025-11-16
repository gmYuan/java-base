package com.exception;

public class CustomizeCheckedExceptionDemo extends Exception {
	public CustomizeCheckedExceptionDemo() {
		super();
	}

	/**
	 * 自定义受检异常
	 * @param message 异常信息
	 */
	public CustomizeCheckedExceptionDemo(String message) {
		super(message);
	}

	/**
	 * 自定义受检异常
	 * @param message 异常信息
	 * @param cause 异常原因
	 */
	public CustomizeCheckedExceptionDemo(String message, Throwable cause) {
		super(message, cause);
	}
}
