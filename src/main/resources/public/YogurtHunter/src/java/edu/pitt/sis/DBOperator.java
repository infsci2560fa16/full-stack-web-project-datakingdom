package edu.pitt.sis;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperator {
    public static void write(String databasename, String sql){
		try {
                    Connection conn = Mysql.getConn(databasename);	
                    Statement st = conn.createStatement();	
                    st.executeUpdate(sql);
                    st.close();
                    conn.close();
		} catch(SQLException sqle){
		    System.out.println("Errors in query.");
		}
	}
}

