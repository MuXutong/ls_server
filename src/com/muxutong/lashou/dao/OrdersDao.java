package com.muxutong.lashou.dao;

import java.util.List;


import com.muxutong.lashou.enity.Order;

public interface OrdersDao {
	
	public Order addOrders(String user_id,String prodouct_id,String state,String allprice,String count);


	public List<Order> getOrders(String user_id, String state);
	
}
