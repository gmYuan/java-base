package com.generics;
import java.util.AbstractList;
import java.util.List;

public class MyArrayListT<E> extends AbstractList<E> implements List<E> {
	// 属性 fields
	private E[] data;
	private int size;
	private int capacity;
	private final static int DEFAULT_CAP = 10;

	@SuppressWarnings("unchecked")
	// 我知道这里有类型转换风险，但我确定是安全的，请不要警告
	// 构造方法 constructor
	public MyArrayListT() {
		this.data = (E[]) new Object[DEFAULT_CAP];
		this.capacity = DEFAULT_CAP;
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	// 我知道这里有类型转换风险，但我确定是安全的，请不要警告
	public MyArrayListT(int cap) {
		if (cap < 0) {
			throw new RuntimeException("cap must be greater than 0");
		}
		if (cap < DEFAULT_CAP) {
			this.data = (E[]) new Object[DEFAULT_CAP];
			this.capacity = DEFAULT_CAP;
		} else {
			this.data = (E[]) new Object[cap];
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


  // 类型擦除的限制：
	// 1. 不能用泛型重写 非泛型方法， 所以这里不能是 contains(E element)
	// 2. 不能创建泛型数组，如 new E[10]
	// 3. 运行时类型信息丢失
	public boolean contains(Object element) {
		return indexOf(element) >= 0;
	}

	public int indexOf(Object element) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].equals(element)) {
				return i;
			}
		}
		return -1;
	}

	public boolean add(E element) {
		add(size, element);
		return true;
	}

	public void add(int idx, E element) {
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
		E[] newData = (E[]) new Object[newCap];
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


	public E remove(int idx) {
		// S1-1 检查idx是否越界
		checkRange(idx);

		// S2 记录要删除的元素
		E old = data[idx];
		// S3-1  计算需要移动的元素个数
		int numToMove = size - idx - 1;
		// S3-2 移动元素：把idx后面的所有元素向左移动一位
		if (numToMove > 0) {
			System.arraycopy(data, idx + 1, data, idx, numToMove);
		}
		
		// S4 清理最后一个元素，size减1
		data[--size] = null;
		// S5 返回要删除的元素
		return old;
	}

	public boolean remove(Object element) {
		for (int i = 0; i < size; i++) {
			if (data[i].equals(element)) {
				int numToMove = size - i - 1;
				if (numToMove > 0) {
					System.arraycopy(data, i + 1, data, i, numToMove);
				}
				// S4 清理最后一个元素，size减1
				data[--size] = null;
				return true;
			}
		}
		return false;
	}

	public E get(int index) {
		checkRange(index);
		return data[index];
	}

	public E set(int index, E element) {
		checkRange(index);
		E old = data[index];
		data[index] = element;
		return old;
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


	public static void main(String[] args) {
		MyArrayListT<Integer> list1 = new MyArrayListT<>();
		System.out.println("The size of list 1:" + list1.size());
		System.out.println("empty of list 1:" + list1.isEmpty());

		System.out.println("-------- for and contains----------");
		for (int i = 0; i < 10; i++) {
			list1.add(i);
		}
		System.out.println("The size of list 1:" + list1.size());
		System.out.println("Contains 100:" + list1.contains(100));

		System.out.println("-------remove && size -----------");
		System.out.println("Number to remove:" + list1.remove(1));
		System.out.println("The size of list 1:" + list1.size());


		System.out.println("-------get && set -----------");
		System.out.println("Element of index 5:"+ list1.get(5));
		int oldValue = list1.set(5, 150);
		System.out.println("old value of index 5:"+ oldValue);
		System.out.println("Current value of index 5: "+ list1.get(5));

		System.out.println("------- list2 -----------");
		MyArrayListT<String>list2 = new MyArrayListT<>( 20);
		list2.add("abd");
		list2.add("Hello World");
		System.out.println("list2: "+ list2);
	}
}
