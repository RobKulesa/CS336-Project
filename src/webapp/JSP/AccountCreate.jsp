<%@ page import="java.sql.*" %>
<%@ page import="com.auctionsite.util.ApplicationDB" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Account Creation Status</title>
</head>

<body>
<div>
	<%
				try {
					//Get the database connection
					ApplicationDB db = new ApplicationDB();
					Connection con = db.getConnection();

					//Get parameters from the HTML form at the index.jsp
					String newUserName = request.getParameter("newusername");
					String newPassword = request.getParameter("newpassword");

					//Make an insert statement for the Sells table:
					String insert = "INSERT INTO users(display_name, pwd)" + "VALUES (?, ?)";
					//Create a Prepared SQL statement allowing you to introduce the parameters of the query
					PreparedStatement ps = con.prepareStatement(insert);
			
					//Add parameters of the query. Start with 1, the 0-parameter is the INSERT statement itself
					
					ps.setString(1, newUserName);
					ps.setString(2, newPassword);
					ps.executeUpdate();
					
					//Close the connection. Don't forget to do it, otherwise you're keeping the resources of the server allocated.
					con.close();
					out.print("account creation succeeded");
					
				} catch (Exception ex) {
					out.print(ex);
					out.print("account creation failed");
				}
			%>
			<form action = "LandingPage.jsp">
				<input type="submit" value="Back to Landing Page">
			</form>
		</div>
</body>
</html>