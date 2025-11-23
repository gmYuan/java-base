package com.annotation;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class SuppressWarningsDemo {

	@SuppressWarnings("unused")
	public void suppressUncheckedWarnings() {
		@SuppressWarnings("rawtypes")
		List list1 = new ArrayList();
	}


	public void test() {
		@SuppressWarnings("unused")
		int a = 2;
		@SuppressWarnings("unused")
		int d = 4;
	}
}
