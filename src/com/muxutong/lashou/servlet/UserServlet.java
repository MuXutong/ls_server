package com.muxutong.lashou.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.google.gson.GsonBuilder;
import com.muxutong.lashou.dao.UserDao;
import com.muxutong.lashou.dao.impl.UserDaoImpl;
import com.muxutong.lashou.enity.ResponseObject;
import com.muxutong.lashou.enity.User;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 处理响应数据的格式和编码，通知浏览器数据的显示方式
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String name=request.getParameter("name");
		String password=request.getParameter("password");
		// 获取ISO8859-1的原始数据
		//内部编码字节流，其实中文字符经过utf-8编码后的字节 都是英文可以表示的，只是       　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
	     //ISO8859-1显示数据以一个字节一个字节显示，而utf-8以几个字节一起表示
	     byte [] bs = name.getBytes("ISO8859-1");
		 name = new String(bs,"UTF-8");
		String flag=request.getParameter("flag");//用于判断操作的行为
		ResponseObject result=null;
		
		if("register".equals(flag)){//用户注册
			System.out.println("name---->>"+name+"password---->>"+password);
			UserDao userDao=new UserDaoImpl();
			if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(password)) {
				User user=userDao.register(name, password);
				if (user!=null) {
					result=new ResponseObject("注册成功！", 1, user);
				}else{
					result=new ResponseObject(0, "用户名已存在！！");
				}
			}else {
				result = new ResponseObject(0, "用户名和密码不能为空！");
			}
		}else if("accout_login".equals(flag)){
			UserDao userDao=new UserDaoImpl();
			User user=userDao.login(name, password);
			if (user!=null) {
				if(password.equals(user.getLogPwd())){
					result=new ResponseObject("登录成功！", 1, user);
				}else{
					result=new ResponseObject( 0,"用户名或密码错误！");
				}
				
			}else{
				result=new ResponseObject(0,"该用户不存在！");
			}
			
		}else if("phone_login".equals(flag)){
			if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(password)){
			UserDao userDao=new UserDaoImpl();
			User user=userDao.login(name, password);
			if (user!=null) {
					result=new ResponseObject("登录成功！", 1, user);
				}else{
					result=new ResponseObject(0,"登录失败，请重试！");
				}
				
			}
			
		}else if("social_login".equals(flag)){
			if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(password)){
			UserDao userDao=new UserDaoImpl();
			User user=userDao.socialLogin(name, password);
			if (user!=null) {
					result=new ResponseObject("授权成功！", 1, user);
				}else{
					result=new ResponseObject(0,"授权失败，请重试！");
				}
				
			}else{
				result=new ResponseObject(0,"用户名和密码不能为空！");
			}
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
