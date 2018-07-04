package com.muxutong.lashou.dao;

import java.util.List;

import com.muxutong.lashou.enity.Favorite;
import com.muxutong.lashou.enity.Goods;


public interface FavoriteDao {

	public Favorite add(String user_id,String prodouct_id);
	
	
}
