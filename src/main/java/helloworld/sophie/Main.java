package web_final.sophie;

import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import web_final.sophie.inventoryInfo;
import web_final.sophie.insertInventory;

public class Main {

  public static void main(String[] args) {

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");
    Object r = new inventoryInfo();
    Object r1 = new insertInventory();
    get("/", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "index.ftl");
        }, new FreeMarkerEngine());

    get("/db", (req, res) -> {
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try {
    	URI dbUri = new URI(System.getenv("DATABASE_URL"));
        connection = DriverManager.getConnection(dbUri.toString(),"postgres","123");

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add( "Read from DB: " + rs.getTimestamp("tick"));
        }
        attributes.put("results", output);
        return new ModelAndView(attributes, "db.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
    }, new FreeMarkerEngine());

    get("/list", (req, res) -> {
        Connection connection = null;
        Map<String, Object> attributes = new HashMap<>();
        try {
          URI dbUri = new URI(System.getenv("DATABASE_URL"));
          connection = DriverManager.getConnection(dbUri.toString(),"postgres","123");
          Statement stmt = connection.createStatement();
          ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

          ArrayList<String> output = new ArrayList<String>();
          while (rs.next()) {
            output.add( "Read from DB: " + rs.getTimestamp("tick"));
          }
          
          attributes.put("results", output);
          return new ModelAndView(attributes, "db.ftl");
        } catch (Exception e) {
          attributes.put("message", "There was an error: " + e);
          return new ModelAndView(attributes, "error.ftl");
        } finally {
          if (connection != null) try{connection.close();} catch(SQLException e){}
        }
      }, new FreeMarkerEngine());
    
  }

}
