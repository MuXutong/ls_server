package com.muxutong.lashou.dao;

import java.sql.*;

public interface Dao {
	Connection getConn() throws Exception;
	void close(ResultSet res,Statement stm,Connection conn)throws Exception;

}
