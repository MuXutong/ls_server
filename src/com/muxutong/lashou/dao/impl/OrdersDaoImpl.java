package com.muxutong.lashou.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.muxutong.lashou.dao.OrdersDao;
import com.muxutong.lashou.enity.Goods;
import com.muxutong.lashou.enity.Order;
import com.muxutong.lashou.enity.Shop;

public class OrdersDaoImpl extends BaseDao implements OrdersDao {

	@Override
	public Order addOrders(String user_id, String prodouct_id, String state, String allprice, String count) {
			Connection connection=null;
		// 定义sql语句执行的对象
		Statement statement=null;
		// 定义查询返回的结果集合
		ResultSet resultSet=null;
		Order order=null;
		try {
			connection=getConn();//获得连接
			statement=connection.createStatement();//得到sql语句的执行对象
			
			String sql="INSERT INTO orders(user_id,orders_prodouct_count,orders_all_price,orders_paystate,orders_prodouct_id)"
					+ " VALUES("+user_id+","+count+","+allprice+","+state+","+prodouct_id+")";
			System.out.println(sql);
			statement.execute(sql);//插入数据
			
			String sqlForCheck="select * from orders where user_id ='"+user_id+"' and "+"orders_prodouct_id = '"+prodouct_id+"'";
			System.out.println(sqlForCheck);
			
			resultSet=statement.executeQuery(sqlForCheck);
			while(resultSet.next()){
				order=new Order();
				order.setOrdersAllPrice(resultSet.getString("orders_all_price"));
				order.setOrdersId(resultSet.getString("orders_id"));
				order.setOrdersPaystate(resultSet.getString("orders_paystate"));
				order.setOrdersProdouctCount(resultSet.getString("orders_prodouct_count"));
				order.setOrdersProdouctId(resultSet.getString("orders_prodouct_id"));
				order.setOrdersTime(resultSet.getString("orders_time"));
				order.setUserId(resultSet.getString("user_id"));
				
				System.out.println(order);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			close(resultSet, statement, connection);
			
		}
		return order;

	}

	@Override
	public List<Order> getOrders(String user_id,String state) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sqlForCheck="select  prodouct.*,orders.* from prodouct,orders where prodouct.prodouct_id=orders.orders_prodouct_id and "
				+ "user_id ='"+user_id+"' and "+"orders_paystate = '"+state+"'";

		List<Order> orders = null;
		try {
			connection = getConn();
			statement = connection.createStatement();
			System.out.println("sql语句：" + sqlForCheck);
			resultSet = statement.executeQuery(sqlForCheck);
			orders = new ArrayList<Order>();
			while (resultSet.next()) {
				Order order=new Order();
				order.setOrdersAllPrice(resultSet.getString("orders_all_price"));
				order.setOrdersId(resultSet.getString("orders_id"));
				order.setOrdersPaystate(resultSet.getString("orders_paystate"));
				order.setOrdersProdouctCount(resultSet.getString("orders_prodouct_count"));
				order.setOrdersProdouctId(resultSet.getString("orders_prodouct_id"));
				order.setOrdersTime(resultSet.getString("orders_time"));
				order.setUserId(resultSet.getString("user_id"));

				Goods product = new Goods();
				product.setId(resultSet.getString("prodouct_id"));
				product.setCategoryId(resultSet.getString("category_id"));
				product.setShopId(resultSet.getString("shop_id"));
				product.setCityId(resultSet.getString("city_id"));
				product.setTitle(resultSet.getString("prodouct_title"));
				product.setSortTitle(resultSet.getString("prodouct_sort_title"));
				product.setImgUrl(resultSet.getString("prodouct_image"));
				product.setStartTime(resultSet.getString("prodouct_start_time"));
				product.setValue(resultSet.getString("prodouct_value"));
				product.setPrice(resultSet.getString("prodouct_price"));
				product.setRibat(resultSet.getString("prodouct_ribat"));
				product.setBought(resultSet.getString("prodouct_bought"));
				product.setMinquota(resultSet.getString("prodouct_minquota"));
				product.setMaxQuota(resultSet.getString("prodouct_maxquota"));
				product.setPost(resultSet.getString("prodouct_post"));
				product.setSoldOut(resultSet.getString("prodouct_soldout"));
				product.setTip(resultSet.getString("prodouct_tip"));
				product.setEndTime(resultSet.getString("prodouct_end_time"));
				product.setDetail(resultSet.getString("prodouct_detail"));
				
				order.setGoods(product);	
				orders.add(order);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			close(resultSet, statement, connection);

		}

		return orders;
	}


}
