package com.annotation;



@MySingleParaDef
public class MySingleParaDemo {
	public void test(@MySingleParaDef("paramB") int b) {
		System.out.println("b = " + b);
	}

	public static void main(String[] args) {
		MySingleParaDemo demo = new MySingleParaDemo();
		demo.test(44);
	}
}
