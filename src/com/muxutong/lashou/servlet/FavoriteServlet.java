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
import com.muxutong.lashou.dao.FavoriteDao;
import com.muxutong.lashou.dao.UserDao;
import com.muxutong.lashou.dao.impl.FavoriteDaoImpl;
import com.muxutong.lashou.dao.impl.UserDaoImpl;
import com.muxutong.lashou.enity.Favorite;
import com.muxutong.lashou.enity.ResponseObject;
import com.muxutong.lashou.enity.User;


@WebServlet("/FavoriteServlet")
public class FavoriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ������Ӧ���ݵĸ�ʽ�ͱ��룬֪ͨ��������ݵ���ʾ��ʽ
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String user_id=request.getParameter("user_id");
		String prodouct_id=request.getParameter("prodouct_id");
		// ��ȡISO8859-1��ԭʼ����
		//�ڲ������ֽ�������ʵ�����ַ�����utf-8�������ֽ� ����Ӣ�Ŀ��Ա�ʾ�ģ�ֻ��       ������������������������������������������������������������������������
		 //ISO8859-1��ʾ������һ���ֽ�һ���ֽ���ʾ����utf-8�Լ����ֽ�һ���ʾ
		byte [] bs = user_id.getBytes("ISO8859-1");
		user_id = new String(bs,"UTF-8");
		
		
		ResponseObject result=null;

		System.out.println("user_id---->>"+user_id+"prodouct_id---->>"+prodouct_id);
		FavoriteDao favoriteDao=new FavoriteDaoImpl();
		if(StringUtils.isNotBlank(user_id)&&StringUtils.isNotBlank(prodouct_id)) {
			Favorite favorite=favoriteDao.add(user_id, prodouct_id);
			if (favorite!=null) {
				result=new ResponseObject("�ղسɹ���", 1, favorite);
			}else{
				result=new ResponseObject(0, "����Ʒ���ղأ���");
			}
		}else {
			result = new ResponseObject(0, "�ղ�ʧ�ܣ�");
		}
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
