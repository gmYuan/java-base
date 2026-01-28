package com.ygm.hotel;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
public class WebPostServlet extends HttpServlet {
	// 重写 doPost 方法
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		System.out.println("WebPostServlet的 doPost is called");
		// 获取请求参数
		String name = req.getParameter("username");
		String pwd = req.getParameter("password");
		System.out.println("name: " + name);
		System.out.println("pwd: " + pwd);

		// 获取 json格式的数据
		// 4.1 从 body 里获取 json格式的 数据
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(
				req.getInputStream(), "UTF-8")
		);
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = streamReader.readLine()) != null) {
			builder.append(line);
		}
		System.out.println("json是: " + builder.toString());
		// 4.2 通过 jackson 解析 json数据，转化为 Java类
		ObjectMapper mapper = new ObjectMapper();
		WebPostData data = mapper.readValue(builder.toString(), WebPostData.class);
		System.out.println("解析的 data类是: " + data);

		// 4.3 还可以把 data类 转化为 json
		String json = mapper.writeValueAsString(data);
		System.out.println("再转化为json是: " + json);

		// 1.1 响应
		 res.getWriter().write("解析Json succeed !");
	}


}