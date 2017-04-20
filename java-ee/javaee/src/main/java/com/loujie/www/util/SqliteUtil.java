package com.loujie.www.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SqliteUtil {

	public static Map<String, Object> query(String basename) {
		Map<String, Object> resultMap = new HashMap<>();
		// 1.加载驱动
		// 2.获取连接
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:D:\\2017sqllit\\transFile_database.db-20170419", null,
					null);
			String selectSQL = "select basename,box_1080p_filename,ultra_resolution_filename,high_resolution_filename,standard_resolution_filename from trans_table where basename = ?";
			PreparedStatement ps = conn.prepareStatement(selectSQL);
			ps.setString(1, basename);
			rs = ps.executeQuery();
			while (rs.next()) {
				resultMap.put("fhd", rs.getString("box_1080p_filename"));
				resultMap.put("hd", rs.getString("ultra_resolution_filename"));
				resultMap.put("sd", rs.getString("high_resolution_filename"));
				resultMap.put("ld", rs.getString("standard_resolution_filename"));
				resultMap.put("name", rs.getString("basename"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return resultMap;
	}

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
