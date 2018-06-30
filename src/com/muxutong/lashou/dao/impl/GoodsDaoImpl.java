package com.muxutong.lashou.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import com.muxutong.lashou.dao.GoodsDao;
import com.muxutong.lashou.enity.Goods;
import com.muxutong.lashou.enity.Shop;


public class GoodsDaoImpl extends BaseDao implements GoodsDao {

	private String str = "prodouct.prodouct_id,prodouct.category_id,prodouct.shop_id,prodouct.sub_category_id,prodouct.city_id,prodouct.prodouct_title," + 
			"prodouct.prodouct_sort_title,prodouct.prodouct_image,prodouct.prodouct_start_time,prodouct.prodouct_value,prodouct.prodouct_price," + 
			"prodouct.prodouct_ribat,prodouct.prodouct_bought,prodouct.prodouct_minquota,prodouct.prodouct_maxquota,prodouct.prodouct_post," + 
			"prodouct.prodouct_soldout,prodouct.prodouct_tip,prodouct.prodouct_end_time,prodouct.prodouct_detail,prodouct.prodouct_is_refund,prodouct.prodouct_is_over_time," + 
			"shop.shop_name,shop.shop_tel,shop.shop_address,shop.shop_area,shop.shop_open_time,shop.shop_lon,shop.shop_lat,shop.shop_traffic_info ";
	@Override
	public List<Goods> getList(String cityId, String catId, int page, int size) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "select "+str+" from prodouct,shop where prodouct.shop_id=shop.shop_id"
		+(StringUtils.isNotBlank(cityId)?(" and city_id"+cityId):"")
		+(StringUtils.isNotBlank(catId)?(" and category_id"+catId):"")
		+" limit "+(page*size)+","+size;
		
		
		List<Goods> goods = null;
		try {
			connection=getConn();
			statement= connection.createStatement();
			System.out.println("sql语句："+sql);
			resultSet=statement.executeQuery(sql);
			System.out.println("连接数据库成功");
			goods = new ArrayList<Goods>();
			while (resultSet.next()) {
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
				
				Shop shop = new Shop();
				shop.setId(resultSet.getString("shop_id"));
				shop.setName(resultSet.getString("shop_name"));
				shop.setTel(resultSet.getString("shop_tel"));
				shop.setAddress(resultSet.getString("shop_address"));
				shop.setArea(resultSet.getString("shop_area"));
				shop.setOpentime(resultSet.getString("shop_open_time"));
				shop.setLat(resultSet.getString("shop_lat"));
				shop.setLon(resultSet.getString("shop_lon"));
				
				product.setShop(shop);
				
				int refund = resultSet.getInt("prodouct_is_refund");
				int overTime = resultSet.getInt("prodouct_is_over_time");
				
				if(refund == 1) {
					product.setRefund(true);
				}else if(refund == 0) {
					product.setRefund(false);
				}
				
				if(overTime == 1) {
					product.setOverTime(true);
				}else if(overTime == 0) {
					product.setOverTime(false);
				}
				
				System.out.println(product);
				
				goods.add(product);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}finally {
			close(resultSet, statement, connection);
			
		}
		
		
		return goods;
	}

	@Override
	public double getcount(String cityId, String catId) {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "select count(*) from prodouct where 1=1"+(StringUtils.isNotBlank(cityId)?("and city_id"+cityId):"")
				+(StringUtils.isNotBlank(catId)?("and category_id"+catId):"");
		
		try {
			connection=getConn();
			statement= connection.createStatement();
			System.out.println("sql语句："+sql);
			resultSet=statement.executeQuery(sql);
			
			if(resultSet.next()) {
				int count = Integer.parseInt(resultSet.getString("count(*)"));
				return count;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}finally {
			close(resultSet, statement, connection);
		}
			
		return 0;
	}

}
