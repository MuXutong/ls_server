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

	private String str = "prodouct.prodouct_id,prodouct.category_id,prodouct.shop_id,prodouct.sub_category_id,prodouct.city_id,prodouct.prodouct_title,"
			+ "prodouct.prodouct_sort_title,prodouct.prodouct_image,prodouct.prodouct_start_time,prodouct.prodouct_value,prodouct.prodouct_price,"
			+ "prodouct.prodouct_ribat,prodouct.prodouct_bought,prodouct.prodouct_minquota,prodouct.prodouct_maxquota,prodouct.prodouct_post,"
			+ "prodouct.prodouct_soldout,prodouct.prodouct_tip,prodouct.prodouct_end_time,prodouct.prodouct_detail,prodouct.prodouct_is_refund,prodouct.prodouct_is_over_time,"
			+ "shop.shop_name,shop.shop_tel,shop.shop_address,shop.shop_area,shop.shop_open_time,shop.shop_lon,shop.shop_lat,shop.shop_traffic_info ";

	/**
	 * 获取商品的列表数据
	 */

	@Override
	public List<Goods> getList(String cityId, String catId, int page, int size) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "select " + str + " from prodouct,shop where prodouct.shop_id=shop.shop_id"
				+ (StringUtils.isNotBlank(cityId) ? (" and city_id" + cityId) : "")
				+ (StringUtils.isNotBlank(catId) ? (" and category_id" + catId) : "") + " limit " + (page * size) + ","
				+ size;

		List<Goods> goods = null;
		try {
			connection = getConn();
			statement = connection.createStatement();
			System.out.println("sql语句：" + sql);
			resultSet = statement.executeQuery(sql);
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

				String str1 = resultSet.getString("prodouct_detail");
				product.setDetail(str1);

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

				if (refund == 1) {
					product.setRefund(true);
				} else if (refund == 0) {
					product.setRefund(false);
				}

				if (overTime == 1) {
					product.setOverTime(true);
				} else if (overTime == 0) {
					product.setOverTime(false);
				}

				// System.out.println(product);

				goods.add(product);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			close(resultSet, statement, connection);

		}

		return goods;
	}

	/**
	 * 获取商品数据的条数
	 */

	@Override
	public double getcount(String cityId, String catId) {
		// TODO Auto-generated method stub

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "select count(*) from prodouct where 1=1"
				+ (StringUtils.isNotBlank(cityId) ? ("and city_id" + cityId) : "")
				+ (StringUtils.isNotBlank(catId) ? ("and category_id" + catId) : "");

		try {
			connection = getConn();
			statement = connection.createStatement();
			System.out.println("sql语句：" + sql);
			resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				int count = Integer.parseInt(resultSet.getString("count(*)"));
				return count;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		} finally {
			close(resultSet, statement, connection);
		}

		return 0;
	}

	@Override
	public int getCountByLBS(String category, double lat, double lon, double minLat, double minLon, double maxLat,
			double maxLon) {
		String sql = "select count(*) as count" + " from prodouct p,shop s where p.shop_id=s.shop_id";
		if (category == null || "".equals(category)) {// 为空是即代表查询全部

		} else if (category.length() < 4) {
			sql += " and category_id=" + category;
		} else {
			sql += " and sub_category_id=" + category;
		}
		sql += " and s.shop_lat BETWEEN '" + minLat + "' and '" + maxLat + "' and s.shop_lon BETWEEN '" + minLon
				+ "' and '" + maxLon + "'";
		// 定义数据库链接
		Connection connection = null;
		// 定义sql语句执行的对象
		Statement statement = null;
		// 定义查询返回的结果集合
		ResultSet resultSet = null;
		int count = 0;
		try {
			connection = getConn();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			System.out.println(sql);

			while (resultSet.next()) {
				count = resultSet.getInt("count");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {

			close(resultSet, statement, connection);

		}
		return count;
	}

	@Override
	public List<Goods> getGoodByLBS(int page, int size, String category, double lat, double lon, double minLat,
			double minLon, double maxLat, double maxLon) {
		String sql = "select " + "GLength(LineString(PointFromWKB(POINT(" + lat + "," + lon
				+ ")),PointFromWKB(POINT(s.shop_lat,s.shop_lon))))*69*1609.344 AS distance,"
				+ "p.*,s.* from prodouct p,shop s where p.shop_id=s.shop_id";
		if (category == null || "".equals(category)) {// 为空是即代表查询全部

		} else if (category.length() < 4) {
			sql += " and category_id=" + category;
		} else {
			sql += " and sub_category_id=" + category;
		}
		sql += " and s.shop_lat BETWEEN '" + minLat + "' and '" + maxLat + "' and s.shop_lon BETWEEN '" + minLon
				+ "' and '" + maxLon + "'" + "order by distance";
		if (page == 0) {
			page = 1;
		}
		sql += " limit " + ((page - 1) * size) + "," + (page * size);
		// GLength(LineString(PointFromWKB(POINT([lat],[lon])),PointFromWKB(POINT(shop.shop_lat,shop.shop_lon))))*69*1609.344
		// AS distance通过MySQL数据库方法实现模糊的距离测算，并以distance字段返回
		// 定义数据库链接
		Connection connection = null;
		// 定义sql语句执行的对象
		Statement statement = null;
		// 定义查询返回的结果集合
		ResultSet resultSet = null;
		List<Goods> gList = new ArrayList<Goods>();
		try {
			connection = getConn();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			System.out.println(sql);

			while (resultSet.next()) {
				Goods goods = new Goods();

				goods.setId(resultSet.getString("prodouct_id"));
				goods.setCategoryId(resultSet.getString("category_id"));
				goods.setShopId(resultSet.getString("shop_id"));
				goods.setCityId(resultSet.getString("city_id"));
				goods.setTitle(resultSet.getString("prodouct_title"));
				goods.setSortTitle(resultSet.getString("prodouct_sort_title"));
				goods.setImgUrl(resultSet.getString("prodouct_image"));
				goods.setStartTime(resultSet.getString("prodouct_start_time"));
				goods.setValue(resultSet.getString("prodouct_value"));
				goods.setPrice(resultSet.getString("prodouct_price"));
				goods.setRibat(resultSet.getString("prodouct_ribat"));
				goods.setBought(resultSet.getString("prodouct_bought"));
				goods.setMaxQuota(resultSet.getString("prodouct_maxquota"));
				goods.setPost(resultSet.getString("prodouct_post"));
				goods.setSoldOut(resultSet.getString("prodouct_soldout"));
				goods.setTip(resultSet.getString("prodouct_tip"));
				goods.setEndTime(resultSet.getString("prodouct_end_time"));
				goods.setDetail(resultSet.getString("prodouct_detail"));
				goods.setMinquota(resultSet.getString("prodouct_minquota"));

				Shop shop = new Shop();
				shop.setId(resultSet.getString("shop_id"));
				shop.setName(resultSet.getString("shop_name"));
				shop.setTel(resultSet.getString("shop_tel"));
				shop.setAddress(resultSet.getString("shop_address"));
				shop.setArea(resultSet.getString("shop_area"));
				shop.setLon(resultSet.getString("shop_lon"));
				shop.setLat(resultSet.getString("shop_lat"));
				shop.setOpentime(resultSet.getString("shop_open_time"));
				goods.setShop(shop);
				int refund = resultSet.getInt("prodouct_is_refund");
				int overTime = resultSet.getInt("prodouct_is_over_time");
				if (refund == 1) {
					goods.setRefund(true);
				} else {
					goods.setRefund(false);
				}
				if (overTime == 1) {
					goods.setOverTime(true);
				} else {
					goods.setOverTime(false);
				}

				gList.add(goods);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {

			close(resultSet, statement, connection);

		}
		return gList;
	}

	// select prodouct.*,shop.* from prodouct,shop,favorite where
	// prodouct.shop_id=shop.shop_id and prodouct.prodouct_id=favorite.prodouct_id
	// and favorite.user_id=16

	@Override
	public List<Goods> getGoodByFavo(String user_id) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String sql = "select prodouct.*,shop.* from prodouct,shop,favorite where prodouct.shop_id=shop.shop_id and prodouct.prodouct_id=favorite.prodouct_id and favorite.user_id="
				+ user_id;

		List<Goods> goods = null;
		try {
			connection = getConn();
			statement = connection.createStatement();
			System.out.println("sql语句：" + sql);
			resultSet = statement.executeQuery(sql);
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

				String str1 = resultSet.getString("prodouct_detail");
				product.setDetail(str1);

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

				if (refund == 1) {
					product.setRefund(true);
				} else if (refund == 0) {
					product.setRefund(false);
				}

				if (overTime == 1) {
					product.setOverTime(true);
				} else if (overTime == 0) {
					product.setOverTime(false);
				}

				// System.out.println(product);

				goods.add(product);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} finally {
			close(resultSet, statement, connection);

		}

		return goods;
	}

	@Override
	public List<Goods> getGoodByMap(double lat, double lon, double minLat, double minLon, double maxLat,
			double maxLon) {
		String sql = "select " + "GLength(LineString(PointFromWKB(POINT(" + lat + "," + lon
				+ ")),PointFromWKB(POINT(s.shop_lat,s.shop_lon))))*69*1609.344 AS distance,"
				+ "p.*,s.* from prodouct p,shop s where p.shop_id=s.shop_id";
	
		sql += " and s.shop_lat BETWEEN '" + minLat + "' and '" + maxLat + "' and s.shop_lon BETWEEN '" + minLon
				+ "' and '" + maxLon + "'" + "order by distance";
		
		// GLength(LineString(PointFromWKB(POINT([lat],[lon])),PointFromWKB(POINT(shop.shop_lat,shop.shop_lon))))*69*1609.344
		// AS distance通过MySQL数据库方法实现模糊的距离测算，并以distance字段返回
		// 定义数据库链接
		Connection connection = null;
		// 定义sql语句执行的对象
		Statement statement = null;
		// 定义查询返回的结果集合
		ResultSet resultSet = null;
		List<Goods> gList = new ArrayList<Goods>();
		try {
			connection = getConn();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			System.out.println(sql);

			while (resultSet.next()) {
				Goods goods = new Goods();

				goods.setId(resultSet.getString("prodouct_id"));
				goods.setCategoryId(resultSet.getString("category_id"));
				goods.setShopId(resultSet.getString("shop_id"));
				goods.setCityId(resultSet.getString("city_id"));
				goods.setTitle(resultSet.getString("prodouct_title"));
				goods.setSortTitle(resultSet.getString("prodouct_sort_title"));
				goods.setImgUrl(resultSet.getString("prodouct_image"));
				goods.setStartTime(resultSet.getString("prodouct_start_time"));
				goods.setValue(resultSet.getString("prodouct_value"));
				goods.setPrice(resultSet.getString("prodouct_price"));
				goods.setRibat(resultSet.getString("prodouct_ribat"));
				goods.setBought(resultSet.getString("prodouct_bought"));
				goods.setMaxQuota(resultSet.getString("prodouct_maxquota"));
				goods.setPost(resultSet.getString("prodouct_post"));
				goods.setSoldOut(resultSet.getString("prodouct_soldout"));
				goods.setTip(resultSet.getString("prodouct_tip"));
				goods.setEndTime(resultSet.getString("prodouct_end_time"));
				goods.setDetail(resultSet.getString("prodouct_detail"));
				goods.setMinquota(resultSet.getString("prodouct_minquota"));

				Shop shop = new Shop();
				shop.setId(resultSet.getString("shop_id"));
				shop.setName(resultSet.getString("shop_name"));
				shop.setTel(resultSet.getString("shop_tel"));
				shop.setAddress(resultSet.getString("shop_address"));
				shop.setArea(resultSet.getString("shop_area"));
				shop.setLon(resultSet.getString("shop_lon"));
				shop.setLat(resultSet.getString("shop_lat"));
				shop.setOpentime(resultSet.getString("shop_open_time"));
				goods.setShop(shop);
				int refund = resultSet.getInt("prodouct_is_refund");
				int overTime = resultSet.getInt("prodouct_is_over_time");
				if (refund == 1) {
					goods.setRefund(true);
				} else {
					goods.setRefund(false);
				}
				if (overTime == 1) {
					goods.setOverTime(true);
				} else {
					goods.setOverTime(false);
				}

				gList.add(goods);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {

			close(resultSet, statement, connection);

		}
		return gList;
	}

}
