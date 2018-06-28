package com.muxutong.lashou.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.muxutong.lashou.dao.CityDao;
import com.muxutong.lashou.enity.City;



public class CityDaoImpl extends BaseDao implements CityDao{

	@Override
	public List<City> getCity() {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<City> cities = null;
		
		try {
			connection=getConn();
			statement= connection.createStatement();
			resultSet=statement.executeQuery("select * from city");
			System.out.println("连接数据库成功");
			cities = new ArrayList<City>();
			while (resultSet.next()) {
				City city = new City();
				city.setId(resultSet.getString("city_id"));
				city.setName(resultSet.getString("city_name"));
				city.setSortKey(resultSet.getString("city_sortkey"));
				cities.add(city);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}finally {
			close(resultSet, statement, connection);
		}
		
		return cities;
	}

	

	
}
