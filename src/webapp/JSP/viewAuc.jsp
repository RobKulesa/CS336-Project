<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@ page import="com.auctionsite.util.ApplicationDB" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="javax.swing.plaf.nimbus.State" %>
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
    HttpSession session1 = request.getSession(false);


    try {
        out.print("Welcome: " + (String)session.getAttribute("uid"));
        //Get the database connection
        ApplicationDB db = new ApplicationDB();
        Connection con = db.getConnection();

        //Create a SQL statement
        Statement stmt = con.createStatement();
        //Get the combobox from the index.jsp
        String entity = request.getParameter("price");
        //Make a SELECT query from the sells table with the price range specified by the 'price' parameter at the index.jsp
        String str = "SELECT * FROM auctions";
        //Run the query against the database.
        ResultSet result = stmt.executeQuery(str);

        //Make an HTML table to show the results in:
        out.print("<table>");

        //make a row
        out.print("<tr>");

        //aid column
        out.print("<td>");
        out.print("aid");
        out.print("</td>");

        //Close time column
        out.print("<td>");
        out.print("\tClose Time\t");
        out.print("</td>");


        out.print("<td>");
        out.print("\tCurrent Highest Bid\t");
        out.print("</td>");

        out.print("<td>");
        out.print("\tinit_price\t");
        out.print("</td>");

        out.print("<td>");
        out.print("\tbid_increment\t");
        out.print("</td>");

        out.print("<td>");
        out.print("\tAutomatic bid creation\t");
        out.print("</td>");

        out.print("</tr>");

        //parse out the results
        while (result.next()) {
            //make a row
            out.print("<tr>");

            //aid
            out.print("<td>");
            out.print(Integer.toString(result.getInt("aid")));
            out.print("</td>");

            //close_time
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
            out.print(Float.toString(result.getFloat("initial_price")));
            out.print("</td>");

            out.print("<td>");
            out.print(Float.toString(result.getFloat("bid_increment")));
            out.print("</td>");

            out.print("<td>");
            out.print(request.getContextPath());
            out.print("<form action=\"createBid.jsp\" method=\"get\">" +
                    "    <input type=\"hidden\" name=\"origin\" value=\"viewAuc.jsp\">" +
                    "    <input type=\"submit\" value=\"Create an automatic bid\">" +
                    "    <input type =\"hidden\" name = \"aid\" value= \"" + Integer.toString(result.getInt("aid")) + "\">" +
                    "    <input type =\"hidden\" name = \"bid_increment\" value= \"" + Float.toString(result.getFloat("bid_increment")) + "\">" +
                    "</form>");


            out.print("</td>");
            out.print("</tr>");
            innerLoopStmt.close();
        }
        out.print("</table>");

        String new_bid = request.getParameter("bid");
        out.print(new_bid);

        //close the connection.


    } catch (Exception e) {
        e.printStackTrace();
    }
%>

</body>
</html>
