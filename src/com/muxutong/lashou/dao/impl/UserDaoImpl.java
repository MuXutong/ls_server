package com.muxutong.lashou.dao.impl;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.muxutong.lashou.dao.UserDao;
import com.muxutong.lashou.enity.User;




public class UserDaoImpl extends BaseDao implements UserDao {

	/**
	 * �û�ע��
	 */
	@Override
	public User register(String name, String password) {
		// �������ݿ�����
				Connection connection=null;
				// ����sql���ִ�еĶ���
				Statement statement=null;
				// �����ѯ���صĽ������
				ResultSet resultSet=null;
				User user=null;
				try {
					connection=getConn();//�������
					statement=connection.createStatement();//�õ�sql����ִ�ж���
					String sqlForCheck="select * from user where user_name ='"+name+"'";
					System.out.println(sqlForCheck);
					resultSet=statement.executeQuery(sqlForCheck);//�鿴���û��Ƿ��Ѵ���
					if (resultSet.next()) {//�û����Ѵ��ڣ�����null��ע��
						System.out.println("�û��Ѵ���");
						return null;
					}else{
						String sql="insert into user(user_name,user_login_pwd) values('"+name+"','"+password+"')";
						System.out.println(sql);
						statement.execute(sql);//��������
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
	*�û���¼
	*/
	@Override
	public User login(String name, String password) {
		// �������ݿ�����
		Connection connection=null;
		// ����sql���ִ�еĶ���
		Statement statement=null;
		// �����ѯ���صĽ������
		ResultSet resultSet=null;
		User user=null;
		try {
			connection=getConn();//�������
			statement=connection.createStatement();//�õ�sql����ִ�ж���
			String sqlForCheck="select * from user where user_name ='"+name+"' or user_tel='"+name+"' or user_email='"+name+"'";
			System.out.println(sqlForCheck);
			resultSet=statement.executeQuery(sqlForCheck);//�鿴���û��Ƿ��Ѵ���
			while(resultSet.next()) {//�û������ڣ�
				System.out.println("�û��Ѵ���");
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
	 *ʹ�õ�����
	 */
	@Override
	public User socialLogin(String name, String pwd) {
		// �������ݿ�����
		Connection conn=null;
		// ����sql���ִ�еĶ���
		Statement statement=null;
		// �����ѯ���صĽ������
		ResultSet resultSet=null;
		User user=null;
		try {
			conn=getConn();//�������
			statement=conn.createStatement();//�õ�sql����ִ�ж���
			String sqlForCheck="select * from user where user_name ='"+name+"' or user_tel='"+name+"' or user_email='"+name+"'";
			System.out.println(sqlForCheck);
			resultSet=statement.executeQuery(sqlForCheck);//�鿴���û��Ƿ��Ѵ���
			if(resultSet.next()) {//�û������ڣ�
					System.out.println("�û��Ѵ���");
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
				statement.execute(sql);//��������
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
