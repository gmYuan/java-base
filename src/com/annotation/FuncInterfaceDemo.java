package com.annotation;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface FuncInterfaceDemo {
	void doSomething();

	// @FunctionalInterface里，只能有一个抽象方法
	// void doReport();

	default void doSomethingElse() {
	}

	default void doWork() {
	}



}
