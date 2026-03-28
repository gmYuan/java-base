package com.ygm;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegDemo {
	public static void main(String[] args) throws IOException {
		/** 例1
		String target = "\\\\N";
		System.out.println(target);
		System.out.println(target.matches("\\\\{2}N"));
		 */

		// 例2
		// (?:)表示分组，但是不会捕获，即不保存匹配的值
		Pattern telPattern = Pattern.compile("(\\d{3})-(\\d{8})|((?:\\d{4})-(\\d{7,8}))");
		Matcher matcher = telPattern.matcher("021-12345678\n2235-8765432\n-sdfsdfsdf");
		while (matcher.find()) {
			// System.out.println(matcher.group(0));
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
			System.out.println(matcher.group(3));
			System.out.println(matcher.group(4));
			System.out.println("------------------");
		}

		// 例3
		Pattern gcPattern = Pattern.compile("(\\d+)K->(\\d+)K\\((\\d+)K\\)");
		final List<String> lines =  Files.readAllLines(new File("Main.java").toPath());
		for (String line : lines) {
			System.out.println("--------------------------");
			Matcher matcher1 = gcPattern.matcher(line);
			while (matcher1.find()) {
				System.out.println(matcher1.group(1));
				System.out.println(matcher1.group(2));
				System.out.println(matcher1.group(3));
			}
		}
	}
}