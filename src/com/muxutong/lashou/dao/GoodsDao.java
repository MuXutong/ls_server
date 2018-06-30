package com.muxutong.lashou.dao;

import java.util.List;

import org.apache.catalina.LifecycleListener;

import com.muxutong.lashou.enity.Goods;

public interface GoodsDao {

	public List<Goods>getList(String cityId,String catId,int page,int size);	
	
	public double getcount(String cityId,String catId);
	
	
}
