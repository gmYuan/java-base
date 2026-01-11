package com.ygm.hotel;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

public abstract class MyGenericServlet implements Servlet {
	ServletConfig config;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		this.config = servletConfig;
		this.init();
	}

	public void init() {}

	@Override
	public ServletConfig getServletConfig() {
		return this.config;
	}

	@Override
	public abstract void service(ServletRequest request, ServletResponse response) throws ServletException, IOException;

	@Override
	public String getServletInfo() {
		return "";
	}

	@Override
	public void destroy() {

	}
}