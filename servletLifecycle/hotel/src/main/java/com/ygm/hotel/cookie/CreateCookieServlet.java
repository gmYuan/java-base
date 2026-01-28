package com.ygm.hotel.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// V2 使用 HttpServlet
public class CreateCookieServlet extends HttpServlet {
	// 重写 doGet 方法
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		Cookie cookie1 = new Cookie("username", "wudi");
		Cookie cookie2 = new Cookie("age", "28");
		// 设置 path
		String path = req.getContextPath();
		System.out.println("path是：" + path);
		cookie1.setPath(path);
		// 设置 maxAge
		cookie1.setMaxAge(100);
		cookie2.setMaxAge(-2);
		// 设置 HttpOnly
		cookie1.setHttpOnly(true);

		res.addCookie(cookie1);
		res.addCookie(cookie2);
		// 1.1 响应
		res.getWriter().write("Create Cookie OK !");
	}


}