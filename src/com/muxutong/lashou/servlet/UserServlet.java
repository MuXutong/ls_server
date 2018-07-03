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
		// ������Ӧ���ݵĸ�ʽ�ͱ��룬֪ͨ��������ݵ���ʾ��ʽ
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String name=request.getParameter("name");
		String password=request.getParameter("password");
		// ��ȡISO8859-1��ԭʼ����
		//�ڲ������ֽ�������ʵ�����ַ�����utf-8�������ֽ� ����Ӣ�Ŀ��Ա�ʾ�ģ�ֻ��       ������������������������������������������������������������������������
	     //ISO8859-1��ʾ������һ���ֽ�һ���ֽ���ʾ����utf-8�Լ����ֽ�һ���ʾ
	     byte [] bs = name.getBytes("ISO8859-1");
		 name = new String(bs,"UTF-8");
		String flag=request.getParameter("flag");//�����жϲ�������Ϊ
		ResponseObject result=null;
		
		if("register".equals(flag)){//�û�ע��
			System.out.println("name---->>"+name+"password---->>"+password);
			UserDao userDao=new UserDaoImpl();
			if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(password)) {
				User user=userDao.register(name, password);
				if (user!=null) {
					result=new ResponseObject("ע��ɹ���", 1, user);
				}else{
					result=new ResponseObject(0, "�û����Ѵ��ڣ���");
				}
			}else {
				result = new ResponseObject(0, "�û��������벻��Ϊ�գ�");
			}
		}else if("accout_login".equals(flag)){
			UserDao userDao=new UserDaoImpl();
			User user=userDao.login(name, password);
			if (user!=null) {
				if(password.equals(user.getLogPwd())){
					result=new ResponseObject("��¼�ɹ���", 1, user);
				}else{
					result=new ResponseObject( 0,"�û������������");
				}
				
			}else{
				result=new ResponseObject(0,"���û������ڣ�");
			}
			
		}else if("phone_login".equals(flag)){
			if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(password)){
			UserDao userDao=new UserDaoImpl();
			User user=userDao.login(name, password);
			if (user!=null) {
					result=new ResponseObject("��¼�ɹ���", 1, user);
				}else{
					result=new ResponseObject(0,"��¼ʧ�ܣ������ԣ�");
				}
				
			}
			
		}else if("social_login".equals(flag)){
			if(StringUtils.isNotBlank(name)&&StringUtils.isNotBlank(password)){
			UserDao userDao=new UserDaoImpl();
			User user=userDao.socialLogin(name, password);
			if (user!=null) {
					result=new ResponseObject("��Ȩ�ɹ���", 1, user);
				}else{
					result=new ResponseObject(0,"��Ȩʧ�ܣ������ԣ�");
				}
				
			}else{
				result=new ResponseObject(0,"�û��������벻��Ϊ�գ�");
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
