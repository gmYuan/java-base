package com.ygm;

import org.junit.Assert;
import org.junit.Test;

public class MyServiceTest {

	@Test
	public void grade_59() {
		// 准备
		int score = 59;
		MyService myService = new MyService();
		// 执行
		String relRes = myService.grade(score);
		// 验证
		String expected = "不及格";
		Assert.assertEquals(expected, relRes);
	}

	@Test
	public void grade_89() {
		// 准备
		int score = 89;
		MyService myService = new MyService();
		// 执行
		String relRes = myService.grade(score);
		// 验证
		String expected = "及格";
		Assert.assertEquals(expected, relRes);
	}

}