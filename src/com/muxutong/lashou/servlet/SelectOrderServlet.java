package com.muxutong.lashou.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;
import com.muxutong.lashou.dao.OrdersDao;
import com.muxutong.lashou.dao.impl.OrdersDaoImpl;
import com.muxutong.lashou.enity.Order;
import com.muxutong.lashou.enity.ResponseObject;

/**
 * Servlet implementation class SelectOrderServlet
 */
@WebServlet("/SelectOrderServlet")
public class SelectOrderServlet extends HttpServlet {
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

				String state = request.getParameter("state");
				String user_id = request.getParameter("user_id");
				
				// ��ȡISO8859-1��ԭʼ����
				// �ڲ������ֽ�������ʵ�����ַ�����utf-8�������ֽ� ����Ӣ�Ŀ��Ա�ʾ�ģ�ֻ��
				// ISO8859-1��ʾ������һ���ֽ�һ���ֽ���ʾ����utf-8�Լ����ֽ�һ���ʾ
				byte[] bs = user_id.getBytes("ISO8859-1");
				user_id = new String(bs, "UTF-8");

				ResponseObject result = null;

				System.out.println("user_id---->>" + user_id + "state---->>" + state);
				OrdersDao ordersDao = new OrdersDaoImpl();
				
			
				List<Order> orders = ordersDao.getOrders(user_id, state);
				
				if (orders != null) {
					result = new ResponseObject("��ѯ�ɹ���", 1, orders);
				} else {
					result = new ResponseObject(0, "��ѯʧ��");
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
