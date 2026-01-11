package com.ygm.hotel;

import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class WebContainerTest {

	private List<ServletConfiguration> buildConfigurations() {
		ServletConfiguration configuration1 = new ServletConfiguration(
				"/queryRoom",
				"com.ygm.hotel.QueryRoomServlet",
				0
		);
		ServletConfiguration configuration2 = new ServletConfiguration(
				"/bookRoom",
				"com.ygm.hotel.BookRoomServlet",
				12
		);
		ServletConfiguration configuration3 = new ServletConfiguration(
				"/closeRoom",
				"com.ygm.hotel.CloseRoomServlet",
				null
		);

		List<ServletConfiguration> configurations = new ArrayList<>();
		configurations.add(configuration1);
		configurations.add(configuration2);
		configurations.add(configuration3);
		return configurations;
	}

	@Test
	public void testWebContainer() throws ServletException, IOException {
		// 1.获取配置信息
		List<ServletConfiguration> configurations = buildConfigurations();
		// 2.创建web容器
		WebContainer webContainer = new WebContainer();
		// 3. 启动web容器
		webContainer.start(configurations);
		// 4. 执行请求
		webContainer.doService("/queryRoom", null, null);
		webContainer.doService("/bookRoom", null, null);
		webContainer.doService("/closeRoom", null, null);
		// 5. 关闭web容器
		webContainer.close();
	}

}