package com.ygm.hotel.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// V2 使用 HttpServlet
public class UseCookieServlet extends HttpServlet {

	// 重写 doGet 方法
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    Cookie[] cookies = req.getCookies();
		for (Cookie cookie : cookies) {
			System.out.println(cookie.getName() + ":" + cookie.getValue());
		}

		// 1.1 响应
		res.getWriter().write("use Cookie OK !");
	}


}