<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.cs336.pkg.*"%>
<%@ page import="java.io.*,java.util.*,java.sql.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Account Landing Page</title>
	</head>
	
	<body>
		<div>
			<p> Login below if your account already exists: </p>
			<form action = "Login.jsp">
				<table>
					<tr>    
						<td>Username</td><td><input type="text" name="username"></td>
					</tr>
					<tr>
						<td>Password</td><td><input type="text" name="password"></td>
					</tr>
				</table>
				<input type="submit" value="Try Logging in!">
			</form>
			<p></p>
			<p></p>
			<p></p>
			<p> Create an account below  if you don't have an account: </p>
			<form action = "AccountCreate.jsp">
				<table>
					<tr>    
						<td>NEWUSER Username</td><td><input type="text" name="newusername"></td>
					</tr>
					<tr>
						<td>NEWUSER Password</td><td><input type="text" name="newpassword"></td>
					</tr>
				</table>
				<input type="submit" value="Try creating an account!">
			</form>
		</div>
	
		

</body>
</html>