package com.muxutong.lashou.dao;

import java.util.List;


import com.muxutong.lashou.enity.Goods;


public interface GoodsDao {

	public List<Goods>getList(String cityId,String catId,int page,int size);	
	
	public double getcount(String cityId,String catId);
	
	public List<Goods> getGoodByLBS(int page,int size,String category,double lat,double lon,double minLat,double minLon,double maxLat,double maxLon);

	//获得附近的商品数据的条数
	public int getCountByLBS(String category,double lat,double lon,double minLat,double minLon,double maxLat,double maxLon);

}
