<%@ page import="com.auctionsite.dao.AuctionDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Rep Logged In!</title>
</head>
<body onload="<%AuctionDao aucDao = new AuctionDao();
        aucDao.refreshAuctions(aucDao.allAuctions());%>">
<form action="<%=request.getContextPath()%>/ThreadsServlet" method="post">
    <input type="submit" value="View Threads">
</form>

<form action="<%=request.getContextPath()%>/AccountInfoServlet" method="post">
    <input type="submit" value="View Accounts">
</form>

<form action="<%=request.getContextPath()%>/LogoutServlet" method="get">
    <input type="submit" value="Logout">
</form>
</body>
</html>
