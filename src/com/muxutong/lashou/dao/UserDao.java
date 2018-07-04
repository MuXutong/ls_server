package com.muxutong.lashou.dao;

import com.muxutong.lashou.enity.User;

public interface UserDao {
	/**
	 * 用户注册
	 */
	public User register(String name,String password);
	/**
	 * 用户登录
	 */
	public User login(String name,String password);
	/**
	 * 使用第三方登录
	 */
	public User socialLogin(String name,String pwd);
}
