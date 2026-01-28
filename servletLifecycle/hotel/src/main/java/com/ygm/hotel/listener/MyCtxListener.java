package com.ygm.hotel.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class MyCtxListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("MyCtxListener start" + sce);
		ServletContext servletCtx = sce.getServletContext();
		servletCtx.setAttribute("onlineCount", 0);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("MyCtxListener destroyed--" + sce);
	}
}
