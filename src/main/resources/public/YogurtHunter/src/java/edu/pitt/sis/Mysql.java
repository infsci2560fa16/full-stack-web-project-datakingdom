package edu.pitt.sis;

import java.sql.Connection;
import java.sql.DriverManager;

public class Mysql {
	public static final String HOST = "localhost";
	public static final String PORT = "3306";
	public static final String DRIVER = "org.gjt.mm.mysql.Driver";
	public static final String USERNAME = "root";
	public static final String PASSWORD="123";
	
	public static Connection getConn(String databaseName) {
		try {
			Class.forName(DRIVER).newInstance();

			return DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT +"/"
			    + databaseName + "?user=" + USERNAME + "&password="
					+ PASSWORD + "&useUnicode=true&characterEncoding=utf-8");
		} catch(Exception e) {
			e.printStackTrace();
			return null;		
		}
	}

}
