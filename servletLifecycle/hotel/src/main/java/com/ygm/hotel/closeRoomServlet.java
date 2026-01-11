package com.ygm.hotel;


import javax.servlet.*;
import java.io.IOException;

public class CloseRoomServlet implements Servlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		System.out.println("closeRoom 的 init方法被调用了");
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		// 在页面中输出 Hello World
		System.out.println("closeRoom service is called" + this);

	}

	@Override
	public String getServletInfo() {
		return "";
	}

	@Override
	public void destroy() {
		System.out.println("closeRoom 的 destroy方法被调用了");
	}

	public static void main(String[] args) {

	}
}