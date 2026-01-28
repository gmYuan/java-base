package com.ygm.hotel.filter;


import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class MyFilterChain implements FilterChain {
	private ArrayList<Filter> filters = new ArrayList<>();
	private int idx = 0;

	public MyFilterChain(ArrayList<Filter> filters, Servlet servlet) {
		// 复制列表，避免修改外部传入的参数
		this.filters = new ArrayList<>(filters);
		this.filters.add(new FilterProxy(servlet));
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res) throws IOException, ServletException {
		Filter filter = filters.get(idx);
		idx++;
		filter.doFilter(req, res,  this);
	}

	private static class FilterProxy extends HttpFilter {
		private Servlet servlet;
		public FilterProxy(Servlet servlet) {
			this.servlet = servlet;
		}
		@Override
		public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
			servlet.service(req, res);
		}
	}

}
