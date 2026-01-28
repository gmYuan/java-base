package com.ygm.hotel.filter;

import com.ygm.hotel.MyServletDemo;
import org.junit.Test;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MyFilterChainTest {
	@Test
	public void test_no_filter() throws ServletException, IOException {
		Servlet servlet = new MyServletDemo();
		FilterChain filterChain = new MyFilterChain(null, servlet);
		filterChain.doFilter(null, null);
	}

	@Test
	public void test_many_filter() throws ServletException, IOException {
		Filter filter1 = new MyFilter1();
		Filter filter2 = new MyFilter2();
		Filter filter3 = new MyFilter3();
		ArrayList<Filter> filters = new ArrayList();
		filters.add(filter3);
		filters.add(filter2);
		filters.add(filter1);

		Servlet servlet = new MyServletDemo();
		FilterChain filterChain = new MyFilterChain(filters, servlet);
		filterChain.doFilter(null, null);

	}

}