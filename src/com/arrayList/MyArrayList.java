package com.arrayList;

public class MyArrayList {
	// 属性 fields
	private int[] data;
	private int size;
	private int capacity;
	private final static int DEFAULT_CAP = 10;

	// 构造方法 constructor
	public MyArrayList() {
		this.data = new int[DEFAULT_CAP];
		this.capacity = DEFAULT_CAP;
		this.size = 0;
	}

	public MyArrayList(int cap) {
		if (cap < 0) {
			throw new RuntimeException("cap must be greater than 0");
		}
		if (cap < DEFAULT_CAP) {
			this.data = new int[DEFAULT_CAP];
			this.capacity = DEFAULT_CAP;
		} else {
			this.data = new int[cap];
			this.capacity = cap;
		}
		this.size = 0;
	}

	// 方法 methods
	private void checkRange(int idx) {
		if (idx < 0 || idx >= size) {
			throw new RuntimeException(String.format("index out of range: %d", idx));
		}
	}


	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public boolean contains(int element) {
		return indexOf(element) >= 0;
	}

	public int indexOf(int element) {
		for (int i = 0; i < data.length; i++) {
			if (data[i] == element) {
				return i;
			}
		}
		return -1;
	}

	public void add(int element) {
		add(size, element);
	}

	public void add(int idx, int element) {
		// S1 检查idx是否越界
		if (idx < 0 || idx > size) {
			throw new RuntimeException(String.format("index out of range: %d", idx));
		}

		// S2 检查是否需要扩容
		if (size >= capacity) {
			resize(capacity * 2);
		}
		// S3 从idx开始，所有元素都向右移动一个位置
		for (int i = size; i > idx; i--) {
			data[i] = data[i - 1];
		}
		// S4.1 插入元素
		data[idx] = element;
		// S4.2 元素个数增加1
		size++;
	}

	private void resize(int newCap) {
		// S1 检查newCap是否小于size
		if (newCap < size) {
			throw new RuntimeException(String.format("newCap must be greater than size: %d", newCap));
		}
		// S2 新建数组
		int[] newData = new int[newCap];
		// S3 复制元素
		// 相当于 System.arraycopy(data, 0, newData, 0, size);
		for (int i = 0; i < size; i++) {
			newData[i] = data[i];
		}
		// S4.1 指向新数组
		data = newData;
		// S4.2 更新容量
		capacity = newCap;
	}


	public int remove(int idx) {
		// S1-1 检查idx是否越界
		checkRange(idx);

		// S2 记录要删除的元素
		int old = data[idx];
		// S3-1  计算需要移动的元素个数
		int numToMove = size - idx - 1;
		// S3-2 移动元素：把idx后面的所有元素向左移动一位
		if (numToMove > 0) {
			System.arraycopy(data, idx + 1, data, idx, numToMove);
		}
		
		// S4 清理最后一个元素，size减1
		data[--size] = 0;
		// S5 返回要删除的元素
		return old;
	}

	public boolean removeByValue(int element) {
		for (int i = 0; i < size; i++) {
			if (data[i] == element) {
				int numToMove = size - i - 1;
				if (numToMove > 0) {
					System.arraycopy(data, i + 1, data, i, numToMove);
				}
				// S4 清理最后一个元素，size减1
				data[--size] = 0;
				return true;
			}
		}
		return false;
	}

	public int get(int index) {
		checkRange(index);
		return data[index];
	}

	public int set(int index, int element) {
		checkRange(index);
		int old = data[index];
		data[index] = element;
		return old;
	}


	public static void main(String[] args) {
		MyArrayList list1 = new MyArrayList();
		//  System.out.println("list1.size是：" + list1.size());
		//  System.out.println("list1.isEmpty()是：" + list1.isEmpty());
		//  System.out.println("list1.contains(100)是：" + list1.contains(100));

		for (int i = 0; i < 20; i++) {
			list1.add(i);
		}
		System.out.println("list1：" + list1);
		System.out.println("--------add-------------");
		list1.add(2, 99);
		System.out.println("list1：" + list1);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < size; i++) {
			sb.append(data[i]);
			if (i < size - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
