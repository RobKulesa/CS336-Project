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

    String username = (String)session.getAttribute("enduser");
    String getid = "SELECT uid FROM users WHERE users.display_name = '" + username + "'";
    Statement stmt = con.createStatement();
    ResultSet id = stmt.executeQuery(getid);

    int uid=0;
    if (id.next()) {
      uid = id.getInt("uid");
    }

    Integer aid = Integer.valueOf(request.getParameter("aid"));

    String bid = request.getParameter("bid");

    Float bid_inc = Float.valueOf(request.getParameter("bid_increment"));

    float top_bid=0;
    if (bid.isEmpty()) {
      out.print("Make sure you enter your bid price in the text box!");
    } else {
      float fbid = Float.valueOf(bid);

      String get_top = "SELECT bid_amt FROM auctions WHERE auctions.aid = '" + aid + "'";
      Statement state = con.createStatement();
      ResultSet bids = state.executeQuery(get_top);

      if (bids.next()) {
        top_bid = bids.getFloat("bid_amt");
      }


      if (top_bid >= fbid) {
        out.print("Your bid is lower than or the same as the current highest bid");
      } else if (fbid<(top_bid +bid_inc)) {
        out.print("You need to bid at least $" + bid_inc + " higher than the current bid on this item");
      }
      else {
        //inserts bid into bids table and updates auction table accordingly
        String bid_insert = "INSERT INTO bids(aid, uid, amnt)" +
                "VALUES (?,?,?)";
        PreparedStatement auc_state = con.prepareStatement(bid_insert);
        auc_state.setInt(1, aid);
        auc_state.setInt(2, uid);
        auc_state.setFloat(3, fbid);
        auc_state.executeUpdate();

        String get_topid = "SELECT bids.bid FROM bids WHERE bids.aid = '" + aid + "'";
        Statement states = con.createStatement();
        ResultSet bid2 = states.executeQuery(get_topid);

        int bidid=0;
        if (bid2.next()) {
          bidid = bid2.getInt("bid");
        }

        //update auction table
        String auc_insert = "UPDATE auctions SET bid_amt = ?, highest_bid = ? WHERE auctions.aid = ?";
        PreparedStatement auc_update = con.prepareStatement(auc_insert);
        auc_update.setFloat(1, fbid);
        auc_update.setInt(2, bidid);
        auc_update.setInt(3, aid);
        auc_update.executeUpdate();

        out.print("You've placed a bid of $" + fbid + "!");
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
