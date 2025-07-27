package com.arrayList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArrayListDemo {

	public static void main(String[] args) {
		// 如何构造声明 ArrayList
		// 1. 不带类型的
		ArrayList list = new ArrayList();

		// 2 带类型的: 泛型传入的必须是 引用类型
		ArrayList<Integer> integerArrayList = new ArrayList<Integer>();
		// 会有类型限制提示
		// integerArrayList.add("2")

		// 3 ⭐⭐️ 【推荐写法：List + 钻石修饰符】
		List<String> list2 = new ArrayList<>();
		list2.add("2");

		List<Integer> list3 = new ArrayList<>();
		list3.add(4);

		// 4 内部类写法
		class User {
			private String name;
			private int age;
		}
		List<User> list4 = new ArrayList<>();
		list4.add(new User());

		// 5 可以传入一个Collection类型的参数
		Collection<Integer> c = new ArrayList<>(200);
		c.add(1);
		c.add(2);
		c.add(3);
		List<Integer> list5 = new ArrayList<>(c);


		// 6 size /isEmpty /contains
		System.out.println("Size是：" + list5.size());
		list5.add(4);
		list5.add(4);
		list5.add(6);
		System.out.println("新Size是：" + list5.size());
		System.out.println("是否为空：" + list5.isEmpty());
		System.out.println("是否包含4：" + list5.contains(4));
		System.out.println("是否包含5：" + list5.contains(5));

		// 7 indexOf /lastIndexOf
		System.out.println("----------------indexOf /lastIndexOf--------------");
		System.out.println("5的下标是：" + list5.indexOf(5));
		System.out.println("4的下标是：" + list5.indexOf(4));
		System.out.println("4的lastIdx下标是：" + list5.lastIndexOf(4));
		System.out.println("1的lastIdx下标是：" + list5.lastIndexOf(1));

		// 8 toArray
		System.out.println("--------------toArray----------------");
		// 方法1：直接调用
		// Object[] arrays = list5.toArray();
    //		for (Object item : arrays) {
    //			int num = (int) item;
    //			System.out.println(num);
    //		}

		// todo 待研究其源码原理，传入new Integer[10] 表现是不同的
		// 方法2：传入一个类型数组
		Integer[] ints = list5.toArray(new Integer[0]);
		for (int num : ints) {
			System.out.println(num);
		}


		// 9 get /add /set /remove
		System.out.println("-------------- get /add /set /remove----------------");
		List<Integer> list6 = new ArrayList<>(list5);
		System.out.println("list6是：" + list6);

    // System.out.println("list6的第0个元素是：" + list6.get(0));
//		for (int i = 0; i < list6.size(); i++) {
//			System.out.println("list6的第" + i + "个元素是：" + list6.get(i));
//		}
		// lambda表达式写法: 遍历ArrayList
		// list6.forEach(System.out::println);

		// set
		list6.set(4,5);
		System.out.println("list6是：" + list6);
		// add： 无索引，默认添加到最后
		list6.add(7);
		System.out.println("list6是：" + list6);
		// add： 有索引，添加到指定位置: 加到index位置，其他后面的元素往右移
		list6.add(0,0);
		System.out.println("list6是：" + list6);
		// 超出size范围，会报错
    //  list6.add(8,6);

		// remove
		System.out.println("--------------remove----------------");
		// 传入索引，移除指定位置的元素，返回的类型是T
		int num =  list6.remove(7);
		System.out.println("返回的num是：" + num);
		System.out.println("list6是：" + list6);
		// 传入元素，移除指定元素，返回的类型是boolean
		boolean hasRemoved = list6.remove(new Integer(6));
		System.out.println("返回的hasRemoved是：" + hasRemoved);
		System.out.println("list6是：" + list6);

		// remove
		System.out.println("--------------clear----------------");
		list6.clear();
		System.out.println("list6是：" + list6);





	}
}
