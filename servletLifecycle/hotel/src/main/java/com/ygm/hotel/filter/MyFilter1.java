package com.ygm.hotel.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

@WebFilter(filterName = "myFilter1", urlPatterns = "/session/*")
public class MyFilter1 extends HttpFilter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("MyFilter1- init执行了");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFilter1- before");
		chain.doFilter(req, res);
		System.out.println("MyFilter1- after");
	}

	@Override
	public void destroy() {
		System.out.println("MyFilter1- destroy执行了");
	}
}
