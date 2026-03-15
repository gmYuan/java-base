package com.ygm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SuperAndExtends {
	public static class Animal{};

	public static class Cat extends Animal{};

	public static <T extends Comparable> T getMaxV1(T a, T b) {
		return a.compareTo(b) >= 0 ? a : b;
	}

	public static <T extends Comparable<T>> T getMaxV2(T a, T b) {
		return a.compareTo(b) >= 0 ? a : b;
	}

	// Super相关
	public static class AnimalComp implements Comparator<Animal> {
		@Override
		public int compare(Animal o1, Animal o2) {
			return 0;
		}
	};
	public static class CatComp implements Comparator<Cat> {
		@Override
		public int compare(Cat o1, Cat o2) {
			return 0;
		}
	};
	public static <T> void sortV1(List<T> list, Comparator<? super T> c) {
		list.sort(c);
	}

	public static void main(String[] args) {
		sortV1(new ArrayList<Cat>(), new AnimalComp());



		getMaxV1("aaa", 1);
		// 这里不会编译报错
		getMaxV2(2L, 1L);
		// 这里会编译报错 ⚠️⚠️
		// getMaxV2("aaa", 1);

	}
}