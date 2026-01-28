package com.ygm.hotel.filter;


import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;

public class MyFilterChain1 implements FilterChain {
	private ArrayList<Filter> filters;
	private Servlet servlet;
	private int idx;

	public MyFilterChain1(ArrayList<Filter> filters, Servlet servlet) {
		this.filters = filters;
		this.servlet = servlet;
		if (filters ==  null || filters.size() == 0) {
			idx = -1;
		} else {
			idx = 0;
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res) throws IOException, ServletException {
		// 不存在 filters 或者 所有 filters 都执行完毕了，则调用 servlet
		if (idx == -1 || idx >= filters.size()) {
			servlet.service(req, res);
		} else {
			Filter filter = filters.get(idx);
			idx++;
			filter.doFilter(req, res,  this);
		}
	}
}
