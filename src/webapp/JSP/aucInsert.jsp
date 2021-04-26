<%@ page import="com.auctionsite.util.ApplicationDB" %>
<%@ page import="java.lang.module.ResolutionException" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.mysql.cj.x.protobuf.MysqlxPrepare" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: domin
  Date: 4/20/2021
  Time: 7:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Auction Creation</title>
</head>

<body>
<%
  try {

    //Get the database connection
    ApplicationDB db = new ApplicationDB();
    Connection con = db.getConnection();

    //Get parameters from the HTML form at the createAuc.jsp

    String username = (String)session.getAttribute("enduser");

    String getid = "SELECT uid FROM users WHERE users.display_name = '" + username + "'";
    Statement stmt = con.createStatement();


    ResultSet id = stmt.executeQuery(getid);
    int uid=0;
    if (id.next()) {
      uid = id.getInt("uid");
    }
    //General item parameters
    String item = request.getParameter("item");
    String item_con = request.getParameter("condition");
    String brand = request.getParameter("brand");
    String part = request.getParameter("part");
    String model = request.getParameter("model");
    float start_price;
    if (request.getParameter("start_price")==null || request.getParameter("start_price").isEmpty() ) {
      start_price = 0F;
    } else{
      start_price = Float.valueOf(request.getParameter("start_price"));
    }

    //pre-emptively declare variables
    String switches,kwire,klayout,mwire,dtype,dsize;
    switches=kwire=klayout=mwire=dtype=dsize="";
    int dpi=0,refresh=0;
    float weight=0F;

    //keyboard parameters
    if (item.equals("keyboard")) {
      switches = request.getParameter("switches");
      kwire = request.getParameter("kwire");
      klayout = request.getParameter("klayout");
    }

    //mouse parameters
    if (item.equals("mouse")) {
      mwire = request.getParameter("mwire");
      weight = Float.valueOf(request.getParameter("weight"));
      dpi = Integer.valueOf(request.getParameter("dpi"));
    }

    //monitor parameters
    if (item.equals("monitor")) {
      dtype = request.getParameter("dtype");
      dsize = request.getParameter("dsize");
      refresh = Integer.valueOf(request.getParameter("refresh"));
    }

    //Auction parameters
    String close_date = request.getParameter("close_date");
    String close_times = request.getParameter("close_time");

    LocalDate date = LocalDate.parse(close_date);
    LocalTime timePart = LocalTime.parse(close_times);
    LocalDateTime close_datetime = LocalDateTime.of(date,timePart);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String close_time = close_datetime.format(formatter);


    float bid_increment = Float.valueOf(request.getParameter("min_increment"));

    float min_price;
    if (request.getParameter("min_price")==null || request.getParameter("min_price").isEmpty() ) {
      min_price = 0F;
    } else{
      min_price = Float.valueOf(request.getParameter("min_price"));
    }




    //Make insert statement for Items
    String item_insert = "INSERT INTO items(brand,part_number,con,model,item_type) " +
            "VALUES (?,?,?,?,?)";
    PreparedStatement item_state = con.prepareStatement(item_insert);
    item_state.setString(1,brand);
    item_state.setString(2,part);
    item_state.setString(3,item_con);
    item_state.setString(4,model);
    item_state.setString(5,item);
    item_state.executeUpdate();

    if (item.equals("keyboard")) {
      String keyboard_insert = "INSERT INTO keyboards(part_number,switch_type,wire,layout) " +
              "VALUES (?,?,?,?)";
      PreparedStatement keyboard_state = con.prepareStatement(keyboard_insert);
      keyboard_state.setString(1,part);
      keyboard_state.setString(2,switches);
      keyboard_state.setString(3,kwire);
      keyboard_state.setString(4,klayout);
      keyboard_state.executeUpdate();
    }

    if (item.equals("monitor")) {
      String monitor_insert = "INSERT INTO monitors(part_number,display_type,display_size,refresh_rate) " +
              "VALUES (?,?,?,?)";
      PreparedStatement monitor_state = con.prepareStatement(monitor_insert);
      monitor_state.setString(1,part);
      monitor_state.setString(2,dtype);
      monitor_state.setString(3,dsize);
      monitor_state.setInt(4,refresh);
      monitor_state.executeUpdate();
    }

    if (item.equals("mouse")) {
      String mouse_insert = "INSERT INTO mice(part_number,wire,weight,DPI) " +
              "VALUES (?,?,?,?)";
      PreparedStatement mouse_state = con.prepareStatement(mouse_insert);
      mouse_state.setString(1,part);
      mouse_state.setString(2,mwire);
      mouse_state.setFloat(3,weight);
      mouse_state.setInt(4,dpi);
      mouse_state.executeUpdate();
    }

    //insert into auction
    String auc_insert = "INSERT INTO auctions(close_date,close_time,available," +
            "initial_price,bid_increment,secret_min_price,uid,part_number) " +
            "VALUES (?,?,?,?,?,?,?,?)";
    PreparedStatement auc_state = con.prepareStatement(auc_insert);
    auc_state.setString(1,close_date);
    auc_state.setString(2,close_time);
    auc_state.setBoolean(3,true);
    auc_state.setFloat(4,start_price);
    auc_state.setFloat(5,bid_increment);
    auc_state.setFloat(6,min_price);
    auc_state.setInt(7,uid);
    auc_state.setString(8,part);
    auc_state.executeUpdate();

    //Close the connection. Don't forget to do it, otherwise you're keeping the resources of the server allocated.
    con.close();

    out.println("You've created an auction for a " + brand + " brand " + item + " which is in " + item_con +
            " condition, " + "the auction will close on " + close_time +
            " and has a minimum price of $" + min_price);
  } catch (Exception ex) {
    out.println(ex);
    out.println(ex.getStackTrace()[0]);
    out.println("Auction creation failed, check to see that you've filled out the required fields");
  }
%>
</body>
<form action="EndUser.jsp" method="get">
  <input type="submit" value="Return to Homepage">
</form>
</html>
