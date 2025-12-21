package web.Servlet;

import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;
import java.util.HashMap;

/**
 * 模拟tomcat服务器, web服务器开发团队
 */


public class TomcatService{
	private static final HashMap<String, Servlet> servletMap = new HashMap<>();

	public static void main(String[] args) {
		// 接收用户请求，根据请求路径调用对应的Servlet 来完成业务逻辑
		while (true) {
			// 1 接收用户请求路径
			String url = getUserInputUrl();
			// 2 根据请求路径找到对应的 Servlet
			Servlet servlet = getServlet(url);
			// 3 调用 Servlet，执行业务逻辑
			if (servlet == null) {
				System.out.println("根据url " + url + " 没有找到对应的 Servlet");
				continue;
			}
			servlet.service();
		}
	}

	public static String getUserInputUrl() {
		// 通过读取命令行参数，模拟 获取用户输入的请求路径
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入请求路径：");
		// 返回的是Url字符串，例如：/orderRoom
		return scanner.nextLine();
	}


	public static Servlet getServlet(String url) {
		// ✅ 创建配置文件： 见 web.properties
		try {
			// 从servletMap 中根据url 获取对应的 Servlet 实例
			if (servletMap.containsKey(url)) {
				System.out.println("从缓存中获取到 Servlet 实例：" + servletMap.get(url));
				return servletMap.get(url);
			}

			// 解析配置文件
			// FileReader 读取配置文件，需要传入绝对路径
			FileReader reader = new FileReader("/Users/jiayin/Documents/learn-day/java-base/src/web/Servlet/web.properties");
			Properties properties = new Properties();
			properties.load(reader);
			reader.close();
			// 根据url 从配置文件中获取对应的 Servlet 类名
			String className = properties.getProperty(url);
			System.out.println("根据url " + url + " 从配置文件中获取到的 Servlet 类名：" + className);
			if (className == null) {
				return null;
			}
			// 反射创建 Servlet 实例
			Class<?> clazz = Class.forName(className);
			Object obj = clazz.newInstance();
			System.out.println("根据url " + url + " 从配置文件中获取到的 Servlet 实例：" + obj);
			// 缓存 Servlet 实例
			servletMap.put(url, (Servlet) obj);
			return (Servlet) obj;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
