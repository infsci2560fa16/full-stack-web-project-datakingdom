package web_final.sophie;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
import static spark.Spark.post;

public class insertInventory {
	Gson gson = new Gson();
	public insertInventory(){
		insert();
	}
	private void insert(){
		post("/insert",(req,res)->{
			String content = req.body();
			String order = content.split("=")[1];
			JsonParser jsonParser = new JsonParser();
			JsonObject jsonArray = (JsonObject) jsonParser.parse(order);
//			System.out.print(jsonArray.get("kiwi"));
	        int kiwi = jsonArray.get("kiwi").getAsInt();
	        int mango = jsonArray.get("mango").getAsInt();
	        int cantaloupe = jsonArray.get("cantaloupe").getAsInt();
	        double total = jsonArray.get("total").getAsDouble();
            Map<String, Object> attributes = new HashMap<>();
    
            Connection connection = null;
            try {
            	URI dbUri = new URI(System.getenv("DATABASE_URL"));
                connection = DriverManager.getConnection(dbUri.toString(),"postgres","123");

                Statement stmt = connection.createStatement();
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS yogurts(kiwi character(10), mango character(10), cantaloupe character(10), total character(10))");          
                stmt.executeUpdate("INSERT INTO yogurts VALUES ("+kiwi+","+mango+","+cantaloupe+","+total+")");
                
                return new ModelAndView(attributes, "index.ftl");
            } catch (Exception e) {
              attributes.put("message", "There was an error: " + e);
              return new ModelAndView(attributes, "error.ftl");
            } finally {
              if (connection != null) try{connection.close();} catch(SQLException e){}
            }
          }, new FreeMarkerEngine());
	}
}
