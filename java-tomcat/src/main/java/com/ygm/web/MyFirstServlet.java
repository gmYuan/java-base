package com.ygm.web;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;


public class MyFirstServlet implements Servlet {


	@Override
	public void init(ServletConfig servletConfig) throws ServletException {

	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
		PrintWriter out = servletResponse.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("    <meta charset=\"UTF-8\">");
		out.println("    <title>Document</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("    <h1>Hello World!</h1>");
		out.println("    <h2>我是 MyFirstServlet!</h2>");
		out.println("</body>");
		out.println("</html>");
	}

	@Override
	public String getServletInfo() {
		return "";
	}

	@Override
	public void destroy() {

	}
}