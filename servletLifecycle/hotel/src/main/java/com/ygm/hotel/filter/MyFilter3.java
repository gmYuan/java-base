package com.ygm.hotel.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import java.io.IOException;

public class MyFilter3 extends HttpFilter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		System.out.println("MyFilter3- before");
		chain.doFilter(req, res);
		System.out.println("MyFilter3- after");
	}
}
