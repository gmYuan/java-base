package com.ygm.hotel.listener;

import javax.servlet.ServletContext;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		ServletContext servletCtx = se.getSession().getServletContext();
		int onlineCount = (int)servletCtx.getAttribute("onlineCount");
		servletCtx.setAttribute("onlineCount", onlineCount + 1);
	}

	@Override
	 public void sessionDestroyed(HttpSessionEvent se) {
		ServletContext servletCtx = se.getSession().getServletContext();
		int onlineCount = (int)servletCtx.getAttribute("onlineCount");
		servletCtx.setAttribute("onlineCount", onlineCount - 1);
	}


}
