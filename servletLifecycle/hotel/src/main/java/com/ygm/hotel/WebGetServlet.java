package com.ygm.hotel;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// V1 使用自己封装的 MyGenericServlet
//public class WebPostServlet extends MyGenericServlet {
//
//	@Override
//	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
//		System.out.println("WebPostServlet的 service is called");
//		// 获取请求参数
//		String name = request.getParameter("username");
//		String pwd = request.getParameter("password");
//		System.out.println("name: " + name);
//		System.out.println("pwd: " + pwd);
//		// 1.1 返回响应
//		 response.getWriter().write("hello, " + name);
//		// 1.2 服务器端报错，默认返回 500
//		// throw new RuntimeException("服务器内部错误");
//
//	}
//}


// V2 使用 HttpServlet
public class WebGetServlet extends HttpServlet {
	// 重写 doPost 方法
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println("WebPostServlet的 doGet is called, req is:" + req);
		// 获取请求参数
		String name = req.getParameter("username");
		String pwd = req.getParameter("password");
		System.out.println("name: " + name);
		System.out.println("pwd: " + pwd);
		// 1.1 响应
		res.getWriter().write("Get OK !");
	}

}