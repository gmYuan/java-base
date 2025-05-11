package com.baseLearn.java.base;

public class BaseStrOpe {
	// 字符串常见基础操作
	public static void main(String[] args) {
		/* 1 创建/声明 字符串 */
		
		// 方法1: 使用双引号
		String s1 = "hello";
		
		// 方法2: 使用new 构造器1
		String s2 = new String("world");
		// 方法2.2：使用new 构造器2
		char[] chars = {'h', 'e', 'l', 'l', 'o'};
		String s3 = new String(chars);
		
		// 方法3 StringBuilder
		StringBuilder s4 = new StringBuilder().append('H')
				.append('E')
				.append('L')
				.append('L')
				.append('O');
		String  s5 = s4.toString();
		System.out.println("s5- StringBuilder的值是：" + s5);
		
		// 方法4 StringBuffer
		StringBuffer s6 = new StringBuffer().append('H')
				.append('E')
				.append('L')
				.append('L')
				.append('O')
				.append(" ")
				.append("world!");
		String s7 = s6.toString();
		System.out.println("s7- StringBuffer的值是：" + s7);
		
		/*  2 读取 字符串 */
		System.out.println("---------------------------------" );
		System.out.println("s1的长度是：" + s1.length()); // 5
		System.out.println("s1的值是：" + s1.charAt(0)); // h
		char char1 = s2.charAt(2);
		System.out.println("char1的值是：" + char1); // r
		// 遍历
		for (int i = 0; i < s5.length(); i++) {
			System.out.println("当前s5-char的值是：" + s5.charAt(i));
		}
		
		/* 3  字符串 连接 */
		System.out.println("-----------------字符串 连接 ----------------" );
		String s8 = s1 + " " + s2;
		System.out.println("s8的值是：" + s8);
		
		String s9 = s1.concat(" ").concat(s2);
		System.out.println("s9的值是：" + s9);
		
		/* 4  格式化 字符串 */
		System.out.println("----------------- 格式化 字符串----------------" );
		String s10 = String.format("s1的值是：%s, s2的值是：%s", s1, s2);
		System.out.println("s10的值是：" + s10);
		
		System.out.printf("格式化字符串类型：int value：%d\n " +
				"float(double) value: %f\n " +
				"2 bit of float(double) value: %.2f\n "
				+ "char value: %c\n " +
				"string value: %s\n",
				1, 2.567, 3.12159, 'a', "hello"
		);
		
		/* 5 数字 和 字符串 相互转化 */
		System.out.println("----------------- 数字 和 字符串 相互转化 ----------------" );
		
		// String ->Numeric:用包装类的parseXXX
		int num1 = Integer.parseInt("123");
		double num2 = Double.parseDouble("123.456");
		long num3 = Long.parseLong("123456789");
		float num4 = Float.parseFloat("123.45623");
		
		System.out.printf("int- num1的值是：%d\n", num1);
		System.out.printf("double- num2的值是：%f\n", num2);
		System.out.printf("long- num3的值是：%d\n", num3);
		System.out.printf("float- num4的值是：%.3f\n", num4);
		
		// Numeric ->string: 字符串拼接 || String.valueOf(num)
		String str1 = "" + 123;
		String str2 = String.valueOf(5.234);
		String str3 = String.valueOf(88888);
		System.out.printf("str1的值是：%s\n", str1);
		System.out.printf("str2的值是：%s\n", str2);
		System.out.printf("str3的值是：%s\n", str3);
		
		/* 5 字符串的常用方法 */
		System.out.println("----------------- 字符串的常用方法 ----------------" );
		String s11 = "hello world";
		String s12 = "hello world2";
		String s13 = "HELLO WORLD";
		System.out.println(s11.equals(s12));
		System.out.println(s13.indexOf('L'));
		System.out.println(s13.lastIndexOf('L'));
		
		System.out.println(s12.toUpperCase());
		System.out.println(s13.toLowerCase());
		
	}
	
}
