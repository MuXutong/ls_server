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

/**
 * Servlet implementation class GoodsServlet
 */
@WebServlet("/GoodsServlet")
public class GoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoodsServlet() {
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
		
		GoodsDao dao = new GoodsDaoImpl();
		String cityId = request.getParameter("cityId");
		String catId = request.getParameter("catId");
		int page = Integer.parseInt(request.getParameter("page"));
		int size = Integer.parseInt(request.getParameter("size"));
		
		List<Goods> list = dao.getList(cityId, catId, page, size);
		double count = dao.getcount(cityId, catId);
		
		ResponseObject result = null;

		if(list != null && list.size()>0) {
			
			result = new ResponseObject(1, list);
			result.setPage(page);
			result.setCount((int)Math.ceil(count/size));
			result.setSize(size);
		}else {
			
			result = new ResponseObject(0, "没有商品数据！");
		}
		
		out.println(new GsonBuilder().create().toJson(result));
		
		//out.println("</HTML>");
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
