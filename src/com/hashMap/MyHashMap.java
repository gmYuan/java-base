package com.hashMap;

public class MyHashMap<K, V> {
	// 内部类 Node
	//
	static class Node<K, V> {
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

		@Override
		public String toString() {
			return "Node{" +
					"key=" + key +
					", value=" + value +
					", next=" + next +
					'}';
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
		// 为什么不能是 new Node<K, V>[this.capacity];
		// 因为Java中的泛型存在类型擦除机制
		// 在运行时，泛型类型信息会被擦除，所以不能直接创建泛型数组
		this.table = new Node[this.capacity];
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	private void resize(int newCapacity) {
		// 实现思路：
		// 1. 更新 数组容量 + 创建新数组
		// 2. rehashing重哈希: 遍历老的哈希表中的哈希桶, 将每个元素重新计算哈希值, 并放到新的哈希表中
		// 3. 更新引用

		this.capacity = newCapacity;
		Node<K, V>[] newTable = new Node[newCapacity];

		// 2 rehashing重哈希
		// 2.1 遍历 table里 的每个桶
		for (Node<K, V> node : table) {
			// 2.2-1 遍历桶里的 每个链表节点
			while (node != null) {
				// 2.3-1 计算新的哈希值
				int newIdx = hash(node.key);
				// 2.3-2 如果新桶里 没有头节点，则创建新的头节点
				Node<K, V> newNode = new Node<>(node.key, node.value, null);
				if (newTable[newIdx] == null) {
					newTable[newIdx] = newNode;
				} else {
					// 2.3-3 如果新桶里 有头节点，则尾插法(一直遍历到尾部) 添加新节点
					Node<K, V> cur = newTable[newIdx];
					while (cur.next != null) {
						cur = cur.next;
					}
					cur.next = newNode;
				}
				// 2.2-2 更新 node 为下一个节点
				node = node.next;
			}
		}

		//3 更新引用
		this.table = newTable;
	}

	// put 方法：添加/更新 键值对
	public V put(K key, V value) {
		// 实现思路：
		// 0. 检测到达阈值, 则进行扩容
		// 1. 通过hash(key)，计算出对应的Node桶- index
		// 2. 根据index, 找到对应的Node桶 头节点
		// 3. 遍历链表, 查找键是否存在：如果存在, 更新值；如果不存在, 【尾插法】添加新节点
		if (size >= capacity * loadFactor) {
			resize(capacity * 2);
		}
		int index = hash(key);
		Node<K, V> node = table[index];
		// 当前桶为空（不存在头节点）, 直接创建头节点即可
		if (node == null) {
			// 为什么提示需要 new Node<>，加上一个尖括号
			// 钻石操作符<>的用法:
			// 当创建泛型类的实例时，如果构造函数的泛型类型参数可以从上下文推断出来，就可以使用<>来简化代码
			table[index] = new Node<>(key, value, null);
			size++;
			return null;
		}
		// 当前桶不为空（存在头节点）, 遍历链表, 查找键是否存在
		while (node != null) {
			// 如果键存在, 更新值, 并返回旧值
			if (node.key == key) {
				V oldValue = node.value;
				node.value = value;
				return oldValue;
			}
			// 如果遍历完链表, 仍未找到键, 则尾插法添加新节点
			if (node.next == null) {
				node.next = new Node<>(key, value, null);
				size++;
			}
			node = node.next;
		}
		return null;
	}

	private int hash(K key) {
		// 为什么key可以有 hashCode 方法？hashCode会返回什么？
		// 在Java中，所有的类都直接或间接继承自Object类，而hashCode()方法是Object类的一个方法
		// 这个方法返回一个32位的整数，用来标识对象的哈希码值
    // return key.hashCode() % this.capacity;

		// 注意，如果key是长字符，如'aaaa'，那么hashCode可能为负，这时候需要把它放到数组0的位置
		// hashCode() 可能返回负数的原因是 由于 int 溢出，会自动转为负数
		return Math.max(0, key.hashCode() % this.capacity ) ;
	}

	// get 方法
	public V get(K key) {
		// 实现思路：
		// 1. 通过hash(key)，计算出对应的Node桶- index
		// 2. 根据index, 找到对应的Node桶 头节点
		// 3. 遍历链表, 查找键是否存在：如果存在, 返回值；如果不存在, 返回null
		int index = hash(key);
		Node<K, V> node = table[index];
		while (node != null) {
			if (node.key == key) {
				return node.value;
			}
			node = node.next;
		}
		return null;
	}

	// remove 方法：删除指定键的键值对
	public V remove(K key) {
		// 实现思路：
		// 1. 通过hash(key)，计算出对应的Node桶- index
		// 2. 根据index, 找到对应的Node桶 头节点
		// 3. 遍历链表, 查找键是否存在：如果存在, 【单链表删除节点】；如果不存在, 返回null
		int index = hash(key);
		Node<K, V> node = table[index];
		// 创建虚拟头节点，来实现 单链表删除节点
		Node<K, V> dummy = new Node<>(null, null, node);
		Node<K, V> pre = dummy, cur = node;
		while (cur != null) {
			if (cur.key == key) {
				// 找到键, 执行删除操作
				pre.next = cur.next;
				cur.next = null;
				size--;
				// 更新table[index]指向新的头节点
				table[index] = dummy.next;
				return cur.value;
			}
			pre = cur;
			cur = cur.next;
		}
		// 遍历完链表, 仍未找到键, 返回null
		return null;
	}

	public static void main(String[] args) {
		MyHashMap<Integer, String> map1 = new MyHashMap<>();
		map1.put(5, "one");
		map1.put(6, "two");
		map1.put(21, "three");
		for (Node<Integer, String> node : map1.table) {
			System.out.println(node);
		}

		System.out.println("---------------map.get---------------");
		System.out.println("map1.get(5) = " + map1.get(5));
		System.out.println("map1.get(6) = " + map1.get(6));
		System.out.println("map1.get(21) = " + map1.get(21));

		System.out.println("---------------map.remove---------------");
		System.out.println("map1.remove(5) = " + map1.remove(5));
		System.out.println("map1.get(5) = " + map1.get(5));
		System.out.println("map1.size = " + map1.size());
		for (Node<Integer, String> node : map1.table) {
			System.out.println(node);
		}

	}

}
