package com.collections.java;

import java.util.Iterator;

public class MyIterator implements Iterator {
	private int[] nums;
	private int idx;

	public MyIterator(int[] nums) {
		this.nums = nums;
		this.idx = 0;
	}

	@Override
	public boolean hasNext() {
		return this.idx < this.nums.length;
	}

	@Override
	public Object next() {
		return this.nums[this.idx++];
	}

	public static void main(String[] args) {
		Iterator iter1 = new MyIterator(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
		while (iter1.hasNext()) {
			int res = (int) iter1.next();
			System.out.println("iter1的当前值是：" + res);
		}
		// 越界时会报错: Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 9
		// iter1.next();


	}
}
