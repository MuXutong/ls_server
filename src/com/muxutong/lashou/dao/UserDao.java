package com.muxutong.lashou.dao;

import com.muxutong.lashou.enity.User;

public interface UserDao {
	/**
	 * �û�ע��
	 */
	public User register(String name,String password);
	/**
	 * �û���¼
	 */
	public User login(String name,String password);
	/**
	 * ʹ�õ�������¼
	 */
	public User socialLogin(String name,String pwd);
}
