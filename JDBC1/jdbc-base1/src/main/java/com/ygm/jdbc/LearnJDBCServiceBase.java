package com.ygm.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// L1
public class LearnJDBCServiceBase {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 1 注册驱动
		Class.forName("com.mysql.cj.jdbc.Driver");
		// 2 获取连接
		String url = "jdbc:mysql://localhost:3306/school_dabtabase";
		String user = "root";
		String pwd = "rootroot";
		Connection connection = DriverManager.getConnection(url, user, pwd);
		// 定义 SQL 语句
		String sql = "update account set money = 3200 where id = 1;";
		// 执行 SQL 语句
		Statement statement = connection.createStatement();
		int count = statement.executeUpdate(sql);
		System.out.println("更新数据" + count + "条");
		// 释放连接
		statement.close();
		connection.close();
	}
}