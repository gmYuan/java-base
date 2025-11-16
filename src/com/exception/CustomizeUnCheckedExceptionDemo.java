package com.exception;

public class CustomizeUnCheckedExceptionDemo extends RuntimeException {
	public CustomizeUnCheckedExceptionDemo() {
		super();
	}

	/**
	 * 自定义受检异常
	 * @param message 异常信息
	 */
	public CustomizeUnCheckedExceptionDemo(String message) {
		super(message);
	}

	/**
	 * 自定义受检异常
	 * @param message 异常信息
	 * @param cause 异常原因
	 */
	public CustomizeUnCheckedExceptionDemo(String message, Throwable cause) {
		super(message, cause);
	}
}
