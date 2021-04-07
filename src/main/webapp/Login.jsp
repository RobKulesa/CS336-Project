<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Login Attempt Result</title>
	</head>
	
	<body>
		<div>
			Login Attempt Result
			
			<%
		List<String> list = new ArrayList<String>();

		try {

			//Get the database connection
			ApplicationDB db = new ApplicationDB();	
			Connection con = db.getConnection();	
			
			//Create a SQL statement
			Statement stmt = con.createStatement();
			
			
			
			//Get the combobox from the index.jsp
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//Make a SELECT query from username and password forms
			String str = "SELECT * FROM users WHERE display_name = '" + username + "' and pwd = '" + password + "';";
			//Run the query against the database.
			ResultSet result = stmt.executeQuery(str);
			
			if(result.next()){
				out.print("<p>Login successful!!</p>");
			} else {
				out.print("<p>Login failed! Try again by returning to login screen</p>]");
			}
			
			//close the connection.
			con.close();
		} catch (Exception e) {
			out.print("\n error:" + e);
			out.print("\nLogin failed");
		}
	%>
			<form action = "LandingPage.jsp">
				<input type="submit" value="Back to Landing Page">
			</form>
		</div>
	
		

</body>
</html>