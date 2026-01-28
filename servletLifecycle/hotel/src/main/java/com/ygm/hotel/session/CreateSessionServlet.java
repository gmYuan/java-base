package com.ygm.hotel.session;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateSessionServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		HttpSession session =req.getSession(true);
		Object obj = session.getAttribute("count");
		// 设置session的过期时间，单位是秒
		session.setMaxInactiveInterval(10);

		// 强制session无效，一般情况下不推荐使用
		// session.invalidate();

		// 如果count属性不存在，则初始化count属性为1，表示第 1 次访问
		if (obj == null) {
			session.setAttribute("count", 1);
		} else {
			// 如果count属性已经存在，则将count属性值 + 1
			int count = (int) obj;
			session.setAttribute("count", count + 1);
		}
	}
}
