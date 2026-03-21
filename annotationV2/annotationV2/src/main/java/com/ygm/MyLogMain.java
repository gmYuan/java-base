package com.ygm;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class MyLogMain {
	public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
		MyLogService service = enhanceByAnnotation();
		service.queryDataBase( 123);
		service.provideHttpRes("abc");
	}

	// 把 MyLogService 中所有的 带@Log注解的方法 都过滤出来
	static List<String> MethodsWithLog = Stream.of(MyLogService.class.getDeclaredMethods())
			.filter(MyLogMain::isAnnotatedWithLog)
			.map(Method::getName)
			.collect(Collectors.toList());

	private static boolean isAnnotatedWithLog(Method method) {
		// 根据「注解的 Class」从该方法上 取注解实例；
		// 若该方法没有该注解则返回 null
		return method.getAnnotation(Log.class) != null;
	}


	private static MyLogService enhanceByAnnotation() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		// 创建一个 ByteBuddy 构建器，用来在内存里生成新类
		return new ByteBuddy()
				// 声明：新类是 MyLogService 的子类
				.subclass(MyLogService.class)
				// 匹配要增强的方法
				.method((method) -> MethodsWithLog.contains(method.getName()))
				// 表示：被拦截的方法调用，要委托给哪个类；这里委托给 LoggerInterceptor。
				// 参数需要的是「类的 Class 对象」，所以必须传 LoggerInterceptor.class
	    	.intercept(MethodDelegation.to(LoggerInterceptor.class))
				// 生成子类的字节码
				.make()
				// 用当前类的 ClassLoader 把新类加载进 JVM，得到 Class<?>
				.load(MyLogMain.class.getClassLoader())
				// 从加载结果里取出生成的 Class（即 MyLogService 的增强子类）
				.getLoaded()
				// 取该类的无参构造器
				.getConstructor()
				// 用无参构造器创建一个实例
				.newInstance();
	}


	public static class LoggerInterceptor {
		public static void log(@Origin Method method, @SuperCall Callable<Void> zuper) {
			Log logAnnotation = method.getAnnotation(Log.class);
			String value = logAnnotation != null ? logAnnotation.value() : "";
			System.out.println("----log start [" + value + "]");
			try {
				zuper.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("log end [" + value + "]----");
		}
	}

}
