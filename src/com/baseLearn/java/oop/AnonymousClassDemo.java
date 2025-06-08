package com.baseLearn.java.oop;
import com.baseLearn.java.oop.abstraction.Bird;
import com.baseLearn.java.oop.abstraction.Flyable;

// 匿名类 使用示例
public class AnonymousClassDemo {
	public void collect(Bird bird) {
		System.out.println("开始收集" + bird);
	}
	
	public static void main(String[] args) {
		AnonymousClassDemo demo = new AnonymousClassDemo();
		// 匿名类实现 抽象类
		// 类 '派生自 Bird 的匿名类' 必须在 'Flyable' 中实现 abstract 方法 'fly()'
		demo.collect(new Bird() {
			@Override
		  public void fly() {
				System.out.println("匿名类中实现的 fly");
		  }
			
			@Override
			public void sleep() {
				System.out.println("匿名类中实现的 sleep");
			}
		});
		
		String name = "匿名类-鸟类";
		demo.collect(new Bird(name) {
			@Override
			public void fly() {
				System.out.println("匿名类2中实现的 fly");
			}
			@Override
			public void sleep() {
				System.out.println("匿名类2中实现的 sleep");
			}
		});
		
		// 匿名类实现 抽象接口
		Flyable flyable = new Flyable() {
			@Override
			public void fly() {
				System.out.println("匿名类中实现的 fly");
			}
		};
		Flyable.prepare();
		flyable.fly();
	
		
		
		
	}
}
