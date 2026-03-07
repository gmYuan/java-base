package com.github.hcsp.exception;

import java.io.File;
import java.sql.*;

public class DatabaseReaderV2 {
    public static void main(String[] args) throws SQLException {
	    System.out.println("user1结果为：" + isCorrectPwd("zhangSan", "pwd123"));
	    // 使用 statement 会存在 sql 注入漏洞
	    System.out.println("user2结果为：" + isCorrectPwd(
					"zhangSan",
			    "pwd12367' or 1 = 1; update users set status = 12 where id = 2; -- ")
	    );
			// 使用 preparedStatement 可以避免 sql注入漏洞
	    System.out.println("user2结果为：" + isCorrectPwdV2(
			    "zhangSan",
			    "pwd12367' or 1 = 1; update users set status = 12 where id = 2; -- ")
	    );
    }

    public static Boolean isCorrectPwd(String name, String pwd) throws SQLException {
	    File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
	    String jdbcUrl = "jdbc:h2:file:" + new File(projectDir, "xdml").getAbsolutePath();
	    // 使用 statement 会存在 sql 注入漏洞
	    try (Connection connection = DriverManager.getConnection(jdbcUrl); Statement statement = connection.createStatement()) {
		    String sql = "select * from USERS where name = '" + name + "' and password = '" + pwd + "'";
		    ResultSet resultSet = statement.executeQuery(sql);
		    return resultSet.next();
	    } catch (Exception e) {
		    e.printStackTrace();
				return false;
	    }
    }

		public static Boolean isCorrectPwdV2(String name, String pwd) throws SQLException {
			File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
			String jdbcUrl = "jdbc:h2:file:" + new File(projectDir, "xdml").getAbsolutePath();
			// 使用 preparedStatement 可以避免 sql 注入漏洞
			try (
					Connection connection = DriverManager.getConnection(jdbcUrl);
					PreparedStatement preparedStatement = connection.prepareStatement(
							"select * from USERS where name = ? and password = ?"
					)
			) {
				preparedStatement.setString(1, name);
				preparedStatement.setString(2, pwd);
				ResultSet resultSet = preparedStatement.executeQuery();
				return resultSet.next();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
}
