package com.muxutong.lashou.dao.impl;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.muxutong.lashou.dao.UserDao;
import com.muxutong.lashou.enity.User;




public class UserDaoImpl extends BaseDao implements UserDao {

	/**
	 * 用户注册
	 */
	@Override
	public User register(String name, String password) {
		// 定义数据库链接
				Connection connection=null;
				// 定义sql语句执行的对象
				Statement statement=null;
				// 定义查询返回的结果集合
				ResultSet resultSet=null;
				User user=null;
				try {
					connection=getConn();//获得连接
					statement=connection.createStatement();//得到sql语句的执行对象
					String sqlForCheck="select * from user where user_name ='"+name+"'";
					System.out.println(sqlForCheck);
					resultSet=statement.executeQuery(sqlForCheck);//查看该用户是否已存在
					if (resultSet.next()) {//用户名已存在，返回null不注册
						System.out.println("用户已存在");
						return null;
					}else{
						String sql="insert into user(user_name,user_login_pwd) values('"+name+"','"+password+"')";
						System.out.println(sql);
						statement.execute(sql);//插入数据
						resultSet=statement.executeQuery(sqlForCheck);
						while(resultSet.next()){
							user=new User();
							user.setId(resultSet.getString("user_id"));
							user.setName(resultSet.getString("user_name"));
							user.setLogPwd(resultSet.getString("user_login_pwd"));
							user.setPayPwd(resultSet.getString("user_pay_pwd"));
							user.setTel(resultSet.getString("user_tel"));
							user.setEmail(resultSet.getString("user_email"));
							user.setCount_money(resultSet.getString("user_money"));
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}finally{
					
						close(resultSet, statement, connection);
					
				}
				return user;
	}
	/**
	*用户登录
	*/
	@Override
	public User login(String name, String password) {
		// 定义数据库链接
		Connection connection=null;
		// 定义sql语句执行的对象
		Statement statement=null;
		// 定义查询返回的结果集合
		ResultSet resultSet=null;
		User user=null;
		try {
			connection=getConn();//获得连接
			statement=connection.createStatement();//得到sql语句的执行对象
			String sqlForCheck="select * from user where user_name ='"+name+"' or user_tel='"+name+"' or user_email='"+name+"'";
			System.out.println(sqlForCheck);
			resultSet=statement.executeQuery(sqlForCheck);//查看该用户是否已存在
			while(resultSet.next()) {//用户名存在，
				System.out.println("用户已存在");
				user=new User();
				user.setId(resultSet.getString("user_id"));
				user.setName(resultSet.getString("user_name"));
				user.setLogPwd(resultSet.getString("user_login_pwd"));
				user.setPayPwd(resultSet.getString("user_pay_pwd"));
				user.setTel(resultSet.getString("user_tel"));
				user.setEmail(resultSet.getString("user_email"));
				user.setCount_money(resultSet.getString("user_money"));
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
				close(resultSet, statement, connection);
			
		}
		return user;
	}
	
	/**
	 *使用第三方
	 */
	@Override
	public User socialLogin(String name, String pwd) {
		// 定义数据库链接
		Connection conn=null;
		// 定义sql语句执行的对象
		Statement statement=null;
		// 定义查询返回的结果集合
		ResultSet resultSet=null;
		User user=null;
		try {
			conn=getConn();//获得连接
			statement=conn.createStatement();//得到sql语句的执行对象
			String sqlForCheck="select * from user where user_name ='"+name+"' or user_tel='"+name+"' or user_email='"+name+"'";
			System.out.println(sqlForCheck);
			resultSet=statement.executeQuery(sqlForCheck);//查看该用户是否已存在
			if(resultSet.next()) {//用户名存在，
					System.out.println("用户已存在");
					user=new User();
					user.setId(resultSet.getString("user_id"));
					user.setName(resultSet.getString("user_name"));
					user.setLogPwd(resultSet.getString("user_login_pwd"));
					user.setPayPwd(resultSet.getString("user_pay_pwd"));
					user.setTel(resultSet.getString("user_tel"));
					user.setEmail(resultSet.getString("user_email"));
					user.setCount_money(resultSet.getString("user_money"));
					return user;
				}else{
					String sql="insert into user(user_name,user_login_pwd) values('"+name+"','"+pwd+"')";
				System.out.println(sql);
				statement.execute(sql);//插入数据
				resultSet=statement.executeQuery(sqlForCheck);
				while(resultSet.next()){
					user.setId(resultSet.getString("user_id"));
					user.setName(resultSet.getString("user_name"));
					user.setLogPwd(resultSet.getString("user_login_pwd"));
					user.setPayPwd(resultSet.getString("user_pay_pwd"));
					user.setTel(resultSet.getString("user_tel"));
					user.setEmail(resultSet.getString("user_email"));
					user.setCount_money(resultSet.getString("user_money"));
					return user;
				}
				
					
					
				}
				
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				close(resultSet, statement, conn);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}

}
