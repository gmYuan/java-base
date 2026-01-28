package com.ygm.hotel;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "queryOnlineCountServlet", urlPatterns = "/queryOnlineCount")
public class QueryOnlineCountServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		ServletContext servletCtx = req.getServletContext();
		int onlineCount = (int) servletCtx.getAttribute("onlineCount");
		res.getWriter().write("online count = " + onlineCount);
	}
}
