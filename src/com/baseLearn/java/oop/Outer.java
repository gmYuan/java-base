package com.baseLearn.java.oop;

public class Outer {
	private int count = 0;
	
	public void outerMethod() {
		System.out.println("我是外部类的 实例方法");
		System.out.println("-----------------------");
	}
	
	public static void outerStaticMethod (){
		System.out.println("我是外部类的 静态方法");
		System.out.println("-----------------------");
	}
	
	public void useInnerMethod() {
		 Inner inner = new Inner();
		 inner.innerMethod();
	}
	
	public void useInnerStaticMethod() {
		StaticInner ex2 = new StaticInner();
		ex2.staticInnerMethod();
	}
	
	
	//2 内部 非静态类
	private class Inner {
		public void innerMethod() {
			outerMethod();
			outerStaticMethod();
			
			System.out.println("我是内部类Inner的 实例方法");
			System.out.println("-----------------------");
		}
	}
	
	// 3 内部 非静态类
	protected static class StaticInner {
		private Outer outer;
		
		public StaticInner() {
		}
		
		public StaticInner(Outer outer) {
			this.outer = outer;
		}
		
		public void staticInnerMethod() {
			// 无法从 static 上下文引用非 static 方法 'outerMethod()'
			// outerMethod();
			
			// 有了this执行外部对象之后，就可以使用外部实例方法了
			outer.outerMethod();
			
			outerStaticMethod();
			
			System.out.println("我是静态内部类StaticInner的 实例方法");
			System.out.println("-----------------------");
		}
		
		public static void main(String[] args) {
			Outer outer1 = new Outer();
			StaticInner ex1 = new StaticInner(outer1);
			ex1.staticInnerMethod();
		}
	}
	
	
	public static void main(String[] args) {
		Outer outer = new Outer();
   //	outer.useInnerMethod();
		outer.useInnerStaticMethod();
	}
	
}
