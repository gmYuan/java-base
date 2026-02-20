package com.ygm.jdbc;


import javax.xml.transform.Result;
import java.sql.*;

// L5
public class LearnJDBCServicePrepare {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 1 注册驱动
		Class.forName("com.mysql.cj.jdbc.Driver");
		// 2 获取连接
		String url = "jdbc:mysql://localhost:3306/school_dabtabase";
		String user = "root";
		String pwd = "rootroot";
		Connection connection = DriverManager.getConnection(url, user, pwd);

    // connection.createStatement 会有 SQL 注入漏洞
		int userId = 1;
		String userPwd = "' or 1 = 1";
		// 原有示例
		// String userPwd = "' or '1' = '1";
		// String sql = "select * from user_pwd where user_id = " + userId + " and user_passd = '" + userPwd + "'";

		// 存在SQL注入漏洞
		// String sql = "select * from user_pwd where user_id = " + userId + " and user_passd = '" + userPwd;
		// Statement statement = connection.createStatement();
		// ResultSet resultSet = statement.executeQuery(sql);

		// 使用 PrepareStatement 预防SQL注入漏洞
		String sql = "select * from user_pwd where user_id = ? and user_passd = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, userId);
		statement.setString(2, userPwd);
		ResultSet resultSet = statement.executeQuery();

		System.out.println(sql);
		if (resultSet.next()) {
			System.out.println("用户名密码验证通过，登录成功");
		} else {
			System.out.println("用户名密码验证失败，登录失败");
		}

		// 释放连接
		statement.close();
		connection.close();
	}
}