package com.ygm.hotel;


import javax.servlet.*;
import java.io.IOException;

public class QueryRoomServlet implements Servlet {

//	public QueryRoomServlet() {
//		System.out.println("QueryRoom 构造函数被执行了");
//	}

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		System.out.println("queryRoom 的 init方法被调用了");
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		// 在页面中输出 Hello World
		System.out.println("QueryRoomServlet service is called" + this);

	}

	@Override
	public String getServletInfo() {
		return "";
	}

	@Override
	public void destroy() {
		System.out.println("queryRoom 的 destroy方法被调用了");
	}

	public static void main(String[] args) {

	}
}