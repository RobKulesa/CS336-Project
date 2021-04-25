<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>End User Logged In!</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/ThreadsServlet" method="post">
    <input type="submit" value="View Threads">
</form>

<form action="<%=request.getContextPath()%>/LogoutServlet" method="get">
    <input type="submit" value="Logout">
</form>
</body>
</html>
