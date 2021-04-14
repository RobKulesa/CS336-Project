<%@ page contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Account Landing Page</title>
</head>
<body>
<div>
	<p> Login below if your account already exists: </p>
	<form action="<%=request.getContextPath()%>/LoginServlet" method="post">
		<table>
			<tr>
				<td>Username</td>
				<td><label>
					<input type="text" name="username">
				</label></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><label>
					<input type="password" name="password">
				</label></td>
			</tr>
			<tr>
				<td><span style="color:red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span></td>
			</tr>
		</table>
		<input type="submit" value="Login!">
	</form>
	<p></p>
	<p></p>
	<p></p>
	<p> Create an account below if you don't have an account: </p>
	<form action="<%=request.getContextPath()%>/CreateUserServlet" method="post">
		<table>
			<tr>
				<td>NEWUSER Username</td>
				<td><label>
					<input type="text" name="newusername">
				</label></td>
			</tr>
			<tr>
				<td>NEWUSER Password</td>
				<td><label>
					<input type="password" name="newpassword">
				</label></td>
			</tr>
			<tr>
				<td><span style="color:red"><%=(request.getAttribute("createMessage") == null) ? "" : request.getAttribute("createMessage")%></span></td>
			</tr>
		</table>
		<input type="submit" value="Create account!">
		<input type="hidden" name="newusertype" value="end user">
	</form>
</div>
</body>
</html>