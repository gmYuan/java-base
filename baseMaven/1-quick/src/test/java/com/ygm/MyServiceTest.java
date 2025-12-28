package com.ygm;

import static org.junit.Assert.*;
public class MyServiceTest {

	@org.junit.Test
	public void testGrade_59() {
		int score = 59;
		MyService myService = new MyService();
		String expected = "不及格";
		String actual = myService.grade(score);
		assertEquals(expected, actual);
	}

	@org.junit.Test
	public void testGrade_89() {
		int score = 89;
		MyService myService = new MyService();
		String expected = "及格";
		String actual = myService.grade(score);
		assertEquals(expected, actual);
	}
}