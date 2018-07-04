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
import com.muxutong.lashou.dao.OrdersDao;
import com.muxutong.lashou.dao.impl.FavoriteDaoImpl;
import com.muxutong.lashou.dao.impl.OrdersDaoImpl;
import com.muxutong.lashou.enity.Favorite;
import com.muxutong.lashou.enity.Order;
import com.muxutong.lashou.enity.ResponseObject;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 处理响应数据的格式和编码，通知浏览器数据的显示方式
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String state = request.getParameter("state");
		String allprice = request.getParameter("allprice");
		String count = request.getParameter("count");
		String prodouct_id = request.getParameter("prodouct_id");
		String user_id = request.getParameter("user_id");
		
		// 获取ISO8859-1的原始数据
		// 内部编码字节流，其实中文字符经过utf-8编码后的字节 都是英文可以表示的，只是
		// ISO8859-1显示数据以一个字节一个字节显示，而utf-8以几个字节一起表示
		byte[] bs = user_id.getBytes("ISO8859-1");
		user_id = new String(bs, "UTF-8");

		ResponseObject result = null;

		System.out.println("user_id---->>" + user_id + "prodouct_id---->>" + prodouct_id);
		OrdersDao ordersDao = new OrdersDaoImpl();
		
	
		Order order = ordersDao.addOrders(user_id, prodouct_id, state, allprice, count);
		if (order != null) {
			result = new ResponseObject("付款成功！", 1, order);
		} else {
			result = new ResponseObject(0, "订单添加失败");
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
