package com.muxutong.lashou.dao.impl;

import java.sql.*;

import java.util.ResourceBundle;

import com.muxutong.lashou.dao.Dao;


public class BaseDao {
	
	private static String url = null;
	private static String user = null;
	private static String password = null;
	private static String driver = null;
	static {
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("com.muxutong.lashou.config");
			driver = bundle.getString("driver");
			url = bundle.getString("url");
			user = bundle.getString("username");
			password = bundle.getString("password");
			
			Class.forName(driver);
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	protected Connection getConn() throws Exception{
		return new Inner().getConn();
		
	}
	
	protected void close(ResultSet res, Statement stm, Connection conn) {
		
		new Inner().close(res, stm, conn);
	}
	
	private class Inner implements Dao{
		
		public Connection getConn() throws Exception {
			return DriverManager.getConnection(url, user, password);
			
		}

		@Override
		public void close(ResultSet res, Statement stm, Connection conn) {
			// TODO Auto-generated method stub
			if(res!=null) {
				try {
					res.close();
					res =null;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			if(stm!=null) {
				try {
					stm.close();
					stm =null;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			if(conn!=null) {
				try {
					conn.close();
					conn =null;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		
	}
}
