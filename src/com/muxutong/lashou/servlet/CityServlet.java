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
import com.muxutong.lashou.dao.CityDao;
import com.muxutong.lashou.dao.impl.CityDaoImpl;
import com.muxutong.lashou.enity.City;
import com.muxutong.lashou.enity.ResponseObject;

@WebServlet("/CityServlet")
public class CityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		CityDao dao = new CityDaoImpl();	
	
		ResponseObject result = null;
		List<City> list = dao.getCity();
		if(list != null && list.size()>0) {
			
			result = new ResponseObject(1, list);
			
		}else {
			
			result = new ResponseObject(0, "没有城市数据！");
		}
		
		out.println(new GsonBuilder().create().toJson(result));
		out.flush();
		out.close();	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
