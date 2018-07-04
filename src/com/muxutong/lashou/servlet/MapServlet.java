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
 * Servlet implementation class MapServlet
 */
@WebServlet("/MapServlet")
public class MapServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//设置编码格式
				response.setContentType("text/html;charset=utf-8");
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				PrintWriter out = response.getWriter();
				
				String lat = request.getParameter("lat");
				String lon = request.getParameter("lon");
				String radius = request.getParameter("radius");
				
				GoodsDao dao = new GoodsDaoImpl();
				
				double [] around = CommonUtil.getAround(Double.parseDouble(lat), Double.parseDouble(lon), Double.parseDouble(radius));
				
				List<Goods>list=dao.getGoodByMap(Double.parseDouble(lat) ,Double.parseDouble(lon) ,around[0], around[1], around[2], around[3]);
				
				
				ResponseObject result = null;

				if(list != null && list.size()>0) {
					
					result = new ResponseObject(1, list);

				}else {
					
					Goods goodsMsg=new Goods();
					goodsMsg.setDetail("没有获得商品数据");
					list.add(goodsMsg);
					result = new ResponseObject(0, list);
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
