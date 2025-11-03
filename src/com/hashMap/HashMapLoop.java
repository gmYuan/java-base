package com.hashMap;

import java.util.HashMap;
import java.util.Map;


public class HashMapLoop {
	public static void main(String[] args) {
		Map<Integer, Integer> map = new HashMap<>(4);
		map.put(1, 1);
		map.put(9, 9);
		Runnable task = new Runnable() {
			@Override
			public void run() {
				map.put(81, 81);
			}
		};

		Thread t1 = new Thread(task, "hm-1");
		Thread t2 = new Thread(task, "hm-2");
		t1.start();
		t2.start();
	}
}
