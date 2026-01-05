package com.ygm.hotel;


import javax.servlet.*;
import java.io.IOException;

public class BookRoomServlet implements Servlet {

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		System.out.println("bookRoomServlet 的 init方法被调用了啊啊啊");
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		System.out.println("bookRoomServlet service is called 了");
	}

	@Override
	public String getServletInfo() {
		return "";
	}

	@Override
	public void destroy() {
		System.out.println("bookRoomServlet 的 destroy方法被调用");
	}

	public static void main(String[] args) {

	}
}