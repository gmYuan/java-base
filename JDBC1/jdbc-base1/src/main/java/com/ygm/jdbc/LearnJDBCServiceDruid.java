package com.ygm.jdbc;

import java.sql.*;

// L6-1
public class LearnJDBCServiceDruid {
	public static void main(String[] args) throws Exception {
		// 1 注册驱动 + 连接数据池
		Connection connection = LearnJDBCServiceDruidUtil.getConnection();

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