package com.ygm;

import java.sql.SQLException;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
	public static void entry() {
		processData();
	}

	public static void processData() {
		int userId = 1;
		try {
			System.out.println("我是 processData");
			InsertIntoDatabase();
		} catch (SQLException e) {
			throw new UserHasExistException("插入id为" + userId + "的用户数据失败", e);
		}
	}

	public static class UserHasExistException extends RuntimeException {
		public UserHasExistException(String message,  Throwable cause) {
			super(message, cause);
		}
	}

	public static void InsertIntoDatabase() throws SQLException {
		System.out.println("插入数据到 数据库里");
		throw new SQLException("有重复的键值");
	}


	public static void main(String[] args) {
		entry();
	}
}