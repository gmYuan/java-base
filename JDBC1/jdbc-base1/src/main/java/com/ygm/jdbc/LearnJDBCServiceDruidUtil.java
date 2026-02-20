package com.ygm.jdbc;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;

// L6-2
public class LearnJDBCServiceDruidUtil {
	public static Connection getConnection() throws Exception {
		HashMap<String, Object> configMap = new HashMap<>();
		configMap.put("driverClassName", "com.mysql.cj.jdbc.Driver");
		configMap.put("url", "jdbc:mysql://localhost:3306/school_dabtabase");
		configMap.put("username", "root");
		configMap.put("password", "rootroot");
		configMap.put("initialSize", "5");
		configMap.put("maxActive", "10");
		configMap.put("maxWait", "1000");
		DataSource dataSource = DruidDataSourceFactory.createDataSource(configMap);
		return dataSource.getConnection();
	}
}