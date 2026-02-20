package com.ygm.jdbc;

import java.sql.*;

// L4
public class LearnJDBCServiceResult {
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

		// 执行 查询操作，通过 next 处理结果集
		String sql = "select id, money, name from account where id in (1,2) order by id desc ";
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			int id = resultSet.getInt(1);
			String name = resultSet.getString(3);
			int money = resultSet.getInt(2);
			AccountInfo account = new AccountInfo();
			account.setId(id);
			account.setName(name);
			account.setMoney(money);
			System.out.println(account);
		}

		// 释放连接
		statement.close();
		connection.close();
	}

	private static class AccountInfo {
		 private Integer id;
		 private String name;
		 private Integer money;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getMoney() {
			return money;
		}

		public void setMoney(Integer money) {
			this.money = money;
		}

		@Override
		public String toString() {
			return "AccountInfo{" +
					"id=" + id +
					", name='" + name + '\'' +
					", money=" + money +
					'}';
		}
	}
}