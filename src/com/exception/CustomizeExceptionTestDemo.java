package com.exception;

import java.io.IOException;

public class CustomizeExceptionTestDemo {
	public static void testCheckedException() throws CustomizeCheckedExceptionDemo {
		System.out.println("准备开始 doHomeWork");
		throw new CustomizeCheckedExceptionDemo("checkedHomeWork 无法被 complete");
	}


	public static void testUnCheckedException()  {
		System.out.println("准备开始 unCheckedHomeWork");
		IndexOutOfBoundsException ex = new IndexOutOfBoundsException("数组越界啦 ");
		throw new CustomizeUnCheckedExceptionDemo("unCheckedHomeWork 无法被完成", ex);
	}


	public static void main(String[] args) throws IOException {
//		try {
//			testCheckedException();
//		} catch (CustomizeCheckedExceptionDemo e) {
//			e.printStackTrace();
//		} finally {
//			System.out.println("---------------------------");
//		}

		try {
			testUnCheckedException();
		} catch (CustomizeUnCheckedExceptionDemo e) {
			e.printStackTrace();
		}

	}

}
