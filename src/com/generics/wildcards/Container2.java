package com.generics.wildcards;
import java.util.ArrayList;
import java.util.List;

public class Container2<T> {
	List<T> items = new ArrayList<>();

	public List<T> getItems() {
		return items;
	}

	public void addItem(T item) {
		items.add(item);
	}

	// 无限定通配符
	public static void process(List<?> list) {
		// 不确定list里面具体是什么类型，所以不能进行写操作
		// list.add(new Integer(1));
		// list.add(new Apple())

		// 可以进行最宽泛类型的 读操作
		for (Object item : list) {
			System.out.println(item);
		}
		// 换成 Apple element 就无法编译成功, 只能用最宽泛的 Object element
		Object element = list.get(0);
		System.out.println("element 是 " + element);
	}

	public void doSomething() {
		Container2<T> tCon = new Container2<>();
		process(tCon.getItems());
	}

	// 上界通配符: ? extends T
	// List<? extends Fruit> list: 表示 list支持 Fruit 以及 所有Fruit的子类
	public static void processWithExtends(List<? extends Fruit> list) {
		// 只规定了上界，所以写操作还是无法支持
		// list.add(new Integer(1));
		// list.add(new Fruit());
		// list.add(new Apple());

		// 读操作由于确定了上界，可以使用 其确定的 上界类型（以及更上界的）
		Fruit fruit = list.get(0);
		Food food = list.get(0);
		Object element = list.get(0);

		// 不能使用 确定的上界类型的 子类，因为不一定就是Apple
		// Apple apple = list.get(0);
		System.out.println("processWithExtends的food 是 " + food);
	}

	// 下界通配符: ? super T
	// List<? super Fruit> list: 表示 list支持 Fruit 以及 所有Fruit的父类
	public static void processWithSuper(List<? super Fruit> list) {
		// 由于确定了下界，所以可以使用 确定的下界类型（以及更下界的）进行 写操作
		list.add(new Fruit());
		list.add(new Apple());
		// 非法，超出下界的 不能随意写入，因为类型无法确定
		// list.add(new Food());
		// list.add(new Vegetable());
		// list.add(new Object());

		// 读操作是非法的，因为list可能是List<Food>或List<Object>
		// 编译器无法确定具体是哪种类型，只能确定是Object
		
		// Apple apple = list.get(0);
		// Fruit fruit = list.get(0);
		// Food food = list.get(0);
	}

	/*
	综上 总结出 PECS原则:
	Producer extends: 用于读操作
	  - 从集合中读取类型T的数据 (集合是生产者)
		- 从容器角度看，能保证这个容器的上界(最大类型是T)
		- 所以 (取出) 读取安全
		- “取出的 上限”

	Consumer super: 用于写操作
	  - 往集合中添加类型T的数据(集合是消费者)
		- 从容器角度看，能保证这个容器的下界(最小类型是T)
		- 所以 (放入) 写入安全
		- “扔入的 下限”

	PECS原则的本质是 类型安全
	*/


	public static void main(String[] args) {
		// Apple
		Container2<Apple> appleCon = new Container2<>();
		appleCon.addItem(new Apple());
		List<Apple> appleList =  appleCon.getItems();
		// Food
		Container2<Food> foodCon = new Container2<>();
		foodCon.addItem(new Food());
		List<Food> foodList =  foodCon.getItems();
		// Fruit
		Container2<Fruit> fruitCon = new Container2<>();
		fruitCon.addItem(new Fruit());
		List<Fruit> fruitList =  fruitCon.getItems();
		// Vegetable
		Container2<Vegetable> vegetableCon = new Container2<>();
		vegetableCon.addItem(new Vegetable());
		List<Vegetable> vegetableList =  vegetableCon.getItems();


		// 通配符- process- 合法
		process(appleList);
		process(foodList);
		process(fruitList);
		process(vegetableList);
		// 合法
		// process(new ArrayList<Integer>());

		// 上界通配符
		System.out.println("-----------上界通配符---------------");
		processWithExtends(appleList);
		processWithExtends(fruitList);

		// processWithExtends(vegetableList);
		// processWithExtends(foodList);
		// processWithExtends(new ArrayList<Integer>());
    // 非法
		// processWithExtends(new ArrayList<Integer>());


		// 下界通配符
		System.out.println("------------下界通配符--------------");
		ArrayList<Object> objectList = new ArrayList<>();
		objectList.add(new Object());
		processWithSuper(objectList);
		processWithSuper(fruitList);
		processWithSuper(foodList);
		// 非法不支持
		// processWithSuper(vegetableList);
		// processWithSuper(appleList);



	}
}
