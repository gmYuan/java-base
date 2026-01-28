package com.ygm.hotel.session;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class QuerySessionServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		HttpSession session =req.getSession(false);
		System.out.println("QuerySessionServlet的 doGet 执行了");
		if (session == null) {
			// session 不存在，访问次数是 0
			System.out.println("session is null, query count = 0");
			// count 属性不存在，访问次数是 0
			res.getWriter().write("session is null, query count = 0");
		} else {
			Object obj = session.getAttribute("count");
			if (obj == null) {
				// count 属性不存在，访问次数是 0
				res.getWriter().write("query count = 0");
			} else {
				int count = (int) obj;
				res.getWriter().write("query count = " +  count);
			}
		}
	}

}
