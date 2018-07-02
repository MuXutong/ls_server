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
 * Servlet implementation class NearbyServlet
 */
@WebServlet("/NearbyServlet")
public class NearbyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public NearbyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String raidus = request.getParameter("raidus");
		
		double [] around = CommonUtil.getAround(Double.parseDouble(lat), Double.parseDouble(lon), Double.parseDouble(raidus));
		
		GoodsDao dao = new GoodsDaoImpl();
		List<Goods> list = dao.getGoodByLBS(around[0], around[1], around[2], around[3]);
		
		ResponseObject result = null;

		if(list != null && list.size()>0) {
			
			result = new ResponseObject(1, list);
			
		}else {
			
			result = new ResponseObject(0, "没有商品数据！");
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
