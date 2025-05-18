package com.baseLearn.java.oop;

import com.baseLearn.java.access.AccessBestPracticeDemo;
import com.baseLearn.java.access.DefaultDemo;
import com.baseLearn.java.access.PrivateDemo;

public class AccessTest {
	public static void main(String[] args) {
		PrivateDemo ex21 = new PrivateDemo(21,"哈哈1");
		// ex21.printInfo();
		
		// DefaultDemo  ex22 = new DefaultDemo(22,"哈哈2");
		
		AccessBestPracticeDemo ex3 = new AccessBestPracticeDemo(33, "哈哈2");
		ex3.PrintBestInfo();
	}
 
}
