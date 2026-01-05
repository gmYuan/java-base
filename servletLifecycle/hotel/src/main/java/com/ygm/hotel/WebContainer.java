package com.ygm.hotel;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class WebContainer {
	public HashMap<String, String> urlPatternToServletClass = new HashMap<>();
	public HashMap<String, Servlet> urlPatternToServlet = new HashMap<>();


	/**
	 * 启动 web 容器
	 */
	public void start(List<ServletConfiguration> configurations) {
		// 用于后续排序 需要构建的 Servlet 对象列表
		List<ServletConfiguration> servletNeedToLoad = new ArrayList<>();
		// 1 构建 urlPatternToServletClass关系
		for (ServletConfiguration configuration : configurations) {
			String urlPattern = configuration.getUrlPattern();
			String servletClass = configuration.getServletClass();
			Integer loadOnStartup = configuration.getLoadOnStartup();
			urlPatternToServletClass.put(urlPattern, servletClass);
			// 2.1 构建 servletNeedToLoad
			if (loadOnStartup != null && loadOnStartup >= 0) {
				servletNeedToLoad.add(configuration);
			}
		}
		// 2.2 升序排序 servletNeedToLoad
		servletNeedToLoad.sort((Comparator.comparing(v-> v.getLoadOnStartup())));

		// 3 构建 urlPatternToServlet关系 + 创建 Servlet实例对象
		 for (ServletConfiguration configuration : servletNeedToLoad) {
			 String urlPattern = configuration.getUrlPattern();
			 String servletClass = configuration.getServletClass();
			 createServlet(urlPattern, servletClass);
		 }
	}

	private Servlet createServlet(String urlPattern, String servletClass)  {
		try {
			Class<?> clazz = Class.forName(servletClass);
			Servlet servlet = (Servlet) clazz.newInstance();
			// 3.2 需要调用servlet的 init() 钩子方法
			servlet.init(null);
			// 3.3
			urlPatternToServlet.put(urlPattern, servlet);
			return servlet;
		} catch (ClassNotFoundException | ServletException | InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}


	/**
	 * 执行请求
	 */
	public void doService(String urlPattern, ServletRequest request, ServletResponse response) throws ServletException, IOException {
		//  1 如果已经存在缓存的 servlet，则使用缓存的 servlet
		 if (urlPatternToServlet.containsKey(urlPattern)) {
			 Servlet servlet = urlPatternToServlet.get(urlPattern);
			 servlet.service(request, response);
			 return;
		 }

		 // 2 如果不存在缓存的 servlet，则从 urlPatternToServletClass 中尝试获取 servletClass
		 String servletClass = urlPatternToServletClass.get(urlPattern);
		 if (servletClass != null) {
			 Servlet servlet = createServlet(urlPattern, servletClass);
			 servlet.service(request, response);
			 return;
		 }

		 // 3 如果 neither exists, then error
		System.out.println("请求路径不存在，无法处理请求");
	}

	/**
	 * 关闭 web 容器
	 */
	public void close() {
		for (Servlet servlet : urlPatternToServlet.values()) {
			servlet.destroy();
		}
	}


}