<%@ page import="com.auctionsite.util.ApplicationDB" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.Statement" %><%--
  Created by IntelliJ IDEA.
  User: domin
  Date: 4/23/2021
  Time: 12:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Manual Bidding</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/GoBackServlet" method="get">
  <input type="hidden" name="origin" value="EndUser.jsp">
  <input type="submit" value="Go Back!">
</form>

<%

  try {
    //Get the database connection
    ApplicationDB db = new ApplicationDB();
    Connection con = db.getConnection();


    String username = (String)session.getAttribute("username");
    String getid = "SELECT uid FROM users WHERE users.display_name = '" + username + "'";
    Statement stmt = con.createStatement();
    ResultSet id = stmt.executeQuery(getid);
    int uid=0;
    if (id.next()) {
      uid = id.getInt("uid");
    }

    Integer aid = Integer.valueOf(request.getParameter("aid"));
    String bid = request.getParameter("bid");

    if (bid.isEmpty()) {
      out.print("Make sure you enter your bid price in the text box!");
    } else {
      float fbid = Float.valueOf(bid);

      float top_bid=0;
      String get_top = "SELECT highest_bid FROM auctions WHERE auctions.uid = '" + uid + "'";
      Statement state = con.createStatement();
      ResultSet bids = stmt.executeQuery(get_top);
      if (id.next()) {
        top_bid = id.getFloat("highest_bid");
      }
      if (top_bid > fbid) {
        out.print("Your bid is lower than than the current highest  bid");
      } else {
        //inserts bid into bids table and updates auction table accordingly
        String bid_insert = "INSERT INTO bids(aid, uid, amnt)" +
                "VALUES (?,?,?)";
        PreparedStatement auc_state = con.prepareStatement(bid_insert);
        auc_state.setInt(1, aid);
        auc_state.setInt(2, uid);
        auc_state.setFloat(3, fbid);

        String auc_insert = "UPDATE auctions SET highest_bid = ? WHERE auctions.aid = ?";
        PreparedStatement auc_update = con.prepareStatement(auc_insert);
        auc_update.setFloat(1, fbid);
        auc_update.setInt(2, aid);

        out.print("You've placed a bid of" + fbid + "on auction #" + aid + "!");
      }
      con.close();
    }
  } catch (Exception ex) {
    out.println(ex);
    out.println(ex.getStackTrace()[0]);
    out.println("Auction creation failed, check to see that you've filled out the required fields");
  }
%>
</body>
</html>
