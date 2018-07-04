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
import com.muxutong.lashou.dao.GoodsDao;
import com.muxutong.lashou.dao.impl.GoodsDaoImpl;
import com.muxutong.lashou.enity.Goods;
import com.muxutong.lashou.enity.ResponseObject;
import com.muxutong.lashou.util.CommonUtil;

/**
 * Servlet implementation class ShowFavoriteServlet
 */
@WebServlet("/ShowFavoriteServlet")
public class ShowFavoriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 设置编码格式
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

		String user_id = request.getParameter("user_id");
		

		GoodsDao dao = new GoodsDaoImpl();


		List<Goods> list = dao.getGoodByFavo(user_id);
		
		ResponseObject result = null;

		if (list != null && list.size() > 0) {

			result = new ResponseObject(1, list);
			

		} else {

			Goods goodsMsg = new Goods();
			goodsMsg.setDetail("没有获得商品数据");
			list.add(goodsMsg);
			result = new ResponseObject(0, list);
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
