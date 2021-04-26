<%--
  Created by IntelliJ IDEA.
  User: domin
  Date: 4/22/2021
  Time: 8:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="com.auctionsite.util.ApplicationDB" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>View Auction</title>
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

        //Create a SQL statement
        Statement stmt = con.createStatement();
        //Get the combobox from the index.jsp
        String entity = request.getParameter("price");
        //Make a SELECT query from the sells table with the price range specified by the 'price' parameter at the index.jsp
        String str = "SELECT * FROM auctions, items WHERE auctions.part_number = items.part_number";
        //Run the query against the database

        ResultSet result = stmt.executeQuery(str);

        //Make an HTML table to show the results in:
        out.print("<table>");

        //make a row
        out.print("<tr>");

        //make a column
        out.print("<td>");
        out.print("Item");
        out.print("</td>");

        //make a column
        out.print("<td>");
        out.print("Brand");
        out.print("</td>");

        //make a column
        out.print("<td>");
        out.print("Condition");
        out.print("</td>");

        //make a column
        out.print("<td>");
        out.print("Close Date");
        out.print("</td>");

        //make a column
        out.print("<td>");
        out.print("Close Time");
        out.print("</td>");

        //make a column
        out.print("<td>");
        out.print("Current Bid");
        out.print("</td>");

        out.print("<td>");
        out.print("</td>");

        out.print("</tr>");

        //parse out the results


        while (result.next()) {
            //make a row
            out.print("<tr>");
            out.print("<td>");
            //Print out current bar name:
            out.print(result.getString("item_type"));
            out.print("</td>");
            out.print("<td>");

            out.print(result.getString("brand"));
            out.print("</td>");
            out.print("<td>");

            out.print(result.getString("con"));
            out.print("</td>");
            out.print("<td>");

            out.print(result.getString("close_date"));
            out.print("</td>");
            out.print("<td>");

            out.print(result.getString("close_time"));
            out.print("</td>");

            out.print("<td>");
            Statement innerLoopStmt = con.createStatement();
            String highestBidQuery = "SELECT * FROM bids WHERE bid= " + Integer.toString(result.getInt("highest_bid")) + ";";
            ResultSet highestBid = innerLoopStmt.executeQuery(highestBidQuery);
            if(highestBid.next()){
                out.print(highestBid.getFloat("amnt"));
            } else {
                out.print(Float.toString(result.getFloat("initial_price")));
            }
            out.print("</td>");

            out.print("<td>");
            out.print(
                    "<form action=\"manualBid.jsp\" method=\"get\">" +
                            "<input type=\"float\" name=\"bid\"/>" +
                            "    <input type=\"submit\" value=\"Create a manual bid\">" +
                            "    <input type =\"hidden\" name = \"aid\" value= \"" + Integer.toString(result.getInt("aid")) + "\">" +
                            "    <input type =\"hidden\" name = \"bid_increment\" value= \"" + Float.toString(result.getFloat("bid_increment")) + "\">" +
                            "</form>");


            out.print("</td>");

            out.print("<td>");
            out.print("<form action=\"createBid.jsp\" method=\"get\">" +
                    "    <input type=\"hidden\" name=\"origin\" value=\"viewAuc.jsp\">" +
                    "    <input type=\"submit\" value=\"Create an automatic bid\">" +
                    "    <input type =\"hidden\" name = \"aid\" value= \"" + Integer.toString(result.getInt("aid")) + "\">" +
                    "    <input type =\"hidden\" name = \"bid_increment\" value= \"" + Float.toString(result.getFloat("bid_increment")) + "\">" +
                    "</form>");

            out.print("</td>");
            out.print("</tr>");

        }
        out.print("</table>");


        //close the connection.
        con.close();

    } catch (Exception e) {
    }
%>

</body>
</html>
