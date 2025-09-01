package com.generics.wildcards;
import java.util.List;


public class Container1<T> {
	List<T> items;

	public List<T> getItems() {
		return items;
	}

	public void addItem(T item) {
		items.add(item);
	}

	public static void main(String[] args) {
		Container1<Apple> container = new Container1<>();
		container.addItem(new Apple());
		// container.addItem(new Banana());

		// 需要解决类型上的 继承问题
		// Container<Fruit> container1 = new Container<Apple>();



	}
}
