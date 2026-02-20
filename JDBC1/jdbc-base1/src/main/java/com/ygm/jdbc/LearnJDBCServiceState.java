package com.ygm.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// L3
public class LearnJDBCServiceState {
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

		// 执行 修改操作
		String sql1 = "insert into account (name, money) values ('小小', 555)";
		String sql2 = "update account set money = 1234 where id = 1";
		String sql3 = "delete from account where id = 100";
		int count1 = statement.executeUpdate(sql1);
		int count2 = statement.executeUpdate(sql2);
		int count3 = statement.executeUpdate(sql3);
		System.out.println("count1 = " + count1);
		System.out.println("count2 = " + count2);
		System.out.println("count3 = " + count3);
		// 释放连接
		statement.close();
		connection.close();
	}
}