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
		
		//设置编码格式
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		int page=0;
		int size=20;
		
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String radius = request.getParameter("radius");
		
		String category=request.getParameter("category");//分类id
		
		if(request.getParameter("page")!=null&&!"".equals(request.getParameter("page"))){
			page=Integer.parseInt(request.getParameter("page"));
		}
		if(request.getParameter("size")!=null&&!"".equals(request.getParameter("size"))){
			size=Integer.parseInt(request.getParameter("size"));
		}
		
		GoodsDao dao = new GoodsDaoImpl();
		
		double [] around = CommonUtil.getAround(Double.parseDouble(lat), Double.parseDouble(lon), Double.parseDouble(radius));
		
		
		List<Goods>list=dao.getGoodByLBS(page,size,category,Double.parseDouble(lat) ,Double.parseDouble(lon) ,around[0], around[1], around[2], around[3]);
		int count=dao.getCountByLBS(category,Double.parseDouble(lat) ,Double.parseDouble(lon) ,around[0], around[1], around[2], around[3]);

		
		ResponseObject result = null;

		if(list != null && list.size()>0) {
			
			result = new ResponseObject(1, list);
			result.setPage(page);
			result.setSize(size);
			result.setCount((int) Math.ceil(count / size));

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
