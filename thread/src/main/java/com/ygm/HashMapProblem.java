package com.ygm;

import java.util.HashMap;
import java.util.Random;

public class HashMapProblem {
	private static final HashMap<Integer, Integer> map = new HashMap<>();

	public static void main(String[] args) {
		for (int i = 0; i < 1000; i++) {
			new Thread(HashMapProblem::putIfAbsent).start();
		}
	}

	public static void putIfAbsent() {
		try {
			// 加上sleep后才更容易看出问题
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int randomNum = new Random().nextInt(10);
		//随机生成一个1到10之间的数字，如果它不在map中，就把它加入map
		// 但是在实际执行时， 由于多线程，还是会可能 加入重复的 key
		if (!map.containsKey(randomNum)) {
			map.put(randomNum, randomNum);
			System.out.println("put的值是: " + randomNum);
		}

	}
}