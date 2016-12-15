package helloworld.sophie;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

public class inventoryInfo {
	public inventoryInfo() {
		getInventoryInfo();
	}
	private void getInventoryInfo(){
		get("/inventoryinfo",(req,res)->{
            Map<String, Object> attributes = new HashMap<>();
    
            Connection connection = null;
            try {
            	URI dbUri = new URI(System.getenv("DATABASE_URL"));
                connection = DriverManager.getConnection(dbUri.toString(),"postgres","123");

                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM yogurts");

                ArrayList<String> output = new ArrayList<String>();
                while (rs.next()) {
                  output.add( "Read from DB:" + " kiwi:" + rs.getString("kiwi") + " mango:" + rs.getString("mango") + " cantaloupe:" + rs.getString("cantaloupe") + " total:" + rs.getString("total"));
                }
                attributes.put("results", output);
                return new ModelAndView(attributes, "list.ftl");
            } catch (Exception e) {
              attributes.put("message", "There was an error: " + e);
              return new ModelAndView(attributes, "error.ftl");
            } finally {
              if (connection != null) try{connection.close();} catch(SQLException e){}
            }
          }, new FreeMarkerEngine());
			
		}
}

