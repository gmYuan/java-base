package com.hashMap;

public class MyHashMap<K, V> {
	// 内部类 Node
	private class Node<K, V> {
		// 键
		private K key;
		// 值
		private V value;
		// 下一个节点
		private Node<K, V> next;

		public Node(K key, V value, Node<K, V> next) {
			this.key = key;
			this.value = value;
			this.next = next;
		}
	}

	// Fields
	// 数组默认初始化大小, 一般是2的幂次方
	private static final int DEFAULT_INITIAL_CAPACITY = 16;
	// 数组默认加载因子
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	// 当前数组 总容量
	private int capacity;
	// 当前数组 元素数量
	private int size;
	// 当前数组 加载因子
	private float loadFactor;

	// 当前数组 元素
	private Node<K, V>[] table;

	// Constructors

	// 无参构造函数, 使用默认值
	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}

	// 有参构造函数, 自定义初始化大小和加载因子
	public MyHashMap(int initialCapacity, float loadFactor) {
		this.capacity = initialCapacity;
		this.loadFactor = loadFactor;
	}

	// put 方法：添加/更新 键值对
	public V put(K key, V value) {
		return null;
	}

	// get 方法
	public V get(K key) {
		return null;
	}

	// remove 方法：删除指定键的键值对
	public V remove(K key) {
		return null;
	}

	public static void main(String[] args) {
		MyHashMap<Integer, String> map1 = new MyHashMap<>();
		map1.put(1, "one");
		map1.get(1);
		map1.remove(1);
	}

}
