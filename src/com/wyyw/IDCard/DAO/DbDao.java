package com.wyyw.IDCard.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wyyw.IDCard.Bean.CardAreaBean;
import com.wyyw.IDCard.Bean.DateBean;

public class DbDao {
	private static DbDao dao;
	private String driver;
	private String url;
	private String user;
	private String pass;
	private Statement stmt;
	private Connection conn;
	public DbDao() {
		this.driver = "org.sqlite.JDBC";
		this.url = "jdbc:sqlite:area.db";
		try {
			Class.forName(this.driver);
			 conn = DriverManager.getConnection(this.url);
			//conn = DriverManager.getConnection(this.url, "root", "password");
			//stmt = conn.createStatement();
			//stmt.execute("CREATE TABLE IF NOT EXISTS  \"data\" (\"id\"  INTEGER NOT NULL,\"url\"  TEXT,\"pass\"  TEXT,\"config\"  TEXT,\"type\"  TEXT,\"code\"  TEXT,\"ip\"  TEXT,\"time\"  TEXT,PRIMARY KEY (\"id\"));");
		} catch (Exception e) {
		}
	}

	public static DbDao getInstance() {
		if (dao == null) {
			dao = new DbDao();
		}
		return dao;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Statement getStmt() throws SQLException {
		return conn.createStatement();
	}
	
	
	/**
	 * 根据sql查询地区编码
	 * @param sql 要执行的sql语句
	 * @return list列表
	 * @throws Exception 如果报错就执行
	 */
	public List querySql(String sql) throws Exception{
		
			List list = new ArrayList();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			try{
				if(null != rs){
					while(rs.next()){
						CardAreaBean cab = new CardAreaBean();
						cab.setId(String.valueOf(rs.getInt("id")));
						cab.setCode(rs.getString("code"));
						cab.setArea(rs.getString("area"));
						list.add(cab);
					}
					return list;
					
				}else{
					return list;
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				rs.close();
				st.close();
			}
			return list;
	}

	/**
	 * 根据sql查询地区编码
	 * @param sql 要执行的sql语句
	 * @return list列表
	 * @throws Exception 如果报错就执行
	 */
	public List querySql_date(String sql) throws Exception{
		
			List list = new ArrayList();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			try{
				if(null != rs){
					while(rs.next()){
						DateBean datebean = new DateBean();
						datebean.setId(String.valueOf(rs.getInt("id")));
						datebean.setDate(rs.getString("date"));
						list.add(datebean);
					}
					return list;
					
				}else{
					return list;
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				rs.close();
				st.close();
			}
			return list;
	}


}
