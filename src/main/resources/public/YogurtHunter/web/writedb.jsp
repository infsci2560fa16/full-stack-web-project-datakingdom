<%@ page language="java" import="java.util.*,edu.pitt.sis.*" pageEncoding="utf-8" %>

<%

    String kiwi = request.getParameter("kiwi");
    if(kiwi == null) kiwi = "";

    String mango = request.getParameter("mango");
    if(mango == null) mango = "mango";

    String total = request.getParameter("total");
    if(total == null) total = "total";
    
    String sql = "INSERT INTO yogurt(kiwi, mango, total)"
            + " VALUES(" + kiwi + ", " + mango + ", " + total + ")";
    
    DBOperator.write("sandbox", sql);
    
    out.println(kiwi + "<br/>" + mango + "<br/>" + total);

%>
