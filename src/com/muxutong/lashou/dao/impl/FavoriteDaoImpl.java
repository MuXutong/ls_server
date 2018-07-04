package com.muxutong.lashou.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.muxutong.lashou.dao.FavoriteDao;
import com.muxutong.lashou.enity.Favorite;
import com.muxutong.lashou.enity.Goods;
import com.muxutong.lashou.enity.Shop;
import com.muxutong.lashou.enity.User;

public class FavoriteDaoImpl extends BaseDao implements FavoriteDao{

	@Override
	public Favorite add(String user_id, String prodouct_id) {
		Connection connection=null;
		// 定义sql语句执行的对象
		Statement statement=null;
		// 定义查询返回的结果集合
		ResultSet resultSet=null;
		Favorite favorite=null;
		try {
			connection=getConn();//获得连接
			statement=connection.createStatement();//得到sql语句的执行对象
			String sqlForCheck="select * from favorite where user_id ='"+user_id+"' and "+"prodouct_id = '"+prodouct_id+"'";
			System.out.println(sqlForCheck);
			resultSet=statement.executeQuery(sqlForCheck);//查看该用户是否已存在
			if (resultSet.next()) {//用户名已存在，返回null不注册
				System.out.println("....已收藏");
				return null;
			}else{
				String sql="insert into favorite(user_id,prodouct_id) values('"+user_id+"','"+prodouct_id+"')";
				System.out.println(sql);
				statement.execute(sql);//插入数据
				resultSet=statement.executeQuery(sqlForCheck);
				while(resultSet.next()){
					favorite=new Favorite();
					favorite.setUser_id(resultSet.getString("user_id"));
					favorite.setFavorite_id(resultSet.getString("favorite_id"));
					favorite.setProdouct_id(resultSet.getString("prodouct_id"));
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			close(resultSet, statement, connection);
			
		}
		return favorite;
	}

	
	
}
