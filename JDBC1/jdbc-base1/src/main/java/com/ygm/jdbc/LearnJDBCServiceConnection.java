package com.ygm.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// L2
public class LearnJDBCServiceConnection {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// 1 注册驱动
		Class.forName("com.mysql.cj.jdbc.Driver");
		// 2 获取连接
		String url = "jdbc:mysql://localhost:3306/school_dabtabase";
		String user = "root";
		String pwd = "rootroot";
		Connection connection = DriverManager.getConnection(url, user, pwd);
		// 这里也可以使用 connection.prepareStatement()
    // 预编译 SQL的执行 SQL对象（防止 SQL 注入
		Statement statement = connection.createStatement();
		// 执行事务
		try {
			connection.setAutoCommit(false);
			String sql1 = "update account set money = 1111 where id = 1;";
			String sql2 = "update account set money = 2222 where id = 2;";
			statement.executeUpdate(sql1);
			statement.executeUpdate(sql2);
			connection.commit();
			System.out.println("执行成功");
		} catch (Exception e) {
			// 修复：打印异常信息，方便调试
			System.out.println("执行失败，回滚事务");
			e.printStackTrace();
			connection.rollback();
		}

		// 释放连接
		statement.close();
		connection.close();
	}
}