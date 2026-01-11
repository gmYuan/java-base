package com.ygm.hotel;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class MyServletDemo extends MyGenericServlet {

	@Override
	public void init() {
		System.out.println("有参数的 初始化内部实现 已经被封装了");
		System.out.println("在暴露的此处编写无参数的 初始化逻辑");
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		System.out.println("MyServletDemo service is called");
		System.out.println("此处编写业务逻辑");
	}
}