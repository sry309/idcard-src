package com.wyyw.IDCard.Test;

import java.sql.*;

public class SQLiteJDBC
{
	
	
public static void test(){
	
	Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:area.db");
      c.setAutoCommit(false);
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM t_cardarea;" );
      while ( rs.next() ) {
         int id = rs.getInt("id");
         String  code = rs.getString("code");
         String area =rs.getString("area");
         System.out.println( "ID = " + id );
         System.out.println( "NAME = " + code );
         System.out.println( "AGE = " + area );
         System.out.println();
      }
      rs.close();
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Operation done successfully");
  }
	
}